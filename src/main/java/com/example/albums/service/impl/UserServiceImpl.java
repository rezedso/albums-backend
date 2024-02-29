package com.example.albums.service.impl;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.request.UpdatePasswordDto;
import com.example.albums.dto.request.UpdateUserDto;
import com.example.albums.dto.request.UserRoleDto;
import com.example.albums.dto.response.UpdatedUserDto;
import com.example.albums.dto.response.UserDto;
import com.example.albums.entity.Role;
import com.example.albums.entity.User;
import com.example.albums.enumeration.ERole;
import com.example.albums.exception.ResourceNotFoundException;
import com.example.albums.repository.AlbumListRepository;
import com.example.albums.repository.RoleRepository;
import com.example.albums.repository.UserRepository;
import com.example.albums.service.IFileUploadService;
import com.example.albums.service.IUserService;
import com.example.albums.service.IUtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final AlbumListRepository albumListRepository;
    private final RoleRepository roleRepository;
    private final IFileUploadService fileUploadService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final IUtilService utilService;

    @Override
    public UserDto getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
    }

    @Override
    public MessageDto updatePassword(UpdatePasswordDto request) {
        var user = utilService.getCurrentUser();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password.");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords don't match.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return new MessageDto("Password updated.");
    }

    @Override
    public UpdatedUserDto updateUser(UpdateUserDto updateUserDto, MultipartFile file) throws IOException {
        var user = utilService.getCurrentUser();

        if (updateUserDto.getUsername() != null &&
                !Objects.equals(user.getUsername(), updateUserDto.getUsername())) {
            user.setUsername(updateUserDto.getUsername());
        }

        if (file != null) {
            String imageUrl = fileUploadService.uploadUserImageFile(file);
            user.setImageUrl(imageUrl);
        }

        userRepository.save(user);
        return new UpdatedUserDto(user.getUsername(), user.getEmail(), user.getImageUrl());
    }

    @Override
    @Transactional
    public UserDto updateUserRole(UserRoleDto userRoleDto, UUID userId, boolean addRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        ERole role = ERole.valueOf(userRoleDto.getRole());

        Optional<Role> existingRoleOpt = roleRepository.findByName(role);
        Role existingRole = existingRoleOpt.orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName(role);
            return roleRepository.save(newRole);
        });

        if (addRole) {
            user.getRoles().add(existingRole);
        } else {
            user.getRoles().remove(existingRole);
        }

        userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public MessageDto deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        albumListRepository.deleteAllAlbumListsByUser(user);
        userRepository.deleteById(userId);

        return new MessageDto("User deleted.");
    }
}
