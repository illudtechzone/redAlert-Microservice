package com.illud.redalert.service.mapper;

import com.illud.redalert.domain.*;
import com.illud.redalert.service.dto.MediaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Media and its DTO MediaDTO.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface MediaMapper extends EntityMapper<MediaDTO, Media> {

    @Mapping(source = "post.id", target = "postId")
    MediaDTO toDto(Media media);

    @Mapping(source = "postId", target = "post")
    Media toEntity(MediaDTO mediaDTO);

    default Media fromId(Long id) {
        if (id == null) {
            return null;
        }
        Media media = new Media();
        media.setId(id);
        return media;
    }
}
