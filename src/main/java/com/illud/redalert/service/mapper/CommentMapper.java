package com.illud.redalert.service.mapper;

import com.illud.redalert.domain.*;
import com.illud.redalert.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Comment and its DTO CommentDTO.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "comment.id", target = "commentId")
    CommentDTO toDto(Comment comment);

    @Mapping(source = "postId", target = "post")
    @Mapping(source = "commentId", target = "comment")
    @Mapping(target = "replies", ignore = true)
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
