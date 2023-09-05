package com.test.testing.services;


import com.test.testing.domain.Entity.UserEntity;
import com.test.testing.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + username));

        return build(userEntity);
    }

    public UserEntity loadUserById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }


    public static UserEntity build(UserEntity user) {
        return new UserEntity(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

}
