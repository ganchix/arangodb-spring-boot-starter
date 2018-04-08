package io.github.ganchix.morphia.configuration;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.springframework.config.AbstractArangoConfiguration;
import com.arangodb.springframework.core.ArangoOperations;
import io.github.ganchix.arangodb.health.ArangoHealthCheck;
import io.github.ganchix.arangodb.properties.ArangoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AutoConfiguration class.
 * <p>
 * Created by Rafa on 05/04/17.
 */
@Configuration
@ConditionalOnClass({ArangoDB.class})
@EnableConfigurationProperties(ArangoProperties.class)
public class ArangoAutoConfiguration extends AbstractArangoConfiguration {

    @Autowired
    private ArangoProperties properties;


    @Override
    public ArangoDB.Builder arango() {
        ArangoDB.Builder arangoBuilder = new ArangoDB.Builder()
                .user(properties.getUser())
                .password(properties.getPassword())
                .chunksize(properties.getChunksize())
                .maxConnections(properties.getMaxConnections())
                .timeout(properties.getTimeout())
                .useSsl(properties.getUseSsl())
                .host(properties.getHost(), properties.getPort())
                .acquireHostList(properties.getAcquireHost())
                .loadBalancingStrategy(properties.getLoadBalancingStrategy())
                .useProtocol(properties.getProtocol());

        if (properties.getHosts() != null && properties.getHosts().size() > 0) {
            for (final String host : properties.getHosts()) {
                final String[] split = host.split(":");
                if (split.length != 2 || !split[1].matches("[0-9]+")) {
                    throw new ArangoDBException(String.format(
                            "Could not load property-value arangodb.hosts=%s. Expected format ip:port,ip:port,...",
                            properties.getHosts()));
                } else {
                    arangoBuilder.host(split[0], Integer.valueOf(split[1]));
                }
            }

        }
        return arangoBuilder;
    }

    @Override
    public String database() {
        return properties.getDatabaseName();
    }


    @Bean
    ArangoHealthCheck arangoHealthCheck(ArangoOperations arangoOperations) {
        return new ArangoHealthCheck(arangoOperations);
    }
}

