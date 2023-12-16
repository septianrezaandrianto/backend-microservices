package com.service.beauth.security.service;

import com.service.beauth.constant.Constant;
import com.service.beauth.entity.User;
import com.service.beauth.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(Objects.isNull(user)) {
            throw new UsernameNotFoundException(Constant.Message.NOT_FOUND_DATA_MESSAGE);
        }
        return UserDetailsImpl.build(user);
    }
}