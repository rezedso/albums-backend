package com.example.albums.service;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.response.FollowerDto;
import com.example.albums.dto.response.FollowingArtistDto;

import java.util.List;
import java.util.UUID;

public interface IFollowerService {
    FollowerDto followArtist(UUID artistId);
    Boolean isFollowing(UUID artistId);
    MessageDto unFollowArtist(UUID artistId);
    List<FollowerDto>getArtistFollowers(UUID artistId);
    List<FollowingArtistDto>getUserFollowingArtists(String username);
    Long getArtistFollowersCount(UUID artistId);
}
