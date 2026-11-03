package com.trainlog.security;

import com.trainlog.dto.requests.LoginRequestDTO;
import com.trainlog.dto.requests.UserRequestDTO;
import com.trainlog.dto.responses.LoginResponseDTO;
import com.trainlog.dto.responses.UserResponseDTO;
import com.trainlog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO login
            ){
        return ResponseEntity.ok().body(new LoginResponseDTO(
                "Bearer",
                authService.login(login.email(), login.password())
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @RequestBody UserRequestDTO user
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    new UserResponseDTO(userService.save(user)
                )
        );

    }

}
