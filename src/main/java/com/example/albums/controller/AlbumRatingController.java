package com.example.albums.controller;


import com.example.albums.dto.response.*;
import com.example.albums.service.IAlbumRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/album-ratings")
@RequiredArgsConstructor
public class AlbumRatingController {
    private final IAlbumRatingService albumRatingService;

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumRatingStatsDto> getAlbumRating(
            @PathVariable("albumId") UUID albumId
    ) {
        return ResponseEntity.ok(albumRatingService.getAlbumRating(albumId));
    }

    @GetMapping("/albums/{albumId}")
    public ResponseEntity<UserRatingDto> getUserAlbumRating(
            @PathVariable("albumId") UUID albumId
    ) {
        return ResponseEntity.ok(albumRatingService.getUserAlbumRating(albumId));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<RecentAlbumRatingDto>> getMostRecentAlbumRatings() {
        return ResponseEntity.ok(albumRatingService.getMostRecentAlbumRatings());
    }

    @GetMapping("/genre-overview/users/{username}")
    public ResponseEntity<List<GenreAlbumCountDto>> getUserGenreOverview(
            @PathVariable("username")String username
    ) {
        return ResponseEntity.ok(albumRatingService.getUserGenreOverview(username));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<AlbumRatingSummaryDto>> getAlbumRatingsByUser(
            @PathVariable("userId") UUID userId
    ) {
        return ResponseEntity.ok(albumRatingService.getAlbumRatingsByUser(userId));
    }

    @GetMapping("/users/{username}/rating/{rating}")
    public ResponseEntity<List<AlbumRatingCollectionDto>> getAlbumRatingsByUserAndRating(
            @PathVariable("username") String username,
            @PathVariable("rating") Double rating
    ) {
        return ResponseEntity.ok(albumRatingService.getAlbumRatingsByUserAndRating(username, rating));
    }
}
