//package com.example.JMSApp;
//
//import jakarta.jms.JMSException;
//import jakarta.jms.Message;
//import jakarta.jms.TextMessage;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalTime;
//import java.util.Date;
//
//@Slf4j
//@Component
//public class TestRedeliverPolicy {
//
//
//    int counter = 0;
//    int retryCount = 0;
//
//    @JmsListener(destination = "example.queue")
//    public void listen(String msg) {
//        log.info("Try number: " + counter++);
//
//        System.out.println("Proba odebrania " + msg + " aktualny czas: " + LocalTime.now());
//
//        if (retryCount < 3) {
//            retryCount++;
//            System.out.println("Symulacja błędu, ponowne dostarczenie...");
//            throw new RuntimeException("Błąd przetwarzania wiadomości");
//        }
//
//        System.out.println("Wiadomość poprawnie przetworzona po " + retryCount + " ponownych próbach.");
//
////		log.info("Listening queue: " + msg);
//    }
//
//
//}
