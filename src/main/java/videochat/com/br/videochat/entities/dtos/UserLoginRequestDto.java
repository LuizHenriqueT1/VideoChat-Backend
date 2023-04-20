package videochat.com.br.videochat.entities.dtos;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@Jacksonized
public class UserLoginRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -3243809542585061706L;

    private final String emailId;
    private final String password;

}
