package com.example.todoapp.dto;

import com.example.todoapp.model.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequestDto {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private Status status;
}
