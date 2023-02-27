package guru.springframework.sfgjms.listeners;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.ExtHelloMessage;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMsg, @Headers MessageHeaders headers, Message msg) {
        System.out.println("I Got Message!");
        System.out.println(helloWorldMsg);
    }

    //    public void listenForHello(@Payload ExtHelloMessage helloWorldMsg, @Headers MessageHeaders headers, Message msg)

    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenForHello(Message message)
            throws JMSException {

        ExtHelloMessage payloadMsg = ExtHelloMessage
                .builder()
                .id(UUID.randomUUID())
                .message("World")
                .build();
        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);
    }

}
