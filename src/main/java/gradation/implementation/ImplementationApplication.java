package gradation.implementation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ImplementationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImplementationApplication.class, args);
    }

}
