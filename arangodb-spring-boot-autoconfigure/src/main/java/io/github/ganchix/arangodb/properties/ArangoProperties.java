package io.github.ganchix.arangodb.properties;

import com.arangodb.Protocol;
import com.arangodb.entity.LoadBalancingStrategy;
import com.arangodb.internal.ArangoDBConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * ArangoDB Properties.
 *
 * Created by Rafael Ríos on 8/04/18.
 */
@ConfigurationProperties(prefix = "spring.data.arangodb")
public class ArangoProperties {

    /**
     * Hosts list with format ip:port,ip:port
     */
    private List<String> hosts;

    /**
     * ArangoDB host, {@link ArangoDBConstants default value}
     */
    private String host = ArangoDBConstants.DEFAULT_HOST;

    /**
     * ArangoDB port, {@link ArangoDBConstants default value}
     */
    private Integer port = ArangoDBConstants.DEFAULT_PORT;

    /**
     * Socket connect timeout(millisecond), {@link ArangoDBConstants default value}
     */
    private Integer timeout = ArangoDBConstants.DEFAULT_TIMEOUT;

    /**
     * Basic Authentication User, {@link ArangoDBConstants default value}
     */
    private String user = ArangoDBConstants.DEFAULT_USER;

    /**
     * Basic Authentication Password
     */
    private String password;

    /**
     * Use SSL connection, {@link ArangoDBConstants default value}
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
     * Protocol used, {@link ArangoDBConstants default value}
     */
    private Protocol protocol = ArangoDBConstants.DEFAULT_NETWORK_PROTOCOL;

    /**
     * Load balancing strategy used, {@link ArangoDBConstants default value}
     */
    private LoadBalancingStrategy loadBalancingStrategy = ArangoDBConstants.DEFAULT_LOAD_BALANCING_STRATEGY;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public Boolean getUseSsl() {
        return useSsl;
    }

    public void setUseSsl(Boolean useSsl) {
        this.useSsl = useSsl;
    }

    public Integer getChunksize() {
        return chunksize;
    }

    public void setChunksize(Integer chunksize) {
        this.chunksize = chunksize;
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getAcquireHostList() {
        return acquireHostList;
    }

    public void setAcquireHostList(Boolean acquireHostList) {
        this.acquireHostList = acquireHostList;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public LoadBalancingStrategy getLoadBalancingStrategy() {
        return loadBalancingStrategy;
    }

    public void setLoadBalancingStrategy(LoadBalancingStrategy loadBalancingStrategy) {
        this.loadBalancingStrategy = loadBalancingStrategy;
    }
}
