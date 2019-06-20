package com.illud.redalert.web.rest;

import com.illud.redalert.RedAlertApp;

import com.illud.redalert.domain.Friend;
import com.illud.redalert.repository.FriendRepository;
import com.illud.redalert.service.FriendService;
import com.illud.redalert.service.dto.FriendDTO;
import com.illud.redalert.service.mapper.FriendMapper;
import com.illud.redalert.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.illud.redalert.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.illud.redalert.domain.enumeration.Status;
/**
 * Test class for the FriendResource REST controller.
 *
 * @see FriendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedAlertApp.class)
public class FriendResourceIntTest {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FRIEND_ID = "AAAAAAAAAA";
    private static final String UPDATED_FRIEND_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACCEPTED = false;
    private static final Boolean UPDATED_ACCEPTED = true;

    private static final Status DEFAULT_STATUS = Status.PENDING;
    private static final Status UPDATED_STATUS = Status.ACCEPTED;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private FriendService friendService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFriendMockMvc;

    private Friend friend;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FriendResource friendResource = new FriendResource(friendService);
        this.restFriendMockMvc = MockMvcBuilders.standaloneSetup(friendResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Friend createEntity(EntityManager em) {
        Friend friend = new Friend()
            .userId(DEFAULT_USER_ID)
            .friendId(DEFAULT_FRIEND_ID)
            .accepted(DEFAULT_ACCEPTED)
            .status(DEFAULT_STATUS);
        return friend;
    }

    @Before
    public void initTest() {
        friend = createEntity(em);
    }

    @Test
    @Transactional
    public void createFriend() throws Exception {
        int databaseSizeBeforeCreate = friendRepository.findAll().size();

        // Create the Friend
        FriendDTO friendDTO = friendMapper.toDto(friend);
        restFriendMockMvc.perform(post("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendDTO)))
            .andExpect(status().isCreated());

        // Validate the Friend in the database
        List<Friend> friendList = friendRepository.findAll();
        assertThat(friendList).hasSize(databaseSizeBeforeCreate + 1);
        Friend testFriend = friendList.get(friendList.size() - 1);
        assertThat(testFriend.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testFriend.getFriendId()).isEqualTo(DEFAULT_FRIEND_ID);
        assertThat(testFriend.isAccepted()).isEqualTo(DEFAULT_ACCEPTED);
        assertThat(testFriend.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createFriendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = friendRepository.findAll().size();

        // Create the Friend with an existing ID
        friend.setId(1L);
        FriendDTO friendDTO = friendMapper.toDto(friend);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFriendMockMvc.perform(post("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Friend in the database
        List<Friend> friendList = friendRepository.findAll();
        assertThat(friendList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFriends() throws Exception {
        // Initialize the database
        friendRepository.saveAndFlush(friend);

        // Get all the friendList
        restFriendMockMvc.perform(get("/api/friends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friend.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].friendId").value(hasItem(DEFAULT_FRIEND_ID.toString())))
            .andExpect(jsonPath("$.[*].accepted").value(hasItem(DEFAULT_ACCEPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getFriend() throws Exception {
        // Initialize the database
        friendRepository.saveAndFlush(friend);

        // Get the friend
        restFriendMockMvc.perform(get("/api/friends/{id}", friend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(friend.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.friendId").value(DEFAULT_FRIEND_ID.toString()))
            .andExpect(jsonPath("$.accepted").value(DEFAULT_ACCEPTED.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFriend() throws Exception {
        // Get the friend
        restFriendMockMvc.perform(get("/api/friends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFriend() throws Exception {
        // Initialize the database
        friendRepository.saveAndFlush(friend);

        int databaseSizeBeforeUpdate = friendRepository.findAll().size();

        // Update the friend
        Friend updatedFriend = friendRepository.findById(friend.getId()).get();
        // Disconnect from session so that the updates on updatedFriend are not directly saved in db
        em.detach(updatedFriend);
        updatedFriend
            .userId(UPDATED_USER_ID)
            .friendId(UPDATED_FRIEND_ID)
            .accepted(UPDATED_ACCEPTED)
            .status(UPDATED_STATUS);
        FriendDTO friendDTO = friendMapper.toDto(updatedFriend);

        restFriendMockMvc.perform(put("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendDTO)))
            .andExpect(status().isOk());

        // Validate the Friend in the database
        List<Friend> friendList = friendRepository.findAll();
        assertThat(friendList).hasSize(databaseSizeBeforeUpdate);
        Friend testFriend = friendList.get(friendList.size() - 1);
        assertThat(testFriend.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testFriend.getFriendId()).isEqualTo(UPDATED_FRIEND_ID);
        assertThat(testFriend.isAccepted()).isEqualTo(UPDATED_ACCEPTED);
        assertThat(testFriend.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingFriend() throws Exception {
        int databaseSizeBeforeUpdate = friendRepository.findAll().size();

        // Create the Friend
        FriendDTO friendDTO = friendMapper.toDto(friend);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendMockMvc.perform(put("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Friend in the database
        List<Friend> friendList = friendRepository.findAll();
        assertThat(friendList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFriend() throws Exception {
        // Initialize the database
        friendRepository.saveAndFlush(friend);

        int databaseSizeBeforeDelete = friendRepository.findAll().size();

        // Delete the friend
        restFriendMockMvc.perform(delete("/api/friends/{id}", friend.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Friend> friendList = friendRepository.findAll();
        assertThat(friendList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Friend.class);
        Friend friend1 = new Friend();
        friend1.setId(1L);
        Friend friend2 = new Friend();
        friend2.setId(friend1.getId());
        assertThat(friend1).isEqualTo(friend2);
        friend2.setId(2L);
        assertThat(friend1).isNotEqualTo(friend2);
        friend1.setId(null);
        assertThat(friend1).isNotEqualTo(friend2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FriendDTO.class);
        FriendDTO friendDTO1 = new FriendDTO();
        friendDTO1.setId(1L);
        FriendDTO friendDTO2 = new FriendDTO();
        assertThat(friendDTO1).isNotEqualTo(friendDTO2);
        friendDTO2.setId(friendDTO1.getId());
        assertThat(friendDTO1).isEqualTo(friendDTO2);
        friendDTO2.setId(2L);
        assertThat(friendDTO1).isNotEqualTo(friendDTO2);
        friendDTO1.setId(null);
        assertThat(friendDTO1).isNotEqualTo(friendDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(friendMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(friendMapper.fromId(null)).isNull();
    }
}
