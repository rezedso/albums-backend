package com.example.albums.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    @NotEmpty(message = "Username must not be empty.")
    @Size(min = 3, max = 40, message = "Username must be between 3 and 40 characters long.")
    private String username;
}
