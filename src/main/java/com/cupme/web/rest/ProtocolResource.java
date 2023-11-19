package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.ProtocolService;
import com.cupme.service.dto.ProtocolDTO;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProtocolResource {

    private final Logger log = LoggerFactory.getLogger(ProtocolResource.class);

    private final ProtocolService protocolService;

    public ProtocolResource(ProtocolService protocolService) {
        this.protocolService = protocolService;
    }

    /**
     * {@code GET /protocols} : get all protocols with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all protocols.
     */
    @GetMapping("/protocols")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<ProtocolDTO>> getAllProtocols() {
        log.debug("REST request to get all public Protocol names");

        final List<ProtocolDTO> protocoles = protocolService.getProtocols();
        return ResponseEntity.ok().body(protocoles);
    }

    /**
     * {@code GET /protocols/:id} : get the "id" protocol.
     * @param id the id of the protocolDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the protocolDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/protocols/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<ProtocolDTO> getProtocol(long id) {
        log.debug("REST request to get Protocol : {}", id);

        final ProtocolDTO protocol = protocolService.getProtocol(id);
        return ResponseEntity.ok().body(protocol);
    }

    /**
     * {@code POST  /protocols} : Create a new protocol.
     *
     * @param protocolDTO the protocolDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new protocolDTO, or with status {@code 400 (Bad Request)} if the protocol has already an ID.
     */
    @PostMapping("/protocols")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<ProtocolDTO> createProtocol(@Valid @RequestBody ProtocolDTO protocolDTO) {
        log.debug("REST request to save Protocol : {}", protocolDTO);

        if (protocolDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final ProtocolDTO result = protocolService.createProtocol(protocolDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /protocols} : Updates an existing protocol.
     *
     * @param protocolDTO the protocolDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated protocolDTO,
     * or with status {@code 400 (Bad Request)} if the protocolDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the protocolDTO couldn't be updated.
     */
    @PutMapping("/protocols")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<ProtocolDTO> updateProtocol(@Valid @RequestBody ProtocolDTO protocolDTO) {
        log.debug("REST request to update Protocol : {}", protocolDTO);

        if (protocolDTO.getId() == null) {
            return createProtocol(protocolDTO);
        }

        final ProtocolDTO result = protocolService.updateProtocol(protocolDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /protocols/:id} : delete the "id" protocol.
     *
     * @param id the id of the protocolDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/protocols/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteProtocol(long id) {
        log.debug("REST request to delete Protocol : {}", id);

        protocolService.deleteProtocol(id);
        return ResponseEntity.noContent().build();
    }
}
