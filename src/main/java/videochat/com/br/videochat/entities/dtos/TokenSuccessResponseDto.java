package videochat.com.br.videochat.entities.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenSuccessResponseDto implements Serializable {

    private static final long serialVersionUID = -8_752_513_311_904_244_663L;

    private final String accessToken;
    private final String refreshToken;
    private final LocalDateTime expiresAt;
}
