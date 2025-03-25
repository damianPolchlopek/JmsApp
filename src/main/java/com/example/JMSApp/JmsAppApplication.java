package com.example.JMSApp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;

@Slf4j
@SpringBootApplication
public class JmsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmsAppApplication.class, args);
    }


    @JmsListener(destination = "example.queue")
    public void listen(String msg) {
        log.info("Listening queue: " + msg);
    }

    @JmsListener(destination = "example.topic",
            subscription = "myDurableSubscription")
    public void listenTopic(String msg) {
        log.info("Listening topic: " + msg);
    }

}
