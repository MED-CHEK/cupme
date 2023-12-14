package com.cupme.service.mapper;

import com.cupme.domain.Tag;
import com.cupme.service.dto.TagDTO;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Tag} and its DTO called {@link Tag}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class TagMapper {

    public List<TagDTO> tagsToTagDTOs(List<Tag> tags) {
        return tags.stream().filter(Objects::nonNull).map(this::tagToTagDTO).collect(Collectors.toList());
    }

    public TagDTO tagToTagDTO(Tag tag) {
        return new TagDTO(tag);
    }

    public Set<Tag> tagDTOsToTags(Set<TagDTO> tagDTOS) {
        return tagDTOS.stream().filter(Objects::nonNull).map(this::tagDTOToTag).collect(Collectors.toSet());
    }

    public Tag tagDTOToTag(TagDTO tagDTO) {
        if (tagDTO == null) {
            return null;
        } else {
            Tag tag = new Tag();
            tag.setId(tagDTO.getId());
            tag.setName(tagDTO.getName());

            return tag;
        }
    }
}
