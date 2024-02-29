package com.example.albums.service.impl;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.request.CreateAlbumListDto;
import com.example.albums.dto.request.UpdateAlbumListDto;
import com.example.albums.dto.response.AlbumListDto;
import com.example.albums.dto.response.AlbumListSummaryDto;
import com.example.albums.dto.response.AlbumListsCountDto;
import com.example.albums.entity.AlbumList;
import com.example.albums.entity.User;
import com.example.albums.exception.ResourceNotFoundException;
import com.example.albums.repository.AlbumListRepository;
import com.example.albums.repository.UserRepository;
import com.example.albums.service.IAlbumListService;
import com.example.albums.service.IUtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumListServiceImpl implements IAlbumListService {
    private final AlbumListRepository albumListRepository;
    private final UserRepository userRepository;
    private final IUtilService utilService;
    private final ModelMapper modelMapper;

    @Override
    public AlbumListsCountDto getUserListsCount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Long listsCount = albumListRepository.getUserListsCount(user);

        return new AlbumListsCountDto(listsCount);
    }

    @Override
    public List<AlbumListSummaryDto> getUserListsSummary(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        List<AlbumList> albumLists = albumListRepository.findAlbumListsByUser(user.getId());

        return albumLists.stream().map(albumList ->
                modelMapper.map(albumList, AlbumListSummaryDto.class)).toList();
    }

    @Override
    public AlbumListDto getList(String listName, String username) {
        AlbumList albumList = albumListRepository.findByNameAndUserUsername(listName, username)
                .orElseThrow(() -> new ResourceNotFoundException("Album List not found."));

        return modelMapper.map(albumList, AlbumListDto.class);
    }

    @Override
    public AlbumListDto createAlbumList(CreateAlbumListDto request) {
        User user = utilService.getCurrentUser();

        AlbumList albumList = AlbumList.builder()
                .name(request.getName())
                .user(user)
                .build();

        albumListRepository.save(albumList);

        return modelMapper.map(albumList, AlbumListDto.class);
    }

    @Override
    @Transactional
    public AlbumListDto updateAlbumList(UpdateAlbumListDto request, UUID albumListId) {
        AlbumList albumList = albumListRepository.findById(albumListId)
                .orElseThrow(() -> new ResourceNotFoundException("Album List not found."));

        if (!albumList.getName().equals(request.getName())) {
            albumList.setName(request.getName());
        }

        return modelMapper.map(albumList, AlbumListDto.class);
    }

    @Override
    @Transactional
    public MessageDto deleteAlbumList(UUID albumListId) {
        albumListRepository.findById(albumListId)
                .orElseThrow(() -> new ResourceNotFoundException("Album List not found."));

        albumListRepository.deleteAlbumList(albumListId);
        return new MessageDto("Album List deleted.");
    }
}
