package io.github.ganchix.arangodb.configuration;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.repository.ArangoRepository;
import com.arangodb.springframework.repository.ArangoRepositoryConfigurationExtension;
import com.arangodb.springframework.repository.ArangoRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link EnableAutoConfiguration Autoconfiguration} for Spring Data's ArangoDB
 * Repositories.
 * <p>
 * Created by Rafael Ríos on 8/04/18.
 */
@Configuration
@ConditionalOnClass({ArangoDB.class, ArangoRepository.class})
@ConditionalOnMissingBean({ArangoRepositoryFactoryBean.class,
        ArangoRepositoryConfigurationExtension.class})
@ConditionalOnProperty(prefix = "spring.data.arangodb.repositories", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import(ArangoRepositoriesAutoConfigureRegistrar.class)
@AutoConfigureAfter(ArangoAutoConfiguration.class)
public class ArangoRepositoriesAutoConfiguration {
}
