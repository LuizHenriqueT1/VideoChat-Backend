package videochat.com.br.videochat.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import videochat.com.br.videochat.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String userId) {
        return convert(userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UsernameNotFoundException("Bad Credentials")));
    }

    private User convert(videochat.com.br.videochat.entities.User user) {
        return new User(user.getEmailId(), user.getPassword(), List.of());
    }

}


