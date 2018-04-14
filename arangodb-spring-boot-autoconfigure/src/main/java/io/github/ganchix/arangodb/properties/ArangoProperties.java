package io.github.ganchix.arangodb.properties;

import com.arangodb.Protocol;
import com.arangodb.entity.LoadBalancingStrategy;
import com.arangodb.internal.ArangoDBConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * ArangoDB Properties.
 *
 * Created by Rafael Ríos on 8/04/18.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.data.arangodb")
public class ArangoProperties {

    /**
     * Hosts list with format ip:port,ip:port
     */
    private List<String> hosts;

    /**
     * ArangoDB host, 127.0.0.1 default value
     */
    private String host = ArangoDBConstants.DEFAULT_HOST;

    /**
     * ArangoDB port, 8529 default value
     */
    private Integer port = ArangoDBConstants.DEFAULT_PORT;

    /**
     * Socket connect timeout(millisecond), 0 default value
     */
    private Integer timeout = ArangoDBConstants.DEFAULT_TIMEOUT;

    /**
     * Basic Authentication User, root default value
     */
    private String user = ArangoDBConstants.DEFAULT_USER;

    /**
     * Basic Authentication Password
     */
    private String password;

    /**
     * Use SSL connection, false default value
     */
    private Boolean useSsl = ArangoDBConstants.DEFAULT_USE_SSL;

    /**
     * Chunk size used
     */
    private Integer chunksize;

    /**
     * Max connections value
     */
    private Integer maxConnections;

    /**
     * Database name
     */
    private String databaseName;

    /**
     * Use acquire host list
     */
    private Boolean acquireHostList = ArangoDBConstants.DEFAULT_ACQUIRE_HOST_LIST;

    /**
     * Protocol used, VST default value
     */
    private Protocol protocol = ArangoDBConstants.DEFAULT_NETWORK_PROTOCOL;

    /**
     * Load balancing strategy used, NONE default value
     */
    private LoadBalancingStrategy loadBalancingStrategy = ArangoDBConstants.DEFAULT_LOAD_BALANCING_STRATEGY;

}
