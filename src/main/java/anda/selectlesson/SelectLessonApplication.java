package anda.selectlesson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("anda")
@EnableJpaRepositories("anda.selectlesson.repo")
public class SelectLessonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelectLessonApplication.class, args);
    }

}
