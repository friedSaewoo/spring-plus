package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.response.QTodoSearchDto;
import org.example.expert.domain.todo.dto.response.TodoSearchDto;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class TodoRepositoryQueryImpl implements TodoRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        QTodo todo = QTodo.todo;
        QUser user = QUser.user;

        Todo result = jpaQueryFactory
                .selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<TodoSearchDto> searchTodo(String keyword, String nickName, LocalDateTime startDate
            ,LocalDateTime endDate, Pageable pageable) {
        QTodo todo = QTodo.todo;
        QManager manager = QManager.manager;
        QComment comment = QComment.comment;
        QUser user =QUser.user;

        List<TodoSearchDto> results = jpaQueryFactory
                .select(new QTodoSearchDto(
                    todo.title,
                    manager.id.countDistinct(),
                    comment.id.countDistinct()
                ))
                .from(todo)
                .leftJoin(manager).on(manager.todo.eq(todo))
                .leftJoin(comment).on(comment.todo.eq(todo))
                .leftJoin(user).on(manager.user.eq(user))
                .where(
                        keyword != null ? todo.title.contains(keyword) : null,
                        nickName != null ? user.nickName.contains(nickName) : null,
                        startDate != null ? todo.createdAt.goe(startDate) : null,
                        endDate != null ? todo.createdAt.loe(endDate) : null
                )
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(results, pageable, () -> jpaQueryFactory
                .select(todo.count())
                .from(todo)
                .fetchOne());
    }
}