package ru.ilya.knowledge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ilya.knowledge.entity.User;
import ru.ilya.knowledge.exception.UserAlreadyExistsException;
import ru.ilya.knowledge.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException();
        }
        userRepository.save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public UserDetailsService getUserDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
}
