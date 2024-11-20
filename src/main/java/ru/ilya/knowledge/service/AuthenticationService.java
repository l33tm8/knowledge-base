package ru.ilya.knowledge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ilya.knowledge.dto.JwtAuthenticationResponse;
import ru.ilya.knowledge.dto.SignInRequest;
import ru.ilya.knowledge.dto.SignUpRequest;
import ru.ilya.knowledge.entity.Role;
import ru.ilya.knowledge.entity.User;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public JwtAuthenticationResponse signUp(SignUpRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);
        return jwtService.generateToken(user);
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),
                signInRequest.getPassword()));

        UserDetails user = userService.getUserDetailsService().loadUserByUsername(signInRequest.getUsername());
        return jwtService.generateToken(user);
    }
}
