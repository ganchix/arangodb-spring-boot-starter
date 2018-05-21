package io.github.ganchix.arangodb.configuration;

import com.arangodb.springframework.core.ArangoOperations;
import io.github.ganchix.arangodb.ArangoDBContainer;
import io.github.ganchix.arangodb.health.ArangoHealthCheck;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.testcontainers.containers.GenericContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArangodbAutoConfigurationTests {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ArangoAutoConfiguration.class));

    @Rule
    public ArangoDBContainer arangoDBContainer = new ArangoDBContainer().withPassword("openSesame");



    @Test
    public void checkIfArangoDBIsConnected() {
        this.contextRunner
                .withPropertyValues("spring.data.arangodb.password=openSesame",
                        "spring.data.arangodb.port="+arangoDBContainer.getMappedPort(arangoDBContainer.getPort()))
                .run((context) -> {
                    ArangoOperations arangoOperations = context.getBean(ArangoOperations.class);
                    assertNotNull(arangoOperations.getVersion());
                    assertThat(context).hasSingleBean(ArangoOperations.class);
                });
    }

    @Test
    public void checkHealthCheck() {
        this.contextRunner
                .withPropertyValues("spring.data.arangodb.password=openSesame",
                        "spring.data.arangodb.port="+arangoDBContainer.getMappedPort(arangoDBContainer.getPort()))
                .run((context) -> {
                    assertThat(context).hasSingleBean(ArangoHealthCheck.class);

                    HealthIndicator arangoHealthCheck = (HealthIndicator)context.getBean(ArangoHealthCheck.class);
                    assertEquals(arangoHealthCheck.health().getStatus(), Status.UP);
                });
    }
}
