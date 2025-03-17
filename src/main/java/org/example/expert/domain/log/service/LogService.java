package org.example.expert.domain.log.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.log.LogRepository;
import org.example.expert.domain.log.entity.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logRequest(Long requesterId, Long todoId, Long managerId){
        logRepository.save(new Log(requesterId, todoId, managerId));
    }
}
