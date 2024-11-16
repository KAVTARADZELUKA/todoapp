package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoRequestDto;
import com.example.todoapp.dto.TodoResponseDto;
import com.example.todoapp.model.Status;
import com.example.todoapp.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetTodoById() throws Exception {
        TodoResponseDto response = new TodoResponseDto(1L, "Test Title", "Test Description", Status.PENDING);

        when(todoService.getTodoById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void shouldCreateTodo() throws Exception {
        TodoRequestDto request = new TodoRequestDto("Test Title", "Test Description", Status.PENDING);
        TodoResponseDto response = new TodoResponseDto(1L, "Test Title", "Test Description", Status.PENDING);

        when(todoService.createTodoItem(any(TodoRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void shouldUpdateTodoItem() throws Exception {
        TodoRequestDto updateRequestDto = new TodoRequestDto("Updated Title", "Updated Description", Status.COMPLETED);
        TodoResponseDto expectedResponse = new TodoResponseDto(1L, "Updated Title", "Updated Description", Status.COMPLETED);

        when(todoService.updateTodoItem(1L, updateRequestDto)).thenReturn(expectedResponse);

        mockMvc.perform(put("/api/todos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Title\", \"description\": \"Updated Description\", \"status\": \"COMPLETED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.status").value("COMPLETED"));

        verify(todoService, times(1)).updateTodoItem(1L, updateRequestDto);
    }

    @Test
    void shouldDeleteTodo() throws Exception {
        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());

        verify(todoService, times(1)).deleteTodoItem(1L);
    }
}
