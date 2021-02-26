package info.gamewise.lor.videos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LoRVideosApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoRVideosApplication.class, args);
    }

}
