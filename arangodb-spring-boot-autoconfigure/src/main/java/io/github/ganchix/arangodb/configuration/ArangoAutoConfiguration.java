package io.github.ganchix.arangodb.configuration;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.Ref;
import com.arangodb.springframework.annotation.Relations;
import com.arangodb.springframework.annotation.To;
import com.arangodb.springframework.config.ArangoEntityClassScanner;
import com.arangodb.springframework.core.ArangoOperations;
import com.arangodb.springframework.core.convert.ArangoConverter;
import com.arangodb.springframework.core.convert.ArangoCustomConversions;
import com.arangodb.springframework.core.convert.DefaultArangoConverter;
import com.arangodb.springframework.core.convert.resolver.*;
import com.arangodb.springframework.core.mapping.ArangoMappingContext;
import com.arangodb.springframework.core.template.ArangoTemplate;
import com.arangodb.velocypack.module.jdk8.VPackJdk8Module;
import com.arangodb.velocypack.module.joda.VPackJodaModule;
import io.github.ganchix.arangodb.health.ArangoHealthCheck;
import io.github.ganchix.arangodb.properties.ArangoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy;

import javax.annotation.PreDestroy;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * AutoConfiguration class.
 * <p>
 * Created by Rafa on 05/04/17.
 */
@Configuration
@ConditionalOnClass({ArangoDB.class})
@EnableConfigurationProperties(ArangoProperties.class)
public class ArangoAutoConfiguration {

    private ArangoOperations arangoOperations;

    @Autowired
    private ArangoProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public ArangoDB.Builder arangoBuilder() {
        ArangoDB.Builder arangoBuilder = new ArangoDB.Builder()
                .user(properties.getUser())
                .password(properties.getPassword())
                .chunksize(properties.getChunksize())
                .maxConnections(properties.getMaxConnections())
                .timeout(properties.getTimeout())
                .useSsl(properties.getUseSsl())
                .host(properties.getHost(), properties.getPort())
                .acquireHostList(properties.getAcquireHostList())
                .loadBalancingStrategy(properties.getLoadBalancingStrategy())
                .useProtocol(properties.getProtocol());

        if (properties.getHosts() != null && properties.getHosts().size() > 0) {
            for (final String host : properties.getHosts()) {
                final String[] split = host.split(":");
                if (split.length != 2 || !split[1].matches("[0-9]+")) {
                    throw new ArangoDBException(String.format(
                            "Could not load property-value spring.data.arangodb.hosts=%s. Expected format ip:port,ip:port,...",
                            properties.getHosts()));
                } else {
                    arangoBuilder.host(split[0], Integer.valueOf(split[1]));
                }
            }

        }
        return arangoBuilder;
    }


    private ArangoDB.Builder configure(final ArangoDB.Builder arangoBuilder) {
        return arangoBuilder.registerModules(new VPackJdk8Module(), new VPackJodaModule());
    }

    @Bean
    @ConditionalOnMissingBean
    public ArangoOperations arangoTemplate(ArangoDB.Builder arangoBuilder) throws Exception {
        this.arangoOperations = new ArangoTemplate(configure(arangoBuilder), properties.getDatabaseName(), arangoConverter(arangoBuilder));
        return arangoOperations;
    }

    @Bean
    @ConditionalOnMissingBean
    public ArangoMappingContext arangoMappingContext() throws Exception {
        final ArangoMappingContext context = new ArangoMappingContext();
        context.setInitialEntitySet(getInitialEntitySet());
        context.setFieldNamingStrategy(fieldNamingStrategy());
        context.setSimpleTypeHolder(customConversions().getSimpleTypeHolder());
        return context;
    }

    @Bean
    @ConditionalOnMissingBean
    public CustomConversions customConversions() {
        return new ArangoCustomConversions(Collections.emptyList());
    }

    private Set<? extends Class<?>> getInitialEntitySet() throws ClassNotFoundException {
        return ArangoEntityClassScanner.scanForEntities(getEntityBasePackages());
    }

    private String[] getEntityBasePackages() {
        return new String[]{getClass().getPackage().getName()};
    }

    private FieldNamingStrategy fieldNamingStrategy() {
        return PropertyNameFieldNamingStrategy.INSTANCE;
    }

    @Bean
    @ConditionalOnMissingBean
    public ArangoConverter arangoConverter(ArangoDB.Builder arangoBuilder) throws Exception {
        return new DefaultArangoConverter(arangoMappingContext(), customConversions(), new ResolverFactory() {
            @SuppressWarnings("unchecked")
            @Override
            public <A extends Annotation> Optional<ReferenceResolver<A>> getReferenceResolver(final A annotation) {
                ReferenceResolver<A> resolver = null;
                try {
                    if (annotation instanceof Ref) {
                        resolver = (ReferenceResolver<A>) new RefResolver(arangoTemplate(arangoBuilder));
                    }
                } catch (final Exception e) {
                    throw new ArangoDBException(e);
                }
                return Optional.ofNullable(resolver);
            }

            @SuppressWarnings("unchecked")
            @Override
            public <A extends Annotation> Optional<RelationResolver<A>> getRelationResolver(final A annotation) {
                RelationResolver<A> resolver = null;
                try {
                    if (annotation instanceof From) {
                        resolver = (RelationResolver<A>) new FromResolver(arangoTemplate(arangoBuilder));
                    } else if (annotation instanceof To) {
                        resolver = (RelationResolver<A>) new ToResolver(arangoTemplate(arangoBuilder));
                    } else if (annotation instanceof Relations) {
                        resolver = (RelationResolver<A>) new RelationsResolver(arangoTemplate(arangoBuilder));
                    }
                } catch (final Exception e) {
                    throw new ArangoDBException(e);
                }
                return Optional.ofNullable(resolver);
            }
        });
    }

    @Bean
    @ConditionalOnMissingBean
    ArangoHealthCheck arango(ArangoOperations arangoOperations) {
        return new ArangoHealthCheck(arangoOperations);
    }

    @PreDestroy
    void destroy() {
        if (this.arangoOperations != null && this.arangoOperations.driver() != null) {
            this.arangoOperations.driver().shutdown();
        }

    }
}

