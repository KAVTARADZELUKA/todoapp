package com.example.todoapp.service;

import com.example.todoapp.dto.TodoRequestDto;
import com.example.todoapp.dto.TodoResponseDto;
import com.example.todoapp.exception.TodoNotFoundException;
import com.example.todoapp.model.Status;
import com.example.todoapp.model.TodoItem;
import com.example.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateTodo() {
        TodoRequestDto request = new TodoRequestDto("Test Title", "Test Description", Status.PENDING);
        TodoItem item = new TodoItem(null, "Test Title", "Test Description", Status.PENDING);

        when(todoRepository.save(any(TodoItem.class))).thenReturn(item);

        TodoResponseDto response = todoService.createTodoItem(request);

        assertEquals("Test Title", response.getTitle());
        assertEquals("Test Description", response.getDescription());
    }

    @Test
    void shouldGetTodoById() {
        TodoItem item = new TodoItem(1L, "Test Title", "Test Description", Status.PENDING);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(item));

        TodoResponseDto response = todoService.getTodoById(1L);

        assertEquals("Test Title", response.getTitle());
        assertEquals("Test Description", response.getDescription());
    }

    @Test
    void shouldUpdateTodo() {
        TodoItem existingTodo = new TodoItem(1L, "Old Title", "Old Description", Status.PENDING);
        TodoRequestDto updateRequestDto = new TodoRequestDto("Updated Title", "Updated Description", Status.COMPLETED);
        TodoItem updatedTodo = new TodoItem(1L, "Updated Title", "Updated Description", Status.COMPLETED);

        when(todoRepository.findById(1L)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(TodoItem.class))).thenReturn(updatedTodo);

        TodoResponseDto result = todoService.updateTodoItem(1L, updateRequestDto);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(Status.COMPLETED, result.getStatus());

        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).save(any(TodoItem.class));
    }

    @Test
    void shouldThrowExceptionWhenTodoNotFound() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.getTodoById(1L));
    }

    @Test
    void shouldDeleteTodo() {
        TodoItem item = new TodoItem(1L, "Test Title", "Test Description", Status.PENDING);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(item));

        todoService.deleteTodoItem(1L);

        verify(todoRepository, times(1)).deleteById(1L);
    }
}
