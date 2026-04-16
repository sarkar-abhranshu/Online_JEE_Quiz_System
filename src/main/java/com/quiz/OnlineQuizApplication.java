package com.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    SOLID/GRASP highlights:
    - SRP (Single Responsibility): Contains only application bootstrap/entry-point responsibility.
*/
@SpringBootApplication
public class OnlineQuizApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineQuizApplication.class, args);
    }
}
