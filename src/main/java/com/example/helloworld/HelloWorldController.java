package com.example.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/hello")
    public String hello() {
        try {
            // Loging success message
            logger.info("Hello, world!");

            kafkaTemplate.send("hello-topic", "Hello, world!")
                .addCallback(
                    success -> logger.info("Message sent successfully"),
                    failure -> logger.error("Message sending failed", failure)
                );

            return "Hello, world!";
        } catch (Exception e) {
            // loging exeption
            logger.error("An error occurred", e);
            // send message to kafka
            sendErrorToKafka(e);

            return "An error occurred";
        }
    }

    public void sendErrorToKafka(Exception e) {
        String errorMessage = "Error occurred: " + e.getMessage();
        kafkaTemplate.send("error-topic", errorMessage)
            .addCallback(
                success -> logger.info("Error message sent successfully"),
                failure -> logger.error("Error message sending failed", failure)
            );
    }
}
