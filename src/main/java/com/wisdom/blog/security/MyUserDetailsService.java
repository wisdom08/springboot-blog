package com.wisdom.blog.security;

import com.wisdom.blog.model.User;
import com.wisdom.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("userId : " + username + " was not found"));

        return createUserDetails(user);
    }

    private UserDetails createUserDetails(User user) {
        List<SimpleGrantedAuthority> grantedAuthorities = user.getRoleList().stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUserId(),
                user.getPw(),
                grantedAuthorities);
    }
}

