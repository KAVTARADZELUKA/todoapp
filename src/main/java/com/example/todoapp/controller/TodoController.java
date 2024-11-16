package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoRequestDto;
import com.example.todoapp.dto.TodoResponseDto;
import com.example.todoapp.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponseDto createTodoItem(@Valid @RequestBody TodoRequestDto requestDto) {
        return todoService.createTodoItem(requestDto);
    }

    @GetMapping
    public List<TodoResponseDto> getAllTodoItems() {
        return todoService.getAllTodoItems();
    }

    @GetMapping("/{id}")
    public TodoResponseDto getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    @PutMapping("/{id}")
    public TodoResponseDto updateTodoItem(@PathVariable Long id, @Valid @RequestBody TodoRequestDto requestDto) {
        return todoService.updateTodoItem(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodoItem(@PathVariable Long id) {
        todoService.deleteTodoItem(id);
    }
}