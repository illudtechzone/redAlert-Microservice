package com.illud.redalert.service.impl;

import com.illud.redalert.service.FriendListService;
import com.illud.redalert.domain.FriendList;
import com.illud.redalert.repository.FriendListRepository;
import com.illud.redalert.service.dto.FriendListDTO;
import com.illud.redalert.service.mapper.FriendListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing FriendList.
 */
@Service
@Transactional
public class FriendListServiceImpl implements FriendListService {

    private final Logger log = LoggerFactory.getLogger(FriendListServiceImpl.class);

    private final FriendListRepository friendListRepository;

    private final FriendListMapper friendListMapper;

    public FriendListServiceImpl(FriendListRepository friendListRepository, FriendListMapper friendListMapper) {
        this.friendListRepository = friendListRepository;
        this.friendListMapper = friendListMapper;
    }

    /**
     * Save a friendList.
     *
     * @param friendListDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FriendListDTO save(FriendListDTO friendListDTO) {
        log.debug("Request to save FriendList : {}", friendListDTO);
        FriendList friendList = friendListMapper.toEntity(friendListDTO);
        friendList = friendListRepository.save(friendList);
        return friendListMapper.toDto(friendList);
    }

    /**
     * Get all the friendLists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FriendListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FriendLists");
        return friendListRepository.findAll(pageable)
            .map(friendListMapper::toDto);
    }


    /**
     * Get one friendList by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FriendListDTO> findOne(Long id) {
        log.debug("Request to get FriendList : {}", id);
        return friendListRepository.findById(id)
            .map(friendListMapper::toDto);
    }

    /**
     * Delete the friendList by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FriendList : {}", id);
        friendListRepository.deleteById(id);
    }
}
