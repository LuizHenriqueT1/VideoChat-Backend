package videochat.com.br.videochat.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import videochat.com.br.videochat.entities.dtos.RefreshTokenRequestDto;
import videochat.com.br.videochat.entities.dtos.TokenSuccessResponseDto;
import videochat.com.br.videochat.entities.dtos.UserLoginRequestDto;
import videochat.com.br.videochat.repositories.UserRepository;
import videochat.com.br.videochat.security.utility.JwtUtility;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtility jwtUtility;

    public TokenSuccessResponseDto login(@NonNull final UserLoginRequestDto userLoginRequestDto) {
        final var user = userRepository.findByEmailId(userLoginRequestDto.getEmailId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid login credentials provided"));

        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid login credentials provided");

        final var accessToken = jwtUtility.generateAccessToken(user);
        final var refreshToken = jwtUtility.generateRefreshToken(user);
        final var accessTokenExpirationTimestamp = jwtUtility.extractExpirationTimestamp(accessToken);

        return TokenSuccessResponseDto.builder().accessToken(accessToken).refreshToken(refreshToken)
                .expiresAt(accessTokenExpirationTimestamp).build();
    }

    public TokenSuccessResponseDto refreshToken(@NonNull final RefreshTokenRequestDto refreshTokenRequestDto) {

        if (jwtUtility.isTokenExpired(refreshTokenRequestDto.getRefreshToken()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token expired");

        final UUID userId = jwtUtility.extractUserId(refreshTokenRequestDto.getRefreshToken());
        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user-id provided"));

        final var accessToken = jwtUtility.generateAccessToken(user);
        final var accessTokenExpirationTimestamp = jwtUtility.extractExpirationTimestamp(accessToken);

        return TokenSuccessResponseDto.builder().accessToken(accessToken).expiresAt(accessTokenExpirationTimestamp)
                .build();
    }
}
