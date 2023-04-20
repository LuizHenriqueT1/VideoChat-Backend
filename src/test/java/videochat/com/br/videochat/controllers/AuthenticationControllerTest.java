package videochat.com.br.videochat.controllers;

import com.twilio.http.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import videochat.com.br.videochat.entities.dtos.RefreshTokenRequestDto;
import videochat.com.br.videochat.entities.dtos.TokenSuccessResponseDto;
import videochat.com.br.videochat.entities.dtos.UserLoginRequestDto;
import videochat.com.br.videochat.services.AuthenticationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class AuthenticationControllerTest {

    private AuthenticationController authenticationController;

    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        this.authenticationService = mock(AuthenticationService.class);
        this.authenticationController = new AuthenticationController(authenticationService);
    }

    @Test
    void loginSuccess() {
        // Prepare
        var userLoginRequestDto = mock(UserLoginRequestDto.class);
        var tokenSuccessResponseDto = mock(TokenSuccessResponseDto.class);
        when(authenticationService.login(userLoginRequestDto)).thenReturn(tokenSuccessResponseDto);

        // Call
        final var response = authenticationController.userLoginRequestHandler(userLoginRequestDto);

        // Verify
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(TokenSuccessResponseDto.class);
        assertThat(response.getBody()).isEqualTo(tokenSuccessResponseDto);
        verify(authenticationService, times(1)).login(userLoginRequestDto);
    }

    @Test
    void invalidCredentialGivenDuringLogin() {
        final String errorMessage = "Invalid login credentials provided";
        var userLoginRequestDto = mock(UserLoginRequestDto.class);
        when(authenticationService.login(userLoginRequestDto))
                .thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, errorMessage));

        final var response = Assertions.assertThrows(ResponseStatusException.class,
                () -> authenticationController.userLoginRequestHandler(userLoginRequestDto));
        assertThat(response.getMessage()).contains(errorMessage);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        verify(authenticationService).login(userLoginRequestDto);
    }

    @Test
    void refreshTokenSuccess() {
        var tokenSuccessResponseDto = mock(TokenSuccessResponseDto.class);
        var refreshTokenRequestDto = mock(RefreshTokenRequestDto.class);
        when(authenticationService.refreshToken(refreshTokenRequestDto))
                .thenReturn(tokenSuccessResponseDto);

        final var response = authenticationController.accessTokenRefreshRequestHandler(refreshTokenRequestDto);

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(TokenSuccessResponseDto.class);
        assertThat(response.getBody()).isEqualTo(tokenSuccessResponseDto);
        verify(authenticationService, times(1)).refreshToken(refreshTokenRequestDto);
    }

    @Test
    void refreshTokenExpired() {
        final String errorMessage = "Token expired";
        var refreshTokenRequestDto = mock(RefreshTokenRequestDto.class);
        when(authenticationService.refreshToken(refreshTokenRequestDto))
                .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, errorMessage));

        final var response = Assertions.assertThrows(ResponseStatusException.class,
                () -> authenticationController.accessTokenRefreshRequestHandler(refreshTokenRequestDto));

        assertThat(response.getMessage()).contains(errorMessage);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        verify(authenticationService, times(1)).refreshToken(refreshTokenRequestDto);
    }

}