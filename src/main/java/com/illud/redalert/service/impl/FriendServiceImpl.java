package com.illud.redalert.service.impl;

import com.illud.redalert.service.FriendService;
import com.illud.redalert.domain.Friend;
import com.illud.redalert.repository.FriendRepository;
import com.illud.redalert.service.dto.FriendDTO;
import com.illud.redalert.service.mapper.FriendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Friend.
 */
@Service
@Transactional
public class FriendServiceImpl implements FriendService {

    private final Logger log = LoggerFactory.getLogger(FriendServiceImpl.class);

    private final FriendRepository friendRepository;

    private final FriendMapper friendMapper;

    public FriendServiceImpl(FriendRepository friendRepository, FriendMapper friendMapper) {
        this.friendRepository = friendRepository;
        this.friendMapper = friendMapper;
    }

    /**
     * Save a friend.
     *
     * @param friendDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FriendDTO save(FriendDTO friendDTO) {
        log.debug("Request to save Friend : {}", friendDTO);
        Friend friend = friendMapper.toEntity(friendDTO);
        friend = friendRepository.save(friend);
        return friendMapper.toDto(friend);
    }

    /**
     * Get all the friends.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FriendDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Friends");
        return friendRepository.findAll(pageable)
            .map(friendMapper::toDto);
    }


    /**
     * Get one friend by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FriendDTO> findOne(Long id) {
        log.debug("Request to get Friend : {}", id);
        return friendRepository.findById(id)
            .map(friendMapper::toDto);
    }

    /**
     * Delete the friend by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Friend : {}", id);
        friendRepository.deleteById(id);
    }
}
