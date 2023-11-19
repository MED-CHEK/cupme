package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.AddressService;
import com.cupme.service.dto.AddressDTO;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    private final AddressService addressService;

    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * {@code GET /addresses} : get all addresses with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all addresses.
     */
    @GetMapping("/addresses")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        log.debug("REST request to get all public Address names");

        final List<AddressDTO> addresses = addressService.getAddresses();
        return ResponseEntity.ok().body(addresses);
    }

    /**
     * {@code GET /addresses/:id} : get the "id" address.
     * @param id the id of the addressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/addresses/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<AddressDTO> getAddress(long id) {
        log.debug("REST request to get Address : {}", id);

        final AddressDTO address = addressService.getAddress(id);
        return ResponseEntity.ok().body(address);
    }

    /**
     * {@code POST  /addresses} : Create a new address.
     *
     * @param addressDTO the addressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addressDTO, or with status {@code 400 (Bad Request)} if the address has already an ID.
     */
    @PostMapping("/addresses")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        log.debug("REST request to save Address : {}", addressDTO);

        if (addressDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final AddressDTO result = addressService.createAddress(addressDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /addresses} : Updates an existing address.
     *
     * @param addressDTO the addressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressDTO,
     * or with status {@code 400 (Bad Request)} if the addressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addressDTO couldn't be updated.
     */
    @PutMapping("/addresses")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<AddressDTO> updateAddress(@Valid @RequestBody AddressDTO addressDTO) {
        log.debug("REST request to update Address : {}", addressDTO);

        if (addressDTO.getId() == null) {
            return createAddress(addressDTO);
        }

        final AddressDTO result = addressService.updateAddress(addressDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /addresses/:id} : delete the "id" address.
     *
     * @param id the id of the addressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/addresses/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteAddress(long id) {
        log.debug("REST request to delete Address : {}", id);

        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
