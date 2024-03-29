package com.example.albums.dto.response;

import com.example.albums.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDto {
    private UUID id;
    private String title;
    private String originCountry;
    private Instant releaseDate;
    private Double rating;
    private String albumImage;
    private String slug;
    private Set<Genre> genres;
    private UUID artistId;
    private String artistName;
    private String artistSlug;
}
