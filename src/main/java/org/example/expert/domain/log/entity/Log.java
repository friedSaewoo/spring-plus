package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;

@Getter
@Entity
@Table(name = "log")
@NoArgsConstructor
public class Log extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long requesterId;
    private Long todoId;
    private Long managerId;

    public Log(Long requesterId, Long todoId, Long managerId) {
        this.requesterId = requesterId;
        this.todoId = todoId;
        this.managerId = managerId;
    }
}
