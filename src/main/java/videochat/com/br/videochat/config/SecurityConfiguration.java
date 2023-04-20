package videochat.com.br.videochat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import videochat.com.br.videochat.security.constant.ApiPathExclusion;
import videochat.com.br.videochat.security.filter.JwtAuthenticationFilter;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.GET,
                                List.of(ApiPathExclusion.GetApiPathExclusion.values()).stream()
                                        .map(ApiPathExclusion.GetApiPathExclusion::getPath).toArray(String[]::new)).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                List.of(ApiPathExclusion.PostApiPathExclusion.values()).stream()
                                        .map(ApiPathExclusion.PostApiPathExclusion::getPath).toArray(String[]::new)).permitAll()
                        .requestMatchers(HttpMethod.PUT,
                                List.of(ApiPathExclusion.PutApiPathExclusion.values()).stream()
                                        .map(ApiPathExclusion.PutApiPathExclusion::getPath).toArray(String[]::new)).permitAll()
                        .anyRequest().authenticated()).addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}





