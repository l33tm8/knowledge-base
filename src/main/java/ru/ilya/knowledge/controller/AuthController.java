package ru.ilya.knowledge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ilya.knowledge.dto.JwtAuthenticationResponse;
import ru.ilya.knowledge.dto.SignInRequest;
import ru.ilya.knowledge.dto.SignUpRequest;
import ru.ilya.knowledge.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public JwtAuthenticationResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest signInRequest) {
        return authenticationService.signIn(signInRequest);
    }
}
