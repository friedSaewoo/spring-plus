package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoSearchDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepositoryQuery {
    Optional<Todo> findByIdWithUser(Long todoId);

    Page<TodoSearchDto> searchTodo(String keyword, String nickName, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
