package com.cupme.service.mapper;

import com.cupme.domain.Session;
import com.cupme.service.dto.SessionDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Session} and its DTO called {@link Session}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class SessionMapper {

    private final UserMapper userMapper;

    public SessionMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<SessionDTO> sessionsToSessionDTOs(List<Session> sessions) {
        return sessions.stream().filter(Objects::nonNull).map(this::sessionToSessionDTO).collect(Collectors.toList());
    }

    public SessionDTO sessionToSessionDTO(Session session) {
        return new SessionDTO(session);
    }

    public List<Session> sessionDTOsToSessions(List<SessionDTO> sessionDTOS) {
        return sessionDTOS.stream().filter(Objects::nonNull).map(this::sessionDTOToSession).collect(Collectors.toList());
    }

    public Session sessionDTOToSession(SessionDTO sessionDTO) {
        if (sessionDTO == null) {
            return null;
        } else {
            Session session = new Session();
            session.setId(sessionDTO.getId());
            session.setName(sessionDTO.getName());
            session.setDuration(sessionDTO.getDuration());
            session.setPrice(sessionDTO.getPrice());

            return session;
        }
    }
}
