package com.example.albums.repository;

import com.example.albums.entity.Album;
import com.example.albums.entity.Review;
import com.example.albums.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    @Query("SELECT DISTINCT r FROM Review r WHERE r.user.id = :userId ORDER BY r.createdAt desc")
    Page<Review> findByUser(@Param("userId") UUID userId, Pageable p);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Review r WHERE r.album= :album AND r.user = :user")
    Boolean existsReviewByAlbumAndUser(@Param("album") Album album, @Param("user") User user);

    @Query("SELECT DISTINCT r FROM Review r WHERE r.album = :album")
    Page<Review> findByAlbum(@Param("album") Album album, Pageable of);

    @Query("SELECT r FROM Review r ORDER BY r.createdAt DESC")
    List<Review> findMostRecentReviews(Pageable pageable);
}
