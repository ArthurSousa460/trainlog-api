package com.trainlog.service;

import com.trainlog.dto.requests.UserRequestDTO;
import com.trainlog.entity.User;
import com.trainlog.exceptions.BusinessException;
import com.trainlog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(UserRequestDTO user){
        Optional<User> userExist = userRepository.findByEmail(user.email());
        if(userExist.isPresent()) throw new BusinessException("Email já cadastrado!");
        User newUser = new User(
            user.name(),
            user.email(),
            passwordEncoder.encode(user.password())
        );
        return userRepository.save(newUser);
    }
}
