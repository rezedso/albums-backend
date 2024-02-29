package com.example.albums.service;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.request.CreateAlbumListDto;
import com.example.albums.dto.request.UpdateAlbumListDto;
import com.example.albums.dto.response.AlbumListDto;
import com.example.albums.dto.response.AlbumListSummaryDto;
import com.example.albums.dto.response.AlbumListsCountDto;

import java.util.List;
import java.util.UUID;

public interface IAlbumListService {
    AlbumListsCountDto getUserListsCount(String username);
    List<AlbumListSummaryDto> getUserListsSummary(String username);
    AlbumListDto getList(String listName, String username);
    AlbumListDto createAlbumList(CreateAlbumListDto createAlbumListDto);
    AlbumListDto updateAlbumList(UpdateAlbumListDto albumListRequestDto, UUID albumListId);
    MessageDto deleteAlbumList(UUID albumListId);
}
