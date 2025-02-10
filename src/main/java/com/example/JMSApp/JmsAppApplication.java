package com.example.JMSApp;

import jakarta.jms.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

	@Bean
	public ConnectionFactory sslConnectionFactory(
			@Value("${spring.artemis.broker-url}") String brokerUrl,
			@Value("${spring.artemis.user}") String user,
			@Value("${spring.artemis.password}") String password
	) {
		ActiveMQSslConnectionFactory cf = new ActiveMQSslConnectionFactory(brokerUrl);
		cf.setUserName(user);
		cf.setPassword(password);
		cf.setOptimizeAcknowledge(true);
		cf.setAlwaysSessionAsync(false);

		PooledConnectionFactory pcf = new PooledConnectionFactory();
		pcf.setConnectionFactory(cf);

		return pcf;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
			ConnectionFactory connectionFactory
	) {

		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setPubSubDomain(true);
		factory.setSubscriptionDurable(true);
		factory.setClientId("client-1");
		return factory;
	}

}
