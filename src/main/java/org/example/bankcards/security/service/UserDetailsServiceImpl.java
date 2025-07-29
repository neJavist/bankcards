package org.example.bankcards.security.service;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.exception.custom_exceptions.UserNotFoundException;
import org.example.bankcards.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
