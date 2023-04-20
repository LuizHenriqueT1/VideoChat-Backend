package videochat.com.br.videochat.entities.dtos;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@Jacksonized
public class UserCreationRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String firstName;
    private final String lastName;
    private final String emailId;
    private final String password;
}
