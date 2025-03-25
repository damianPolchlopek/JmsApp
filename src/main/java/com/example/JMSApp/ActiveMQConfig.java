package com.example.JMSApp;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
public class ActiveMQConfig {

    @Bean
    public ConnectionFactory sslConnectionFactory(
            @Value("${spring.artemis.broker-url}") String brokerUrl,
            @Value("${spring.artemis.user}") String user,
            @Value("${spring.artemis.password}") String password
    ) {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(4);
        redeliveryPolicy.setInitialRedeliveryDelay(2000);
        redeliveryPolicy.setBackOffMultiplier(2);
        redeliveryPolicy.setUseExponentialBackOff(true);

        ActiveMQSslConnectionFactory cf = new ActiveMQSslConnectionFactory(brokerUrl);
        cf.setUserName(user);
        cf.setPassword(password);
        cf.setOptimizeAcknowledge(true);
        cf.setAlwaysSessionAsync(false);
        cf.setRedeliveryPolicy(redeliveryPolicy);

        PooledConnectionFactory pcf = new PooledConnectionFactory();
        pcf.setConnectionFactory(cf);

        return pcf;
    }

//    @Bean
//    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
//            ConnectionFactory connectionFactory
//    ) {
//
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setPubSubDomain(true);
//        factory.setSubscriptionDurable(true);
//        factory.setClientId("client-1");
//        return factory;
//    }

}
