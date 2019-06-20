package com.illud.redalert.web.rest;
import com.illud.redalert.service.FriendService;
import com.illud.redalert.web.rest.errors.BadRequestAlertException;
import com.illud.redalert.web.rest.util.HeaderUtil;
import com.illud.redalert.web.rest.util.PaginationUtil;
import com.illud.redalert.service.dto.FriendDTO;
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
 * REST controller for managing Friend.
 */
@RestController
@RequestMapping("/api")
public class FriendResource {

    private final Logger log = LoggerFactory.getLogger(FriendResource.class);

    private static final String ENTITY_NAME = "redAlertFriend";

    private final FriendService friendService;

    public FriendResource(FriendService friendService) {
        this.friendService = friendService;
    }

    /**
     * POST  /friends : Create a new friend.
     *
     * @param friendDTO the friendDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new friendDTO, or with status 400 (Bad Request) if the friend has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/friends")
    public ResponseEntity<FriendDTO> createFriend(@RequestBody FriendDTO friendDTO) throws URISyntaxException {
        log.debug("REST request to save Friend : {}", friendDTO);
        if (friendDTO.getId() != null) {
            throw new BadRequestAlertException("A new friend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FriendDTO result = friendService.save(friendDTO);
        return ResponseEntity.created(new URI("/api/friends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /friends : Updates an existing friend.
     *
     * @param friendDTO the friendDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated friendDTO,
     * or with status 400 (Bad Request) if the friendDTO is not valid,
     * or with status 500 (Internal Server Error) if the friendDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/friends")
    public ResponseEntity<FriendDTO> updateFriend(@RequestBody FriendDTO friendDTO) throws URISyntaxException {
        log.debug("REST request to update Friend : {}", friendDTO);
        if (friendDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FriendDTO result = friendService.save(friendDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, friendDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /friends : get all the friends.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of friends in body
     */
    @GetMapping("/friends")
    public ResponseEntity<List<FriendDTO>> getAllFriends(Pageable pageable) {
        log.debug("REST request to get a page of Friends");
        Page<FriendDTO> page = friendService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/friends");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /friends/:id : get the "id" friend.
     *
     * @param id the id of the friendDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the friendDTO, or with status 404 (Not Found)
     */
    @GetMapping("/friends/{id}")
    public ResponseEntity<FriendDTO> getFriend(@PathVariable Long id) {
        log.debug("REST request to get Friend : {}", id);
        Optional<FriendDTO> friendDTO = friendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(friendDTO);
    }

    /**
     * DELETE  /friends/:id : delete the "id" friend.
     *
     * @param id the id of the friendDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/friends/{id}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long id) {
        log.debug("REST request to delete Friend : {}", id);
        friendService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    //public ResponseEntity<FriendDTO> getFriend  
    
}
