package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.TagService;
import com.cupme.service.dto.TagDTO;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TagResource {

    private final Logger log = LoggerFactory.getLogger(TagResource.class);

    private final TagService tagService;

    public TagResource(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * {@code GET /tags} : get all tags with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all tags.
     */
    @GetMapping("/tags")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<TagDTO>> getAllTags() {
        log.debug("REST request to get all public Tag names");

        final List<TagDTO> tages = tagService.getTags();
        return ResponseEntity.ok().body(tages);
    }

    /**
     * {@code GET /tags/:id} : get the "id" tag.
     * @param id the id of the tagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tags/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<TagDTO> getTag(long id) {
        log.debug("REST request to get Tag : {}", id);

        final TagDTO tag = tagService.getTag(id);
        return ResponseEntity.ok().body(tag);
    }

    /**
     * {@code POST  /tags} : Create a new tag.
     *
     * @param tagDTO the tagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagDTO, or with status {@code 400 (Bad Request)} if the tag has already an ID.
     */
    @PostMapping("/tags")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagDTO tagDTO) {
        log.debug("REST request to save Tag : {}", tagDTO);

        if (tagDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final TagDTO result = tagService.createTag(tagDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /tags} : Updates an existing tag.
     *
     * @param tagDTO the tagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagDTO,
     * or with status {@code 400 (Bad Request)} if the tagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagDTO couldn't be updated.
     */
    @PutMapping("/tags")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<TagDTO> updateTag(@Valid @RequestBody TagDTO tagDTO) {
        log.debug("REST request to update Tag : {}", tagDTO);

        if (tagDTO.getId() == null) {
            return createTag(tagDTO);
        }

        final TagDTO result = tagService.updateTag(tagDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /tags/:id} : delete the "id" tag.
     *
     * @param id the id of the tagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tags/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteTag(long id) {
        log.debug("REST request to delete Tag : {}", id);

        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
