package io.github.ganchix.arangodb.health;

import com.arangodb.entity.ArangoDBVersion;
import com.arangodb.springframework.core.ArangoOperations;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.util.Assert;

/**
 * Created by Rafael Ríos on 8/04/18.
 */

public class ArangoHealthCheck extends AbstractHealthIndicator {

    private final ArangoOperations arangoOperations;

    public ArangoHealthCheck(ArangoOperations arangoOperations) {
        Assert.notNull(arangoOperations, "ArangoOperations must not be null");
        this.arangoOperations = arangoOperations;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        ArangoDBVersion version = arangoOperations.getVersion();
        builder.up();
        builder.withDetail("server", version.getServer());
        builder.withDetail("version", version.getVersion());
        builder.withDetail("license", version.getLicense());

    }
}
