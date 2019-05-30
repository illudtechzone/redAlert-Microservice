package com.illud.redalert.service.mapper;

import com.illud.redalert.domain.*;
import com.illud.redalert.service.dto.FriendListDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FriendList and its DTO FriendListDTO.
 */
@Mapper(componentModel = "spring", uses = {UserDetailsMapper.class})
public interface FriendListMapper extends EntityMapper<FriendListDTO, FriendList> {

    @Mapping(source = "userDetails.id", target = "userDetailsId")
    FriendListDTO toDto(FriendList friendList);

    @Mapping(source = "userDetailsId", target = "userDetails")
    FriendList toEntity(FriendListDTO friendListDTO);

    default FriendList fromId(Long id) {
        if (id == null) {
            return null;
        }
        FriendList friendList = new FriendList();
        friendList.setId(id);
        return friendList;
    }
}
