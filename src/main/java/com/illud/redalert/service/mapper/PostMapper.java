package com.illud.redalert.service.mapper;

import com.illud.redalert.domain.*;
import com.illud.redalert.service.dto.PostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Post and its DTO PostDTO.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(source = "location.id", target = "locationId")
    PostDTO toDto(Post post);

    @Mapping(source = "locationId", target = "location")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "reports", ignore = true)
    Post toEntity(PostDTO postDTO);

    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
