package io.github.ganchix.arangodb.properties;

import com.arangodb.Protocol;
import com.arangodb.entity.LoadBalancingStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by Rafael Ríos on 8/04/18.
 */

@ConfigurationProperties(prefix = "spring.data.arangodb")
public class ArangoProperties {


    private List<String> hosts;
    private String host = "localhost";
    private Integer port = 8529;
    private Integer timeout = 0;
    private String user;
    private String password;
    private Boolean useSsl;
    private Integer chunksize;
    private Integer maxConnections;
    private String databaseName;
    private Boolean acquireHost;
    private Protocol protocol = Protocol.VST;
    private LoadBalancingStrategy loadBalancingStrategy = LoadBalancingStrategy.NONE;


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

    public Boolean getAcquireHost() {
        return acquireHost;
    }

    public void setAcquireHost(Boolean acquireHost) {
        this.acquireHost = acquireHost;
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
