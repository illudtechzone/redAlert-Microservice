package com.illud.redalert.service.mapper;

import com.illud.redalert.domain.*;
import com.illud.redalert.service.dto.UserDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserDetails and its DTO UserDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserDetailsMapper extends EntityMapper<UserDetailsDTO, UserDetails> {


    @Mapping(target = "friends", ignore = true)
    UserDetails toEntity(UserDetailsDTO userDetailsDTO);

    default UserDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDetails userDetails = new UserDetails();
        userDetails.setId(id);
        return userDetails;
    }
}
