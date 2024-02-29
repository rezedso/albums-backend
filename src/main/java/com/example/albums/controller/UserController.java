package com.example.albums.controller;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.request.UpdatePasswordDto;
import com.example.albums.dto.request.UpdateUserDto;
import com.example.albums.dto.request.UserRoleDto;
import com.example.albums.dto.response.UpdatedUserDto;
import com.example.albums.dto.response.UserDto;
import com.example.albums.service.IUserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable("username") String username
    ) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UpdatedUserDto> updateUser(
            @RequestPart(value = "user", required = false) @Parameter(schema = @Schema(type = "string", format = "binary"))
            @Valid UpdateUserDto updateUserDto,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(userService.updateUser(updateUserDto, file));
    }

    @PutMapping("/update-password")
    public ResponseEntity<MessageDto> updatePassword(
            @RequestBody UpdatePasswordDto updatePasswordDto
    ) {
        return ResponseEntity.ok(userService.updatePassword(updatePasswordDto));
    }

    @PutMapping("/{userId}/update-role")
    public ResponseEntity<UserDto> updateUserRole(
            @RequestBody @Valid UserRoleDto userRoleDto,
            @PathVariable("userId") UUID userId,
            @RequestParam("addRole") boolean addRole
    ) {
        return ResponseEntity.ok(userService.updateUserRole(userRoleDto, userId,addRole));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<MessageDto> deleteUser(
            @PathVariable("userId") UUID userId
    ) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
