package com.example.todoapp.repository;

import com.example.todoapp.model.Status;
import com.example.todoapp.model.TodoItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    void shouldSaveAndFindTodoById() {
        TodoItem item = new TodoItem(null, "Test Title", "Test Description", Status.PENDING);
        TodoItem savedItem = todoRepository.save(item);

        Optional<TodoItem> foundItem = todoRepository.findById(savedItem.getId());

        assertTrue(foundItem.isPresent());
        assertEquals("Test Title", foundItem.get().getTitle());
    }

    @Test
    void shouldDeleteTodo() {
        TodoItem item = new TodoItem(null, "Test Title", "Test Description", Status.PENDING);
        TodoItem savedItem = todoRepository.save(item);

        todoRepository.deleteById(savedItem.getId());

        Optional<TodoItem> foundItem = todoRepository.findById(savedItem.getId());
        assertFalse(foundItem.isPresent());
    }
}
