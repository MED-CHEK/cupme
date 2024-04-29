package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.PromoCodeService;
import com.cupme.service.dto.PromoCodeDTO;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class PromoCodeResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "code", "discount", "activated", "createdDate")
    );
    private final Logger log = LoggerFactory.getLogger(PromoCodeResource.class);

    private final PromoCodeService promoCodeService;

    public PromoCodeResource(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    /**
     * {@code GET /promoCodes} : get all promoCodees with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all promoCodes.
     */
    @GetMapping("/promoCodes")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<PromoCodeDTO>> getAllPromoCodes(@ParameterObject Pageable pageable) {
        log.debug("REST request to get all public PromoCode");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<PromoCodeDTO> page = promoCodeService.getPromoCodes(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    /**
     * {@code GET /promoCodes/:id} : get the "id" promoCode.
     * @param id the id of the promoCodeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the promoCodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/promoCodes/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<PromoCodeDTO> getPromoCode(@PathVariable long id) {
        log.debug("REST request to get PromoCode : {}", id);

        final PromoCodeDTO promoCode = promoCodeService.getPromoCode(id);
        return ResponseEntity.ok().body(promoCode);
    }

    @GetMapping("/promoCodes/code/{code}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<PromoCodeDTO> getPromoCodeByCode(@PathVariable String code) {
        log.debug("REST request to get PromoCode by code : {}", code);

        final PromoCodeDTO promoCode = promoCodeService.getPromoCodeByCode(code);
        return ResponseEntity.ok().body(promoCode);
    }

    /**
     * {@code POST  /promoCodes} : Create a new promoCode.
     *
     * @param promoCodeDTO the promoCodeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new promoCodeDTO, or with status {@code 400 (Bad Request)} if the promoCode has already an ID.
     */
    @PostMapping("/promoCodes")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<PromoCodeDTO> createPromoCode(@Valid @RequestBody PromoCodeDTO promoCodeDTO) {
        log.debug("REST request to save PromoCode : {}", promoCodeDTO);

        if (promoCodeDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final PromoCodeDTO result = promoCodeService.createPromoCode(promoCodeDTO);

        if (result == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /promoCodes} : Updates an existing promoCode.
     *
     * @param promoCodeDTO the promoCodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated promoCodeDTO,
     * or with status {@code 400 (Bad Request)} if the promoCodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the promoCodeDTO couldn't be updated.
     */
    @PutMapping("/promoCodes")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<PromoCodeDTO> updatePromoCode(@Valid @RequestBody PromoCodeDTO promoCodeDTO) {
        log.debug("REST request to update PromoCode : {}", promoCodeDTO);

        if (promoCodeDTO.getId() == null) {
            return createPromoCode(promoCodeDTO);
        }

        final PromoCodeDTO result = promoCodeService.updatePromoCode(promoCodeDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /promoCodes/:id} : delete the "id" promoCode.
     *
     * @param id the id of the promoCodeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/promoCodes/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deletePromoCode(@PathVariable long id) {
        log.debug("REST request to delete PromoCode : {}", id);

        promoCodeService.deletePromoCode(id);
        return ResponseEntity.noContent().build();
    }
}
