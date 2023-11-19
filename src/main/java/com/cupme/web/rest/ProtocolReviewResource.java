package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.ProtocolReviewService;
import com.cupme.service.dto.ProtocolReviewDTO;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProtocolReviewResource {

    private final Logger log = LoggerFactory.getLogger(ProtocolReviewResource.class);

    private final ProtocolReviewService protocolReviewService;

    public ProtocolReviewResource(ProtocolReviewService protocolReviewService) {
        this.protocolReviewService = protocolReviewService;
    }

    /**
     * {@code GET /protocolReviews} : get all protocolReviews with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all protocolReviews.
     */
    @GetMapping("/protocolReviews")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<ProtocolReviewDTO>> getAllProtocolReviews() {
        log.debug("REST request to get all public ProtocolReview names");

        final List<ProtocolReviewDTO> protocolReviewes = protocolReviewService.getProtocolReviews();
        return ResponseEntity.ok().body(protocolReviewes);
    }

    /**
     * {@code GET /protocolReviews/:id} : get the "id" protocolReview.
     * @param id the id of the protocolReviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the protocolReviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/protocolReviews/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<ProtocolReviewDTO> getProtocolReview(long id) {
        log.debug("REST request to get ProtocolReview : {}", id);

        final ProtocolReviewDTO protocolReview = protocolReviewService.getProtocolReview(id);
        return ResponseEntity.ok().body(protocolReview);
    }

    /**
     * {@code POST  /protocolReviews} : Create a new protocolReview.
     *
     * @param protocolReviewDTO the protocolReviewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new protocolReviewDTO, or with status {@code 400 (Bad Request)} if the protocolReview has already an ID.
     */
    @PostMapping("/protocolReviews")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<ProtocolReviewDTO> createProtocolReview(@Valid @RequestBody ProtocolReviewDTO protocolReviewDTO) {
        log.debug("REST request to save ProtocolReview : {}", protocolReviewDTO);

        if (protocolReviewDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final ProtocolReviewDTO result = protocolReviewService.createProtocolReview(protocolReviewDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /protocolReviews} : Updates an existing protocolReview.
     *
     * @param protocolReviewDTO the protocolReviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated protocolReviewDTO,
     * or with status {@code 400 (Bad Request)} if the protocolReviewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the protocolReviewDTO couldn't be updated.
     */
    @PutMapping("/protocolReviews")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<ProtocolReviewDTO> updateProtocolReview(@Valid @RequestBody ProtocolReviewDTO protocolReviewDTO) {
        log.debug("REST request to update ProtocolReview : {}", protocolReviewDTO);

        if (protocolReviewDTO.getId() == null) {
            return createProtocolReview(protocolReviewDTO);
        }

        final ProtocolReviewDTO result = protocolReviewService.updateProtocolReview(protocolReviewDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /protocolReviews/:id} : delete the "id" protocolReview.
     *
     * @param id the id of the protocolReviewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/protocolReviews/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteProtocolReview(long id) {
        log.debug("REST request to delete ProtocolReview : {}", id);

        protocolReviewService.deleteProtocolReview(id);
        return ResponseEntity.noContent().build();
    }
}
