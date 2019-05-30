package com.illud.redalert.service.impl;

import com.illud.redalert.service.UserDetailsService;
import com.illud.redalert.domain.UserDetails;
import com.illud.redalert.repository.UserDetailsRepository;
import com.illud.redalert.service.dto.UserDetailsDTO;
import com.illud.redalert.service.mapper.UserDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing UserDetails.
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	private final UserDetailsRepository userDetailsRepository;

	private final UserDetailsMapper userDetailsMapper;

	public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository, UserDetailsMapper userDetailsMapper) {
		this.userDetailsRepository = userDetailsRepository;
		this.userDetailsMapper = userDetailsMapper;
	}

	/**
	 * Save a userDetails.
	 *
	 * @param userDetailsDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public UserDetailsDTO save(UserDetailsDTO userDetailsDTO) {
		log.debug("Request to save UserDetails : {}", userDetailsDTO);
		UserDetails userDetails = userDetailsMapper.toEntity(userDetailsDTO);
		userDetails = userDetailsRepository.save(userDetails);
		return userDetailsMapper.toDto(userDetails);
	}

	/**
	 * Get all the userDetails.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<UserDetailsDTO> findAll(Pageable pageable) {
		log.debug("Request to get all UserDetails");
		return userDetailsRepository.findAll(pageable).map(userDetailsMapper::toDto);
	}

	/**
	 * Get one userDetails by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<UserDetailsDTO> findOne(Long id) {
		log.debug("Request to get UserDetails : {}", id);
		return userDetailsRepository.findById(id).map(userDetailsMapper::toDto);
	}

	/**
	 * Delete the userDetails by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete UserDetails : {}", id);
		userDetailsRepository.deleteById(id);
	}

	@Override
	public Page<UserDetailsDTO> findByUserId(String userId, Pageable pageable) {
		log.debug("Request to getfindbyuserId UserDetails : {}", userId, pageable);

		Page<UserDetails> user = userDetailsRepository.findByUserId(userId, pageable);
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&7" + user.getContent());
		return user.map(userDetailsMapper::toDto);
	}

	@Override
	public Page<UserDetailsDTO> findByFirstName(String firstName, Pageable pageable) {
		log.debug("Request to getFindByFirstName UserDetails : {}", firstName, pageable);

		return userDetailsRepository.findByFirstNameIgnoreCase(firstName, pageable).map(userDetailsMapper::toDto);
	}

	@Override
	public Page<UserDetailsDTO> findByLastName(String lastName, Pageable pageable) {
		log.debug("Request to getFindByLastName UserDetails : {}", lastName, pageable);

		return userDetailsRepository.findByLastNameIgnoreCase(lastName, pageable).map(userDetailsMapper::toDto);
	}

	@Override
	public Page <UserDetailsDTO> findByFirstNameContainingOrLastNameContaining(String searchName,
			Pageable pageable) {
		log.debug("Request to getAllContainingNames UserDetails : {}", searchName, pageable);
		return userDetailsRepository.findByFirstNameContainingOrLastNameContaining(searchName,searchName, pageable).map(userDetailsMapper::toDto);
	}
}
