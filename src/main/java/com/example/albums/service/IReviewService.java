package com.example.albums.service;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.request.CreateReviewDto;
import com.example.albums.dto.request.UpdateReviewDto;
import com.example.albums.dto.response.PageDto;
import com.example.albums.dto.response.ReviewDto;

import java.util.List;
import java.util.UUID;

public interface IReviewService {
    PageDto<ReviewDto> getUserReviews(int page);
    List<ReviewDto> getMostRecentReviews();
    PageDto<ReviewDto> getAlbumReviews(UUID albumId, int page);
    boolean existsReview(UUID albumId);
    ReviewDto createReview(CreateReviewDto createReviewDto, UUID albumId);
    ReviewDto updateReview(UpdateReviewDto updateReviewDto, UUID reviewId);
    MessageDto deleteReview(UUID reviewId);
}
