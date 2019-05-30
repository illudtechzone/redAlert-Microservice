package com.illud.redalert.repository;

import com.illud.redalert.domain.UserDetails;
import com.illud.redalert.service.dto.UserDetailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
	
	Page<UserDetails> findByUserId(String userId, Pageable pageable);

	Page<UserDetails> findByFirstNameIgnoreCase(String firstName, Pageable pageable);

	Page<UserDetails> findByLastNameIgnoreCase(String lastName, Pageable pageable);

	
	  //Page<UserDetails> findByFirstNameContainingOrLastNameContaining(String
	  //firstName, String lastName, Pageable pageable);
	 

	Page<UserDetails> findByFirstNameContainingOrLastNameContaining(String firstName,String lastName, Pageable pageable);

	

	

}
