package com.example.albums.controller;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.dto.request.CreateAlbumListDto;
import com.example.albums.dto.request.UpdateAlbumListDto;
import com.example.albums.dto.response.*;
import com.example.albums.service.IAlbumListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/album-lists")
@RequiredArgsConstructor
public class AlbumListController {
    private final IAlbumListService albumListService;

    @GetMapping("/count/users/{username}")
    public ResponseEntity<AlbumListsCountDto> getUserListsCount(
            @PathVariable("username") String username
    ) {
        return ResponseEntity.ok(albumListService.getUserListsCount(username));
    }

    @GetMapping("/summary/users/{username}")
    public ResponseEntity<List<AlbumListSummaryDto>> getUserListsSummary(
            @PathVariable("username") String username
    ) {
        return ResponseEntity.ok(albumListService.getUserListsSummary(username));
    }

    @GetMapping("/{listName}/users/{username}")
    public ResponseEntity<AlbumListDto> getList(
            @PathVariable("listName") String listName,
            @PathVariable("username") String username
    ) {
        return ResponseEntity.ok(albumListService.getList(listName, username));
    }

    @PostMapping
    public ResponseEntity<AlbumListDto> createAlbumList(
            @RequestBody @Valid CreateAlbumListDto request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(albumListService.createAlbumList(request));
    }

    @PutMapping("/{albumListId}")
    public ResponseEntity<AlbumListDto> updateAlbumList(
            @RequestBody @Valid UpdateAlbumListDto updateAlbumListDto,
            @PathVariable("albumListId") UUID albumListId
    ) {
        return ResponseEntity.ok(albumListService.updateAlbumList(updateAlbumListDto, albumListId));
    }

    @DeleteMapping("/{albumListId}")
    public ResponseEntity<MessageDto> deleteAlbumList(
            @PathVariable("albumListId") UUID albumListId) {
        return ResponseEntity.ok(albumListService.deleteAlbumList(albumListId));
    }
}
