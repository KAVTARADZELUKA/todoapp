package com.example.todoapp.service;

import com.example.todoapp.dto.TodoRequestDto;
import com.example.todoapp.dto.TodoResponseDto;
import com.example.todoapp.exception.TodoNotFoundException;
import com.example.todoapp.model.Status;
import com.example.todoapp.model.TodoItem;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoResponseDto createTodoItem(TodoRequestDto requestDto) {
        TodoItem item = new TodoItem();
        item.setTitle(requestDto.getTitle());
        item.setDescription(requestDto.getDescription());
        item.setStatus(requestDto.getStatus() != null ? requestDto.getStatus() : Status.PENDING);

        todoRepository.save(item);
        return mapToResponseDto(item);
    }

    public List<TodoResponseDto> getAllTodoItems() {
        return todoRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public TodoResponseDto updateTodoItem(Long id, TodoRequestDto requestDto) {
        TodoItem item = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo item not found"));

        item.setTitle(requestDto.getTitle());
        item.setDescription(requestDto.getDescription());
        if (requestDto.getStatus() != null) {
            item.setStatus(requestDto.getStatus());
        }
        todoRepository.save(item);

        return mapToResponseDto(item);
    }

    public void deleteTodoItem(Long id) {
        todoRepository.deleteById(id);
    }

    public TodoResponseDto mapToResponseDto(TodoItem item) {
        TodoResponseDto responseDto = new TodoResponseDto();
        responseDto.setId(item.getId());
        responseDto.setTitle(item.getTitle());
        responseDto.setDescription(item.getDescription());
        responseDto.setStatus(item.getStatus());
        return responseDto;
    }

    public TodoResponseDto getTodoById(Long id) {
        TodoItem item = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo item not found"));
        return mapToResponseDto(item);
    }
}
