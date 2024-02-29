package com.example.albums.service;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.request.UpdatePasswordDto;
import com.example.albums.dto.request.UpdateUserDto;
import com.example.albums.dto.request.UserRoleDto;
import com.example.albums.dto.response.UpdatedUserDto;
import com.example.albums.dto.response.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


public interface IUserService {
    UserDto getUser(String username);
    List<UserDto>getUsers();
    MessageDto updatePassword(UpdatePasswordDto updatePasswordDto);
    UpdatedUserDto updateUser(UpdateUserDto updateUserDto, MultipartFile file) throws IOException;

    UserDto updateUserRole(UserRoleDto userRoleDto, UUID userId,boolean addRole);

    MessageDto deleteUser(UUID userId);
}
