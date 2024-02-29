package com.example.albums.controller;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.request.CreateAlbumDto;
import com.example.albums.dto.request.RateAlbumDto;
import com.example.albums.dto.request.UpdateAlbumDto;
import com.example.albums.dto.response.AlbumDto;
import com.example.albums.dto.response.PageDto;
import com.example.albums.dto.response.RecentAlbumDto;
import com.example.albums.service.IAlbumService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {
    private final IAlbumService albumService;

    @GetMapping("/page/{page}")
    public ResponseEntity<PageDto<AlbumDto>> getAlbums(
            @PathVariable("page") int page
    ) {
        return ResponseEntity.ok(albumService.getAlbums(page));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<RecentAlbumDto>> getMostRecentAlbums() {
        return ResponseEntity.ok(albumService.getMostRecentAlbums());
    }

    @GetMapping("/{artistSlug}/{albumSlug}")
    public ResponseEntity<AlbumDto> getAlbum(
            @PathVariable("artistSlug") String artistSlug,
            @PathVariable("albumSlug") String albumSlug
    ) {
        return ResponseEntity.ok(albumService.getAlbum(artistSlug, albumSlug));
    }

    @GetMapping("/artists/{artistSlug}")
    public ResponseEntity<List<AlbumDto>> getAlbumsByArtist(
            @PathVariable("artistSlug") String artistSlug
    ) {
        return ResponseEntity.ok(albumService.getAlbumsByArtist(artistSlug));
    }

    @GetMapping("/genres/{genreSlug}/page/{page}")
    public ResponseEntity<PageDto<AlbumDto>> getAlbumsByGenre(
            @PathVariable("genreSlug") String genreSlug,
            @PathVariable("page") int page
    ) {
        return ResponseEntity.ok(albumService.getAlbumsByGenre(genreSlug, page));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AlbumDto> createAlbum(
            @RequestPart("album") @Parameter(schema = @Schema(type = "string", format = "binary")) @Valid
            CreateAlbumDto request,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(albumService.createAlbum(request, file));
    }

    @PutMapping(value = "/{albumId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AlbumDto> updateAlbum(
            @PathVariable("albumId") UUID albumId,
            @RequestPart(value = "album", required = false) @Parameter(schema = @Schema(type = "string", format = "binary")) @Valid
            UpdateAlbumDto updateAlbumDto,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(albumService.updateAlbum(updateAlbumDto, file, albumId));
    }

    @PutMapping("/rate/{albumId}")
    public ResponseEntity<AlbumDto> rateAlbum(
            @RequestBody @Valid RateAlbumDto rateAlbumDto,
            @PathVariable("albumId") UUID albumId
    ) {
        return ResponseEntity.ok(albumService.rateAlbum(rateAlbumDto, albumId));
    }

    @PostMapping("/add/{listId}/{albumId}")
    public ResponseEntity<MessageDto> addAlbumToList(
            @PathVariable("listId") UUID listId,
            @PathVariable("albumId") UUID albumId
    ) {
        return ResponseEntity.ok(albumService.addAlbumToList(listId, albumId));
    }

    @DeleteMapping("/remove/{listId}/{albumId}")
    public ResponseEntity<MessageDto> removeAlbumFromList(
            @PathVariable("listId") UUID listId,
            @PathVariable("albumId") UUID albumId
    ) {
        return ResponseEntity.ok(albumService.removeAlbumFromList(listId, albumId));
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<MessageDto> deleteAlbum(
            @PathVariable("albumId") UUID albumId) {
        return ResponseEntity.ok(albumService.deleteAlbum(albumId));
    }
}
