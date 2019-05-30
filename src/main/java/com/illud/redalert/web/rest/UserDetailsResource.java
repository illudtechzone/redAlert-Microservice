package com.illud.redalert.web.rest;
import com.illud.redalert.service.UserDetailsService;
import com.illud.redalert.web.rest.errors.BadRequestAlertException;
import com.illud.redalert.web.rest.util.HeaderUtil;
import com.illud.redalert.web.rest.util.PaginationUtil;
import com.illud.redalert.service.dto.UserDetailsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserDetails.
 */
@RestController
@RequestMapping("/api")
public class UserDetailsResource {

    private final Logger log = LoggerFactory.getLogger(UserDetailsResource.class);

    private static final String ENTITY_NAME = "redAlertUserDetails";

    private final UserDetailsService userDetailsService;

    public UserDetailsResource(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * POST  /user-details : Create a new userDetails.
     *
     * @param userDetailsDTO the userDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userDetailsDTO, or with status 400 (Bad Request) if the userDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-details")
    public ResponseEntity<UserDetailsDTO> createUserDetails(@Valid @RequestBody UserDetailsDTO userDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save UserDetails : {}", userDetailsDTO);
        if (userDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDetailsDTO result = userDetailsService.save(userDetailsDTO);
        return ResponseEntity.created(new URI("/api/user-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-details : Updates an existing userDetails.
     *
     * @param userDetailsDTO the userDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userDetailsDTO,
     * or with status 400 (Bad Request) if the userDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the userDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-details")
    public ResponseEntity<UserDetailsDTO> updateUserDetails(@Valid @RequestBody UserDetailsDTO userDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update UserDetails : {}", userDetailsDTO);
        if (userDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserDetailsDTO result = userDetailsService.save(userDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-details : get all the userDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userDetails in body
     */
    @GetMapping("/user-details")
    public ResponseEntity<List<UserDetailsDTO>> getAllUserDetails(Pageable pageable) {
        log.debug("REST request to get a page of UserDetails");
        Page<UserDetailsDTO> page = userDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /user-details/:id : get the "id" userDetails.
     *
     * @param id the id of the userDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-details/{id}")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@PathVariable Long id) {
        log.debug("REST request to get UserDetails : {}", id);
        Optional<UserDetailsDTO> userDetailsDTO = userDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDetailsDTO);
    }

    /**
     * DELETE  /user-details/:id : delete the "id" userDetails.
     *
     * @param id the id of the userDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-details/{id}")
    public ResponseEntity<Void> deleteUserDetails(@PathVariable Long id) {
        log.debug("REST request to delete UserDetails : {}", id);
        userDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
	@GetMapping("/user-details-userId/{userId}")
	public ResponseEntity<List<UserDetailsDTO>> getAllDetailsByUserId(@PathVariable String userId, Pageable pageable) {
		log.debug("REST request to getAllDetailsByUserId : {}", userId);
		Page<UserDetailsDTO> page = userDetailsService.findByUserId(userId, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "api, user-details/{userId}");
		return ResponseEntity.ok().headers(headers).body(page.getContent());

	}
	@GetMapping("/user-details-firstName/{firstName}")
	public ResponseEntity<List<UserDetailsDTO>> getAllDetailsByFirstName(@PathVariable String firstName, Pageable pageable){
		log.debug("REST request to getAllDetailsByFrstName : {}", firstName, pageable);
		Page<UserDetailsDTO> page = userDetailsService.findByFirstName(firstName, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "api,user-details");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
		
	}
	@GetMapping("/user-details-lastName/{lastName}")
	public ResponseEntity<List<UserDetailsDTO>> getAllDetailsByLastName(@PathVariable String lastName, Pageable pageable){
		log.debug("REST request to getAllDetailsByFrstName : {}", lastName, pageable);
		Page<UserDetailsDTO> page = userDetailsService.findByLastName(lastName, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "api,user-details");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
		
	}
	@GetMapping("/user-details-searchName/{searchName}")
	public ResponseEntity<List<UserDetailsDTO>> getAllContainingNames( @PathVariable String searchName, Pageable pageable){
		log.debug("REST request to getAllContainingNames : {}", searchName, pageable);
		Page<UserDetailsDTO> page = userDetailsService.findByFirstNameContainingOrLastNameContaining(searchName,pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "api,user-details-searchName/{searchName}");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
			
	}
	
}
