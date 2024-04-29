package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.SessionService;
import com.cupme.service.dto.SessionDTO;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SessionResource {

    private final Logger log = LoggerFactory.getLogger(SessionResource.class);

    private final SessionService sessionService;

    public SessionResource(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * {@code GET /sessions} : get all sessions with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all sessions.
     */
    @GetMapping("/sessions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<SessionDTO>> getAllSessions() {
        log.debug("REST request to get all Session");

        final List<SessionDTO> sessions = sessionService.getSessions();
        return ResponseEntity.ok().body(sessions);
    }

    /**
     * {@code GET /sessions/:id} : get the "id" session.
     * @param id the id of the sessionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sessionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sessions/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<SessionDTO> getSession(long id) {
        log.debug("REST request to get Session : {}", id);

        final SessionDTO session = sessionService.getSession(id);
        return ResponseEntity.ok().body(session);
    }

    /**
     * {@code POST  /sessions} : Create a new session.
     *
     * @param sessionDTO the sessionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sessionDTO, or with status {@code 400 (Bad Request)} if the session has already an ID.
     */
    @PostMapping("/sessions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<SessionDTO> createSession(@Valid @RequestBody SessionDTO sessionDTO) {
        log.debug("REST request to save Session : {}", sessionDTO);

        if (sessionDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final SessionDTO result = sessionService.createSession(sessionDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /sessions} : Updates an existing session.
     *
     * @param sessionDTO the sessionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sessionDTO,
     * or with status {@code 400 (Bad Request)} if the sessionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sessionDTO couldn't be updated.
     */
    @PutMapping("/sessions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<SessionDTO> updateSession(@Valid @RequestBody SessionDTO sessionDTO) {
        log.debug("REST request to update Session : {}", sessionDTO);

        if (sessionDTO.getId() == null) {
            return createSession(sessionDTO);
        }

        final SessionDTO result = sessionService.updateSession(sessionDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /sessions/:id} : delete the "id" session.
     *
     * @param id the id of the sessionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sessions/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteSession(@PathVariable long id) {
        log.debug("REST request to delete Session : {}", id);

        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
