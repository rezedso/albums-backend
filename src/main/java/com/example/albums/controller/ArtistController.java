package com.example.albums.controller;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.request.CreateArtistDto;
import com.example.albums.dto.request.UpdateArtistDto;
import com.example.albums.dto.response.ArtistDto;
import com.example.albums.dto.response.PageDto;
import com.example.albums.dto.response.RecentArtistDto;
import com.example.albums.service.IArtistService;
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
import java.util.*;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {
    private final IArtistService artistService;

    @GetMapping("/page/{page}")
    public ResponseEntity<PageDto<ArtistDto>> getArtists(
            @PathVariable("page") int page
    ) {
        return ResponseEntity.ok(artistService.getArtists(page));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<RecentArtistDto>> getMostRecentArtists() {
        return ResponseEntity.ok(artistService.getMostRecentArtists());
    }

    @GetMapping("/genres/{genreSlug}/page/{page}")
    public ResponseEntity<PageDto<ArtistDto>> getArtistsByGenre(
            @PathVariable("genreSlug") String genreSlug,
            @PathVariable("page") int page
    ) {
        return ResponseEntity.ok(artistService.getArtistsByGenre(genreSlug, page));
    }

    @GetMapping("/{artistSlug}")
    public ResponseEntity<ArtistDto> getArtist(
            @PathVariable("artistSlug") String artistSlug
    ) {
        return ResponseEntity.ok(artistService.getArtist(artistSlug));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArtistDto> createArtist(
            @RequestPart("artist") @Valid CreateArtistDto request,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(artistService.createArtist(request, file));
    }

    @PutMapping(value = "/{artistId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArtistDto> updateArtist(
            @PathVariable("artistId") UUID artistId,
            @RequestPart(value = "artist", required = false) @Parameter(schema = @Schema(type = "string", format = "binary")) @Valid UpdateArtistDto updateArtistDto,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(artistService.updateArtist(updateArtistDto, file, artistId));
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<MessageDto> deleteArtist(
            @PathVariable("artistId") UUID artistId) {
        return ResponseEntity.ok(artistService.deleteArtist(artistId));
    }
}
