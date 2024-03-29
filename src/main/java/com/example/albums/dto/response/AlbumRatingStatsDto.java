package com.example.albums.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumRatingStatsDto {
    private Double rating;
    private Long totalRatings;
}
