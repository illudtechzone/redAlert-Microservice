package com.illud.redalert.web.rest;

import com.illud.redalert.RedAlertApp;

import com.illud.redalert.domain.FriendList;
import com.illud.redalert.repository.FriendListRepository;
import com.illud.redalert.service.FriendListService;
import com.illud.redalert.service.dto.FriendListDTO;
import com.illud.redalert.service.mapper.FriendListMapper;
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

/**
 * Test class for the FriendListResource REST controller.
 *
 * @see FriendListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedAlertApp.class)
public class FriendListResourceIntTest {

    private static final String DEFAULT_FRIEND_ID = "AAAAAAAAAA";
    private static final String UPDATED_FRIEND_ID = "BBBBBBBBBB";

    @Autowired
    private FriendListRepository friendListRepository;

    @Autowired
    private FriendListMapper friendListMapper;

    @Autowired
    private FriendListService friendListService;

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

    private MockMvc restFriendListMockMvc;

    private FriendList friendList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FriendListResource friendListResource = new FriendListResource(friendListService);
        this.restFriendListMockMvc = MockMvcBuilders.standaloneSetup(friendListResource)
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
    public static FriendList createEntity(EntityManager em) {
        FriendList friendList = new FriendList()
            .friendId(DEFAULT_FRIEND_ID);
        return friendList;
    }

    @Before
    public void initTest() {
        friendList = createEntity(em);
    }

    @Test
    @Transactional
    public void createFriendList() throws Exception {
        int databaseSizeBeforeCreate = friendListRepository.findAll().size();

        // Create the FriendList
        FriendListDTO friendListDTO = friendListMapper.toDto(friendList);
        restFriendListMockMvc.perform(post("/api/friend-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendListDTO)))
            .andExpect(status().isCreated());

        // Validate the FriendList in the database
        List<FriendList> friendListList = friendListRepository.findAll();
        assertThat(friendListList).hasSize(databaseSizeBeforeCreate + 1);
        FriendList testFriendList = friendListList.get(friendListList.size() - 1);
        assertThat(testFriendList.getFriendId()).isEqualTo(DEFAULT_FRIEND_ID);
    }

    @Test
    @Transactional
    public void createFriendListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = friendListRepository.findAll().size();

        // Create the FriendList with an existing ID
        friendList.setId(1L);
        FriendListDTO friendListDTO = friendListMapper.toDto(friendList);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFriendListMockMvc.perform(post("/api/friend-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FriendList in the database
        List<FriendList> friendListList = friendListRepository.findAll();
        assertThat(friendListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFriendLists() throws Exception {
        // Initialize the database
        friendListRepository.saveAndFlush(friendList);

        // Get all the friendListList
        restFriendListMockMvc.perform(get("/api/friend-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friendList.getId().intValue())))
            .andExpect(jsonPath("$.[*].friendId").value(hasItem(DEFAULT_FRIEND_ID.toString())));
    }
    
    @Test
    @Transactional
    public void getFriendList() throws Exception {
        // Initialize the database
        friendListRepository.saveAndFlush(friendList);

        // Get the friendList
        restFriendListMockMvc.perform(get("/api/friend-lists/{id}", friendList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(friendList.getId().intValue()))
            .andExpect(jsonPath("$.friendId").value(DEFAULT_FRIEND_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFriendList() throws Exception {
        // Get the friendList
        restFriendListMockMvc.perform(get("/api/friend-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFriendList() throws Exception {
        // Initialize the database
        friendListRepository.saveAndFlush(friendList);

        int databaseSizeBeforeUpdate = friendListRepository.findAll().size();

        // Update the friendList
        FriendList updatedFriendList = friendListRepository.findById(friendList.getId()).get();
        // Disconnect from session so that the updates on updatedFriendList are not directly saved in db
        em.detach(updatedFriendList);
        updatedFriendList
            .friendId(UPDATED_FRIEND_ID);
        FriendListDTO friendListDTO = friendListMapper.toDto(updatedFriendList);

        restFriendListMockMvc.perform(put("/api/friend-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendListDTO)))
            .andExpect(status().isOk());

        // Validate the FriendList in the database
        List<FriendList> friendListList = friendListRepository.findAll();
        assertThat(friendListList).hasSize(databaseSizeBeforeUpdate);
        FriendList testFriendList = friendListList.get(friendListList.size() - 1);
        assertThat(testFriendList.getFriendId()).isEqualTo(UPDATED_FRIEND_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingFriendList() throws Exception {
        int databaseSizeBeforeUpdate = friendListRepository.findAll().size();

        // Create the FriendList
        FriendListDTO friendListDTO = friendListMapper.toDto(friendList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendListMockMvc.perform(put("/api/friend-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FriendList in the database
        List<FriendList> friendListList = friendListRepository.findAll();
        assertThat(friendListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFriendList() throws Exception {
        // Initialize the database
        friendListRepository.saveAndFlush(friendList);

        int databaseSizeBeforeDelete = friendListRepository.findAll().size();

        // Delete the friendList
        restFriendListMockMvc.perform(delete("/api/friend-lists/{id}", friendList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FriendList> friendListList = friendListRepository.findAll();
        assertThat(friendListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FriendList.class);
        FriendList friendList1 = new FriendList();
        friendList1.setId(1L);
        FriendList friendList2 = new FriendList();
        friendList2.setId(friendList1.getId());
        assertThat(friendList1).isEqualTo(friendList2);
        friendList2.setId(2L);
        assertThat(friendList1).isNotEqualTo(friendList2);
        friendList1.setId(null);
        assertThat(friendList1).isNotEqualTo(friendList2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FriendListDTO.class);
        FriendListDTO friendListDTO1 = new FriendListDTO();
        friendListDTO1.setId(1L);
        FriendListDTO friendListDTO2 = new FriendListDTO();
        assertThat(friendListDTO1).isNotEqualTo(friendListDTO2);
        friendListDTO2.setId(friendListDTO1.getId());
        assertThat(friendListDTO1).isEqualTo(friendListDTO2);
        friendListDTO2.setId(2L);
        assertThat(friendListDTO1).isNotEqualTo(friendListDTO2);
        friendListDTO1.setId(null);
        assertThat(friendListDTO1).isNotEqualTo(friendListDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(friendListMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(friendListMapper.fromId(null)).isNull();
    }
}
