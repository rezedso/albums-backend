package com.example.albums.service.impl;

import com.example.albums.entity.User;
import com.example.albums.repository.UserRepository;
import com.example.albums.service.IUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilServiceImpl implements IUtilService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).get();
    }
}
