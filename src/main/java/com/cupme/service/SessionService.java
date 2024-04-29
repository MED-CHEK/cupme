package com.cupme.service;

import com.cupme.repository.SessionRepository;
import com.cupme.service.dto.SessionDTO;
import com.cupme.service.mapper.SessionMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing sessions.
 */
@Service
@Transactional
public class SessionService {

    private final Logger log = LoggerFactory.getLogger(SessionService.class);

    private final SessionRepository sessionRepository;

    private final SessionMapper sessionMapper;
    private final CacheManager cacheManager;

    public SessionService(SessionRepository sessionRepository, SessionMapper sessionMapper, CacheManager cacheManager) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
        this.cacheManager = cacheManager;
    }

    public List<SessionDTO> getSessions() {
        return sessionMapper.sessionsToSessionDTOs(sessionRepository.findAll());
    }

    public SessionDTO getSession(long id) {
        return sessionMapper.sessionToSessionDTO(sessionRepository.findById(id).get());
    }

    public SessionDTO createSession(SessionDTO sessionDTO) {
        return sessionMapper.sessionToSessionDTO(sessionRepository.save(sessionMapper.sessionDTOToSession(sessionDTO)));
    }

    public SessionDTO updateSession(SessionDTO sessionDTO) {
        return sessionMapper.sessionToSessionDTO(sessionRepository.save(sessionMapper.sessionDTOToSession(sessionDTO)));
    }

    public void deleteSession(long id) {
        sessionRepository.deleteById(id);
    }
}
