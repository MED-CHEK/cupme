package com.cupme.service;

import com.cupme.repository.TagRepository;
import com.cupme.service.dto.TagDTO;
import com.cupme.service.mapper.TagMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing tags.
 */
@Service
@Transactional
public class TagService {

    private final Logger log = LoggerFactory.getLogger(TagService.class);

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;
    private final CacheManager cacheManager;

    public TagService(TagRepository tagRepository, TagMapper tagMapper, CacheManager cacheManager) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.cacheManager = cacheManager;
    }

    public List<TagDTO> getTags() {
        return tagMapper.tagsToTagDTOs(tagRepository.findAll());
    }

    public TagDTO getTag(long id) {
        return tagMapper.tagToTagDTO(tagRepository.findById(id).get());
    }

    public TagDTO createTag(TagDTO tagDTO) {
        return tagMapper.tagToTagDTO(tagRepository.save(tagMapper.tagDTOToTag(tagDTO)));
    }

    public TagDTO updateTag(TagDTO tagDTO) {
        return tagMapper.tagToTagDTO(tagRepository.save(tagMapper.tagDTOToTag(tagDTO)));
    }

    public void deleteTag(long id) {
        tagRepository.deleteById(id);
    }
}
