package pl.beng.thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CVGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CVGeneratorApplication.class, args);
    }
}
