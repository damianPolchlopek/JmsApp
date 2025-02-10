package com.example.JMSApp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class JmsController {

    private JmsTemplate jmsTemplate;

    @GetMapping("queue/addMsg/{msg}")
    public void addMsg(@PathVariable String msg){
        log.info("Add to queue: " + msg);

        jmsTemplate.convertAndSend("example.queue", msg);
    }

    @GetMapping("topic/addMsg/{msg}")
    public void addMsgTopic(@PathVariable String msg){
        log.info("Add to topic: " + msg);
        jmsTemplate.convertAndSend("example.topic", msg);
    }

}
