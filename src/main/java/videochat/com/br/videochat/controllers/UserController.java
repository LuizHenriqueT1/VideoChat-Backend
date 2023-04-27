package videochat.com.br.videochat.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import videochat.com.br.videochat.entities.dtos.UserCreationRequestDto;
import videochat.com.br.videochat.services.UserService;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> userCreationHandler(
            @Valid @RequestBody() final UserCreationRequestDto userCreationRequestDto) {
        userService.create(userCreationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
