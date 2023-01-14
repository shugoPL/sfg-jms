package guru.springframework.sfgjms.senders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
//        System.out.println("I'm sending a message!");
        HelloWorldMessage helloWorldMessage = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, helloWorldMessage);
//        System.out.println("Message sent!");
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();
        Message rcvMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMsg = null;
                try {
                    helloMsg = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMsg.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");
                    System.out.println("Sending hello.");
                    return helloMsg;
                } catch (JsonProcessingException e) {
                    throw new JMSException("boom");
                }
            }
        });
        System.out.println(rcvMsg.getBody(String.class));
    }


}
