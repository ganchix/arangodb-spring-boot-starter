package io.github.ganchix.arangodb.configuration;

import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.repository.ArangoRepositoryConfigurationExtension;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

import java.lang.annotation.Annotation;

/**
 * Used to auto-configure Spring Data ArangoDB Repositories.
 *
 * Created by Rafael Ríos on 8/04/18.
 */
public class ArangoRepositoriesAutoConfigureRegistrar extends AbstractRepositoryConfigurationSourceSupport {

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableArangoRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return ArangoRepositoriesAutoConfigureRegistrar.EnableArangoRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new ArangoRepositoryConfigurationExtension();
    }

    @EnableArangoRepositories
    private static class EnableArangoRepositoriesConfiguration {

    }

}