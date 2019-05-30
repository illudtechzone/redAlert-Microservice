package com.illud.redalert.service;

import com.illud.redalert.service.dto.UserDetailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserDetails.
 */
public interface UserDetailsService {

    /**
     * Save a userDetails.
     *
     * @param userDetailsDTO the entity to save
     * @return the persisted entity
     */
    UserDetailsDTO save(UserDetailsDTO userDetailsDTO);

    /**
     * Get all the userDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserDetailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userDetails.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" userDetails.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    Page<UserDetailsDTO> findByUserId(String userId, Pageable pageable);

	Page<UserDetailsDTO> findByFirstName(String firstName, Pageable pageable);

	Page<UserDetailsDTO> findByLastName(String lastName, Pageable pageable);

	Page<UserDetailsDTO> findByFirstNameContainingOrLastNameContaining(String searchName, Pageable pageable);
}
