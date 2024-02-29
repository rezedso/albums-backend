package com.example.albums.controller;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.response.ArtistDto;
import com.example.albums.dto.response.FollowerDto;
import com.example.albums.dto.response.FollowingArtistDto;
import com.example.albums.service.IFollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/followers")
@RequiredArgsConstructor
public class FollowerController {
    private final IFollowerService followerService;

    @GetMapping("/artists/{artistId}")
    ResponseEntity<List<FollowerDto>> getArtistFollowers(
            @PathVariable("artistId") UUID artistId
    ) {
        return ResponseEntity.ok(followerService.getArtistFollowers(artistId));
    }

    @GetMapping("/{username}")
    ResponseEntity<List<FollowingArtistDto>> getUserFollowingArtists(
            @PathVariable("username") String username
    ) {
        return ResponseEntity.ok(followerService.getUserFollowingArtists(username));
    }

    @GetMapping("/count/{artistId}")
    ResponseEntity<Long> getArtistFollowersCount(
            @PathVariable("artistId") UUID artistId
    ) {
        return ResponseEntity.ok(followerService.getArtistFollowersCount(artistId));
    }

    @GetMapping("/is-following/{artistId}")
    ResponseEntity<Boolean> isUserFollowing(
            @PathVariable("artistId") UUID artistId
    ) {
        return ResponseEntity.ok(followerService.isFollowing(artistId));
    }

    @PostMapping("/follow/{artistId}")
    ResponseEntity<FollowerDto>followArtist(
            @PathVariable("artistId")UUID artistId
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(followerService.followArtist(artistId));
    }

    @DeleteMapping("/unfollow/{artistId}")
    ResponseEntity<MessageDto>unFollowArtist(
            @PathVariable("artistId")UUID artistId
    ){
        return ResponseEntity.ok(followerService.unFollowArtist(artistId));
    }
}
