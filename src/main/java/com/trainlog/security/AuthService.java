package com.trainlog.security;

import com.trainlog.exceptions.ResourceNotFoundException;
import com.trainlog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    public final AuthenticationManager authenticationManager;
    public final UserRepository userRepository;
    public final JwtService jwtService;


    public String login(String email, String password){
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                email,
                                password
                        )
                );
        var user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Usuário não encontrado")
                );
        return jwtService.generateToken(user.getId());
    }
}
