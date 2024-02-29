package com.example.albums.controller;

import com.example.albums.auth.dto.response.MessageDto;
import com.example.albums.auth.service.JwtService;
import com.example.albums.auth.service.UserDetailsServiceImpl;
import com.example.albums.dto.request.CreateAlbumListDto;
import com.example.albums.dto.request.UpdateAlbumListDto;
import com.example.albums.dto.response.AlbumListDto;
import com.example.albums.dto.response.AlbumListSummaryDto;
import com.example.albums.dto.response.AlbumListsCountDto;
import com.example.albums.exception.ResourceNotFoundException;
import com.example.albums.repository.AlbumListRepository;
import com.example.albums.repository.TokenRepository;
import com.example.albums.repository.UserRepository;
import com.example.albums.service.IUtilService;
import com.example.albums.service.impl.AlbumListServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlbumListController.class)
@WithMockUser(username = "user", password = "test", roles = {"USER", "ADMIN"})
@AutoConfigureMockMvc(addFilters = false)
public class AlbumListControllerTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenRepository tokenRepository;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private IUtilService utilService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private AlbumListRepository albumListRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AlbumListServiceImpl albumListService;

    @Test
    public void getListsCount_Success() throws Exception {
        given(albumListService.getUserListsCount(anyString())).willReturn(new AlbumListsCountDto());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/album-lists/count/users/{username}", "user")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void getListsCount_WhenUserNotFound_ThrowsResourceNotFoundException() throws Exception {
        given(albumListService.getUserListsCount(anyString()))
                .willThrow(new ResourceNotFoundException("User not found"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/album-lists/count/users/{username}", "user")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUserListsSummary_Success() throws Exception {
        given(albumListService.getUserListsSummary(anyString()))
                .willReturn(List.of(new AlbumListSummaryDto()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/album-lists/summary/users/{username}", "user")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void getUserListsSummary_WhenUserNotFound_ThrowsResourceNotFoundException() throws Exception {
        given(albumListService.getUserListsSummary(anyString()))
                .willThrow(new ResourceNotFoundException("User not found"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/album-lists/summary/users/{username}", "user")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUserList_Success() throws Exception {
        given(albumListService.getList(anyString(), anyString()))
                .willReturn(new AlbumListDto());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/album-lists/{listName}/users/{username}", "list", "user")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void getUserList_WhenListNotFound_ThrowsResourceNotFoundException() throws Exception {
        given(albumListService.getList(anyString(), anyString()))
                .willThrow(new ResourceNotFoundException("List not found"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/album-lists/{listName}/users/{username}", "list", "user")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateList() throws Exception {
        CreateAlbumListDto createAlbumListDto = CreateAlbumListDto.builder()
                .name("list")
                .build();

        given(albumListService.createAlbumList(any(CreateAlbumListDto.class)))
                .willReturn(new AlbumListDto());


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/album-lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAlbumListDto));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateAlbumList_Success() throws Exception {
        UpdateAlbumListDto updateAlbumListDto = UpdateAlbumListDto.builder()
                .name("new name")
                .build();

        UUID listId = UUID.randomUUID();

        given(albumListService.updateAlbumList(any(UpdateAlbumListDto.class), eq(listId)))
                .willReturn(new AlbumListDto());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/album-lists/{albumListId}", listId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateAlbumListDto));


        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateAlbumList_WhenListNotFound_ThrowsResourceNotFoundException() throws Exception {
        UpdateAlbumListDto updateAlbumListDto = UpdateAlbumListDto.builder()
                .name("new name")
                .build();

        given(albumListService.updateAlbumList(any(UpdateAlbumListDto.class), any(UUID.class)))
                .willThrow(new ResourceNotFoundException("List not found"));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/album-lists/{albumListId}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateAlbumListDto));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAlbumList_Success() throws Exception {
        UUID listId = UUID.randomUUID();

        given(albumListService.deleteAlbumList(listId)).willReturn(new MessageDto("List deleted."));


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/album-lists/{listId}", listId)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("List deleted."));
    }

    @Test
    public void testDeleteAlbumList_WhenCommentNotFound_ThrowsResourceNotFoundException() throws Exception {
        given(albumListService.deleteAlbumList(any(UUID.class)))
                .willThrow(new ResourceNotFoundException("List not found."));

        mockMvc.perform(MockMvcRequestBuilders.delete("/album-lists/{listId}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
