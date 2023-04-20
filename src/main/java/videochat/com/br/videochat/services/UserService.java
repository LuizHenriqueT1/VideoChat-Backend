package videochat.com.br.videochat.services;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import videochat.com.br.videochat.entities.User;
import videochat.com.br.videochat.entities.dtos.UserCreationRequestDto;
import videochat.com.br.videochat.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void create(@NonNull UserCreationRequestDto userCreationRequestDto) {
        if(emailAlreadyTaken(userCreationRequestDto.getEmailId()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account width provided email already exists");

        final var user = new User();
        user.setFirstName(userCreationRequestDto.getFirstName());
        user.setLastName(userCreationRequestDto.getLastName());
        user.setEmailId(userCreationRequestDto.getEmailId());
        user.setPassword(passwordEncoder.encode(userCreationRequestDto.getPassword()));

        repository.save(user);
    }


    private boolean emailAlreadyTaken(final String emailId) {
        return repository.existsByEmailId(emailId);
    }
}
