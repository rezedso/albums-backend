package com.example.albums.controller;


import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.request.CreateReviewDto;
import com.example.albums.dto.request.UpdateReviewDto;
import com.example.albums.dto.response.PageDto;
import com.example.albums.dto.response.ReviewDto;
import com.example.albums.service.IReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final IReviewService reviewService;

    @GetMapping("/page/{page}")
    ResponseEntity<PageDto<ReviewDto>> getUserReviews(
            @PathVariable("page") int page
    ) {
        return ResponseEntity.ok(reviewService.getUserReviews(page));
    }

    @GetMapping("/recent")
    ResponseEntity<List<ReviewDto>> getMostRecentReviews() {
        return ResponseEntity.ok(reviewService.getMostRecentReviews());
    }

    @GetMapping("/albums/{albumId}/page/{page}")
    ResponseEntity<PageDto<ReviewDto>> getAlbumReviews(
            @PathVariable("albumId") UUID albumId,
            @PathVariable("page") int page
    ) {
        return ResponseEntity.ok(reviewService.getAlbumReviews(albumId, page));
    }

    @GetMapping("/exists-review/{albumId}")
    ResponseEntity<Boolean> existsReview(
            @PathVariable("albumId") UUID albumId
    ) {
        return ResponseEntity.ok(reviewService.existsReview(albumId));
    }

    @PostMapping("/albums/{albumId}")
    ResponseEntity<ReviewDto> createReview(
            @RequestBody @Valid CreateReviewDto createReviewDto,
            @PathVariable("albumId") UUID albumId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(createReviewDto, albumId));
    }

    @PutMapping("/{reviewId}")
    ResponseEntity<ReviewDto> updateReview(
            @RequestBody UpdateReviewDto updateReviewDto,
            @PathVariable("reviewId") UUID reviewId
    ) {
        return ResponseEntity.ok(reviewService.updateReview(updateReviewDto, reviewId));
    }

    @DeleteMapping("/{reviewId}")
    ResponseEntity<MessageDto> deleteReview(
            @PathVariable("reviewId") UUID reviewId
    ) {
        return ResponseEntity.ok(reviewService.deleteReview(reviewId));
    }
}
