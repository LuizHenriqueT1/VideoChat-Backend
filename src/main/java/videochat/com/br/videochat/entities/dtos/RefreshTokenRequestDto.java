package videochat.com.br.videochat.entities.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@Jacksonized
public class RefreshTokenRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 7278113015247374755L;

    @NotBlank(message = "Refresh token must not be empty")
    private final String refreshToken;
}
