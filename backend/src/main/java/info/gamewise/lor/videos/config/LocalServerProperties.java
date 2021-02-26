package info.gamewise.lor.videos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "lor.you-tube.local-server")
public class LocalServerProperties {
    private String host;
    private int port;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "LocalServerProperties{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
