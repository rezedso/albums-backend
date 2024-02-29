package com.example.albums.service;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.request.CreateAlbumDto;
import com.example.albums.dto.request.RateAlbumDto;
import com.example.albums.dto.request.UpdateAlbumDto;
import com.example.albums.dto.response.AlbumDto;
import com.example.albums.dto.response.PageDto;
import com.example.albums.dto.response.RecentAlbumDto;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IAlbumService {
    PageDto<AlbumDto> getAlbums(int page);

    AlbumDto getAlbum(String artistSlug, String albumSlug);

    List<RecentAlbumDto> getMostRecentAlbums();

    List<AlbumDto> getAlbumsByArtist(String artistSlug);

    PageDto<AlbumDto> getAlbumsByGenre(String slug, int page);

    AlbumDto createAlbum(CreateAlbumDto createAlbumDto, MultipartFile file) throws IOException;

    AlbumDto rateAlbum(RateAlbumDto rateAlbumDto, UUID albumId);

    AlbumDto updateAlbum(UpdateAlbumDto updateAlbumDto, MultipartFile file, UUID albumId) throws IOException;

    MessageDto addAlbumToList(UUID listId, UUID albumId);

    MessageDto removeAlbumFromList(UUID listId, UUID albumId);

    MessageDto deleteAlbum(UUID albumId);
}
