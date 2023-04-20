package videochat.com.br.videochat.entities.dtos;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Getter
@Builder
@Jacksonized
public class UserDetailDto {

    private final String firstName;
    private final String lastName;
    private final String emailId;
    private final LocalDateTime createdAt;
}
