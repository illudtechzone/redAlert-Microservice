package com.illud.redalert.web.rest;
import com.illud.redalert.service.FriendListService;
import com.illud.redalert.web.rest.errors.BadRequestAlertException;
import com.illud.redalert.web.rest.util.HeaderUtil;
import com.illud.redalert.web.rest.util.PaginationUtil;
import com.illud.redalert.service.dto.FriendListDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FriendList.
 */
@RestController
@RequestMapping("/api")
public class FriendListResource {

    private final Logger log = LoggerFactory.getLogger(FriendListResource.class);

    private static final String ENTITY_NAME = "redAlertFriendList";

    private final FriendListService friendListService;

    public FriendListResource(FriendListService friendListService) {
        this.friendListService = friendListService;
    }

    /**
     * POST  /friend-lists : Create a new friendList.
     *
     * @param friendListDTO the friendListDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new friendListDTO, or with status 400 (Bad Request) if the friendList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/friend-lists")
    public ResponseEntity<FriendListDTO> createFriendList(@RequestBody FriendListDTO friendListDTO) throws URISyntaxException {
        log.debug("REST request to save FriendList : {}", friendListDTO);
        if (friendListDTO.getId() != null) {
            throw new BadRequestAlertException("A new friendList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FriendListDTO result = friendListService.save(friendListDTO);
        return ResponseEntity.created(new URI("/api/friend-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /friend-lists : Updates an existing friendList.
     *
     * @param friendListDTO the friendListDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated friendListDTO,
     * or with status 400 (Bad Request) if the friendListDTO is not valid,
     * or with status 500 (Internal Server Error) if the friendListDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/friend-lists")
    public ResponseEntity<FriendListDTO> updateFriendList(@RequestBody FriendListDTO friendListDTO) throws URISyntaxException {
        log.debug("REST request to update FriendList : {}", friendListDTO);
        if (friendListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FriendListDTO result = friendListService.save(friendListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, friendListDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /friend-lists : get all the friendLists.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of friendLists in body
     */
    @GetMapping("/friend-lists")
    public ResponseEntity<List<FriendListDTO>> getAllFriendLists(Pageable pageable) {
        log.debug("REST request to get a page of FriendLists");
        Page<FriendListDTO> page = friendListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/friend-lists");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /friend-lists/:id : get the "id" friendList.
     *
     * @param id the id of the friendListDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the friendListDTO, or with status 404 (Not Found)
     */
    @GetMapping("/friend-lists/{id}")
    public ResponseEntity<FriendListDTO> getFriendList(@PathVariable Long id) {
        log.debug("REST request to get FriendList : {}", id);
        Optional<FriendListDTO> friendListDTO = friendListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(friendListDTO);
    }

    /**
     * DELETE  /friend-lists/:id : delete the "id" friendList.
     *
     * @param id the id of the friendListDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/friend-lists/{id}")
    public ResponseEntity<Void> deleteFriendList(@PathVariable Long id) {
        log.debug("REST request to delete FriendList : {}", id);
        friendListService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
   // public ResponseEntity<List<FriendListDTO>> getAllDetails(@PathVariable String )
}
