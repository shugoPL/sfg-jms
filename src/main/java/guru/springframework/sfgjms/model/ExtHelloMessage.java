package guru.springframework.sfgjms.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtHelloMessage implements Serializable {
    private UUID id;
    private String message;
}

