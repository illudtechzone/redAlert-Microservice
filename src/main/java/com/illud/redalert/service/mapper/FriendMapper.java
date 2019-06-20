package com.illud.redalert.service.mapper;

import com.illud.redalert.domain.*;
import com.illud.redalert.service.dto.FriendDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Friend and its DTO FriendDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FriendMapper extends EntityMapper<FriendDTO, Friend> {



    default Friend fromId(Long id) {
        if (id == null) {
            return null;
        }
        Friend friend = new Friend();
        friend.setId(id);
        return friend;
    }
}
