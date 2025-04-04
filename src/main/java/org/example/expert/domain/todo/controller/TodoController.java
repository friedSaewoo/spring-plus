package org.example.expert.domain.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.dto.response.TodoSearchDto;
import org.example.expert.domain.todo.service.TodoService;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @Secured(UserRole.Authority.USER)
    @PostMapping("/todos")
    public ResponseEntity<TodoSaveResponse> saveTodo(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody TodoSaveRequest todoSaveRequest
    ) {
        return ResponseEntity.ok(todoService.saveTodo(authUser, todoSaveRequest));
    }
    @Secured(UserRole.Authority.USER)
    @GetMapping("/todos")
    public ResponseEntity<Page<TodoResponse>> getTodos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String weather,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate
            ) {
        return ResponseEntity.ok(todoService.getTodos(page, size, weather, startDate, endDate));
    }
    @Secured(UserRole.Authority.USER)
    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(@PathVariable long todoId) {
            return ResponseEntity.ok(todoService.getTodo(todoId));
    }

    @Secured(UserRole.Authority.USER)
    @GetMapping("/todos/search")
    public ResponseEntity<Page<TodoSearchDto>> searchTodos(@RequestParam(required = false) String keyword,
                                                           @RequestParam(required = false) String nickName,
                                                           @RequestParam(required = false) LocalDateTime startDate,
                                                           @RequestParam(required = false) LocalDateTime endDate,
                                                           Pageable pageable){

        return ResponseEntity.ok(todoService.searchTodo(keyword, nickName, startDate, endDate, pageable));
    }
}
