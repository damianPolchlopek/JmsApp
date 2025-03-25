# Jak postawic brokera activemq:artemis

## Komenda Dockera:
docker run -d --name artemis -p 61616:61616 -p 8161:8161 apache/activemq-artemis

## Link do dokumentacji: 
https://activemq.apache.org/components/artemis/documentation/latest/

## Link do UI:
http://localhost:8161

## Rozne opcje brokera:

?randomize=false&maxReconnectAttempts=5
?randomize=false&rebalanceClusterClients=true -> po wstaniu pierwszego brokera zostanie ponownie podpiety

## Różne konfiguracje brokera:

1. Broker bez failovera
```
org.apache.activemq.transport.InactivityIOException: Channel was inactive for too (>30000) long: tcp://127.0.0.1:61616
	at org.apache.activemq.transport.AbstractInactivityMonitor$5.run(AbstractInactivityMonitor.java:248) ~[activemq-client-6.1.5.jar:6.1.5]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:833) ~[na:na]

2025-03-25T09:53:36.670+01:00 ERROR 29564 --- [JMSApp] [ntContainer#1-7] o.s.j.l.DefaultMessageListenerContainer  : Could not refresh JMS Connection for destination 'example.topic' - retrying using FixedBackOff{interval=5000, currentAttempts=0, maxAttempts=unlimited}. Cause: Wire format negotiation timeout: peer did not send his wire format.
2025-03-25T09:53:41.682+01:00  WARN 29564 --- [JMSApp] [ntContainer#0-4] o.s.j.l.DefaultMessageListenerContainer  : Setup of JMS message listener invoker failed for destination 'example.queue' - trying to recover. Cause: jakarta.jms.JMSException: Cannot send, channel has already failed: tcp://127.0.0.1:61616
2025-03-25T09:53:51.696+01:00 ERROR 29564 --- [JMSApp] [ntContainer#1-7] o.s.j.l.DefaultMessageListenerContainer  : Could not refresh JMS Connection for destination 'example.topic' - retrying using FixedBackOff{interval=5000, currentAttempts=1, maxAttempts=unlimited}. Cause: Wire format negotiation timeout: peer did not send his wire format.
2025-03-25T09:53:56.704+01:00  WARN 29564 --- [JMSApp] [ntContainer#0-5] o.s.j.l.DefaultMessageListenerContainer  : Setup of JMS message listener invoker failed for destination 'example.queue' - trying to recover. Cause: jakarta.jms.JMSException: Cannot send, channel has already failed: tcp://127.0.0.1:61616
2025-03-25T09:54:06.728+01:00 ERROR 29564 --- [JMSApp] [ntContainer#1-7] o.s.j.l.DefaultMessageListenerContainer  : Could not refresh JMS Connection for destination 'example.topic' - retrying using FixedBackOff{interval=5000, currentAttempts=2, maxAttempts=unlimited}. Cause: Wire format negotiation timeout: peer did not send his wire format.
2025-03-25T09:54:11.742+01:00  WARN 29564 --- [JMSApp] [ntContainer#0-6] o.s.j.l.DefaultMessageListenerContainer  : Setup of JMS message listener invoker failed for destination 'example.queue' - trying to recover. Cause: jakarta.jms.JMSException: Cannot send, channel has already failed: tcp://127.0.0.1:61616
2025-03-25T09:54:21.755+01:00 ERROR 29564 --- [JMSApp] [ntContainer#1-7] o.s.j.l.DefaultMessageListenerContainer  : Could not refresh JMS Connection for destination 'example.topic' - retrying using FixedBackOff{interval=5000, currentAttempts=3, maxAttempts=unlimited}. Cause: Wire format negotiation timeout: peer did not send his wire format.
2025-03-25T09:54:26.768+01:00  WARN 29564 --- [JMSApp] [ntContainer#0-7] o.s.j.l.DefaultMessageListenerContainer  : Setup of JMS message listener invoker failed for destination 'example.queue' - trying to recover. Cause: jakarta.jms.JMSException: Cannot send, channel has already failed: tcp://127.0.0.1:61616
2025-03-25T09:54:36.787+01:00 ERROR 29564 --- [JMSApp] [ntContainer#1-7] o.s.j.l.DefaultMessageListenerContainer  : Could not refresh JMS Connection for destination 'example.topic' - retrying using FixedBackOff{interval=5000, currentAttempts=4, maxAttempts=unlimited}. Cause: Wire format negotiation timeout: peer did not send his wire format.
2025-03-25T09:54:41.802+01:00  WARN 29564 --- [JMSApp] [ntContainer#0-8] o.s.j.l.DefaultMessageListenerContainer  : Setup of JMS message listener invoker failed for destination 'example.queue' - trying to recover. Cause: jakarta.jms.JMSException: Cannot send, channel has already failed: tcp://127.0.0.1:61616
2025-03-25T09:54:51.826+01:00 ERROR 29564 --- [JMSApp] [ntContainer#1-7] o.s.j.l.DefaultMessageListenerContainer  : Could not refresh JMS Connection for destination 'example.topic' - retrying using FixedBackOff{interval=5000, currentAttempts=5, maxAttempts=unlimited}. Cause: Wire format negotiation timeout: peer did not send his wire format.
2025-03-25T09:54:56.834+01:00  WARN 29564 --- [JMSApp] [ntContainer#0-9] o.s.j.l.DefaultMessageListenerContainer  : Setup of JMS message listener invoker failed for destination 'example.queue' - trying to recover. Cause: jakarta.jms.JMSException: Cannot send, channel has already failed: tcp://127.0.0.1:61616

```


2. Failover z Jednym brokerem:

```
2025-03-25T12:53:37.234+01:00  INFO 22256 --- [JMSApp] [nio-8081-exec-3] com.example.JMSApp.JmsController         : Add to queue: Kasia
2025-03-25T12:54:31.603+01:00  WARN 22256 --- [JMSApp] [onitor Worker 0] o.a.a.t.failover.FailoverTransport       : Transport (tcp://localhost:61616) failed, attempting to automatically reconnect

org.apache.activemq.transport.InactivityIOException: Channel was inactive for too (>30000) long: tcp://127.0.0.1:61616
	at org.apache.activemq.transport.AbstractInactivityMonitor$5.run(AbstractInactivityMonitor.java:248) ~[activemq-client-6.1.5.jar:6.1.5]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:833) ~[na:na]

2025-03-25T12:54:41.630+01:00  WARN 22256 --- [JMSApp] [ActiveMQ Task-3] o.a.a.t.failover.FailoverTransport       : Failed to connect to [tcp://localhost:61616] after: 1 attempt(s) with Wire format negotiation timeout: peer did not send his wire format., continuing to retry.
2025-03-25T12:56:22.024+01:00  WARN 22256 --- [JMSApp] [ActiveMQ Task-3] o.a.a.t.failover.FailoverTransport       : Failed to connect to [tcp://localhost:61616] after: 10 attempt(s) with Wire format negotiation timeout: peer did not send his wire format., continuing to retry.
2025-03-25T12:58:22.810+01:00  INFO 22256 --- [JMSApp] [ActiveMQ Task-3] o.a.a.t.failover.FailoverTransport       : Successfully reconnected to tcp://localhost:61616
2025-03-25T12:58:22.841+01:00  INFO 22256 --- [JMSApp] [ntContainer#0-1] com.example.JMSApp.JmsAppApplication     : Listening queue: Kasia
```

3. Failover z dwoma brokerami: (main is down)

```
2025-03-25T10:01:56.413+01:00  INFO 16908 --- [JMSApp] [nio-8081-exec-5] com.example.JMSApp.JmsController         : Add to queue: Kasia
2025-03-25T10:02:54.629+01:00  WARN 16908 --- [JMSApp] [onitor Worker 0] o.a.a.t.failover.FailoverTransport       : Transport (tcp://localhost:61616) failed, attempting to automatically reconnect

org.apache.activemq.transport.InactivityIOException: Channel was inactive for too (>30000) long: tcp://127.0.0.1:61616
	at org.apache.activemq.transport.AbstractInactivityMonitor$5.run(AbstractInactivityMonitor.java:248) ~[activemq-client-6.1.5.jar:6.1.5]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:833) ~[na:na]

2025-03-25T10:02:54.760+01:00  INFO 16908 --- [JMSApp] [ActiveMQ Task-3] o.a.a.t.failover.FailoverTransport       : Successfully reconnected to tcp://localhost:61617
2025-03-25T10:02:55.005+01:00  INFO 16908 --- [JMSApp] [ntContainer#0-1] com.example.JMSApp.JmsAppApplication     : Listening queue: Kasia
```




Druga instancja brokera:

docker run -d --name artemis2 -p 61617:61616 -p 8162:8161 apache/activemq-artemis