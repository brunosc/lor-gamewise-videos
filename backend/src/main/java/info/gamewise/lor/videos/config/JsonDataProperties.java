package info.gamewise.lor.videos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "lor.json-data")
public class JsonDataProperties {
    private String channelsUrl;
    private String championsUrl;

    public String getChannelsUrl() {
        return channelsUrl;
    }

    public void setChannelsUrl(String channelsUrl) {
        this.channelsUrl = channelsUrl;
    }

    public String getChampionsUrl() {
        return championsUrl;
    }

    public void setChampionsUrl(String championsUrl) {
        this.championsUrl = championsUrl;
    }

    @Override
    public String toString() {
        return "JsonDataProperties{" +
                "channelsUrl='" + channelsUrl + '\'' +
                ", championsUrl='" + championsUrl + '\'' +
                '}';
    }
}
