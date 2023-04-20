package videochat.com.br.videochat.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import videochat.com.br.videochat.entities.dtos.RefreshTokenRequestDto;
import videochat.com.br.videochat.entities.dtos.TokenSuccessResponseDto;
import videochat.com.br.videochat.entities.dtos.UserLoginRequestDto;
import videochat.com.br.videochat.services.AuthenticationService;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenSuccessResponseDto> userLoginRequestHandler(
            @Valid @RequestBody(required = true) final UserLoginRequestDto userLoginRequestDto) {
        return ResponseEntity.ok(authenticationService.login(userLoginRequestDto));
    }

    @PutMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TokenSuccessResponseDto> accessTokenRefreshRequestHandler(
            @Valid @RequestBody final RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequestDto));
    }
}
