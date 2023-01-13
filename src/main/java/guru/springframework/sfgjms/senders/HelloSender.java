package guru.springframework.sfgjms.senders;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorld;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class HelloSender {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        System.out.println("I'm sending a message!");
        HelloWorld helloWorldMessage = HelloWorld
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, helloWorldMessage);

        System.out.println("Message sent!");
    }

}
