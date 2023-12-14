package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.ProtocolService;
import com.cupme.service.dto.MyProtocolDetailDTO;
import com.cupme.service.dto.ProtocolCartDTO;
import com.cupme.service.dto.ProtocolDTO;
import com.cupme.service.dto.ProtocolDetailDTO;
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
     * {@code GET /protocol} : get all protocol with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all protocol.
     */
    @GetMapping("/protocol")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<ProtocolCartDTO>> getAllProtocols() {
        log.debug("REST request to get all public Protocol names");

        final List<ProtocolCartDTO> protocoles = protocolService.getProtocolCards();
        return ResponseEntity.ok().body(protocoles);
    }

    /**
     * {@code GET /protocol/:id} : get the "id" protocol.
     * @param id the id of the protocolDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the protocolDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/protocol/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<ProtocolDetailDTO> getProtocolDetail(@PathVariable long id) {
        log.debug("REST request to get Protocol : {}", id);

        final ProtocolDetailDTO protocolDetailDTO = protocolService.getProtocolDetail(id);
        return ResponseEntity.ok().body(protocolDetailDTO);
    }

    /**
     * {@code GET /myprotocol/:id} : get the "id" protocol.
     * @param id the id of the myProtocolDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the protocolDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/myprotocol/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<MyProtocolDetailDTO> getMyProtocol(@PathVariable long id) {
        log.debug("REST request to get Protocol : {}", id);

        final MyProtocolDetailDTO protocol = protocolService.getMyProtocol(id);
        return ResponseEntity.ok().body(protocol);
    }

    /**
     * {@code POST  /protocol} : Create a new protocol.
     *
     * @param protocolDTO the protocolDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new protocolDTO, or with status {@code 400 (Bad Request)} if the protocol has already an ID.
     */
    @PostMapping("/protocol")
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
     * {@code PUT  /protocol} : Updates an existing protocol.
     *
     * @param protocolDTO the protocolDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated protocolDTO,
     * or with status {@code 400 (Bad Request)} if the protocolDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the protocolDTO couldn't be updated.
     */
    @PutMapping("/protocol")
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
     * {@code DELETE  /protocol/:id} : delete the "id" protocol.
     *
     * @param id the id of the protocolDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/protocol/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteProtocol(long id) {
        log.debug("REST request to delete Protocol : {}", id);

        protocolService.deleteProtocol(id);
        return ResponseEntity.noContent().build();
    }
}
