package com.illud.redalert.service;

import com.illud.redalert.service.dto.FriendListDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing FriendList.
 */
public interface FriendListService {

    /**
     * Save a friendList.
     *
     * @param friendListDTO the entity to save
     * @return the persisted entity
     */
    FriendListDTO save(FriendListDTO friendListDTO);

    /**
     * Get all the friendLists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FriendListDTO> findAll(Pageable pageable);


    /**
     * Get the "id" friendList.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FriendListDTO> findOne(Long id);

    /**
     * Delete the "id" friendList.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
