package com.example.albums.service.impl;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.response.ArtistDto;
import com.example.albums.dto.response.FollowerDto;
import com.example.albums.dto.response.FollowingArtistDto;
import com.example.albums.entity.Artist;
import com.example.albums.entity.Follower;
import com.example.albums.entity.User;
import com.example.albums.exception.ResourceNotFoundException;
import com.example.albums.repository.ArtistRepository;
import com.example.albums.repository.FollowerRepository;
import com.example.albums.repository.UserRepository;
import com.example.albums.service.IFollowerService;
import com.example.albums.service.IUtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements IFollowerService {
    private final FollowerRepository followerRepository;
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final IUtilService utilService;
    private final ModelMapper modelMapper;

    @Override
    public FollowerDto followArtist(UUID artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found."));

        User user = utilService.getCurrentUser();

        Follower follower = Follower.builder()
                .artist(artist)
                .user(user)
                .build();

        followerRepository.save(follower);

        return modelMapper.map(follower, FollowerDto.class);
    }

    @Override
    public Boolean isFollowing(UUID artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found."));

        User user = utilService.getCurrentUser();

        return followerRepository.isUserFollowing(artist, user);
    }

    @Override
    @Transactional
    public MessageDto unFollowArtist(UUID artistId) {
        User user = utilService.getCurrentUser();

        Follower follower = followerRepository
                .findByArtistIdAndUserId(artistId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Follower not found."));

        followerRepository
                .deleteByArtistIdAndUserId(artistId, user.getId());
        return new MessageDto("\"" + follower.getArtist().getName() + "\" was unfollowed.");
    }

    @Override
    public List<FollowerDto> getArtistFollowers(UUID artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found."));

        List<Follower> artistFollowers = followerRepository.findArtistFollowers(artist);

        return artistFollowers.stream().map(follower ->
                modelMapper.map(follower, FollowerDto.class)).toList();
    }

    @Override
    public List<FollowingArtistDto> getUserFollowingArtists(String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        List<Artist> artistFollowers = followerRepository.findUserFollowingArtists(username);

        return artistFollowers.stream()
                .map(artist -> modelMapper.map(artist, FollowingArtistDto.class)).toList();

    }

    @Override
    public Long getArtistFollowersCount(UUID artistId) {
        artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found."));

        return followerRepository.countArtistFollowers(artistId);
    }

}
