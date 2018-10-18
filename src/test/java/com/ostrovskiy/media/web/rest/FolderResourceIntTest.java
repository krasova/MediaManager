package com.ostrovskiy.media.web.rest;

import com.ostrovskiy.media.MediaManagerApp;

import com.ostrovskiy.media.domain.Folder;
import com.ostrovskiy.media.repository.FolderRepository;
import com.ostrovskiy.media.repository.search.FolderSearchRepository;
import com.ostrovskiy.media.service.FolderService;
import com.ostrovskiy.media.web.rest.errors.ExceptionTranslator;

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

import java.util.Collections;
import java.util.List;


import static com.ostrovskiy.media.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FolderResource REST controller.
 *
 * @see FolderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MediaManagerApp.class)
public class FolderResourceIntTest {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    @Autowired
    private FolderRepository folderRepository;



    @Autowired
    private FolderService folderService;

    /**
     * This repository is mocked in the com.ostrovskiy.media.repository.search test package.
     *
     * @see com.ostrovskiy.media.repository.search.FolderSearchRepositoryMockConfiguration
     */
    @Autowired
    private FolderSearchRepository mockFolderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFolderMockMvc;

    private Folder folder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FolderResource folderResource = new FolderResource(folderService);
        this.restFolderMockMvc = MockMvcBuilders.standaloneSetup(folderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Folder createEntity() {
        return new Folder()
            .path(DEFAULT_PATH);
    }

    @Before
    public void initTest() {
        folderRepository.deleteAll();
        folder = createEntity();
    }

    @Test
    public void createFolder() throws Exception {
        int databaseSizeBeforeCreate = folderRepository.findAll().size();

        // Create the Folder
        restFolderMockMvc.perform(post("/api/folders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(folder)))
            .andExpect(status().isCreated());

        // Validate the Folder in the database
        List<Folder> folderList = folderRepository.findAll();
        assertThat(folderList).hasSize(databaseSizeBeforeCreate + 1);
        Folder testFolder = folderList.get(folderList.size() - 1);
        assertThat(testFolder.getPath()).isEqualTo(DEFAULT_PATH);

        // Validate the Folder in Elasticsearch
        verify(mockFolderSearchRepository, times(1)).save(testFolder);
    }

    @Test
    public void createFolderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = folderRepository.findAll().size();

        // Create the Folder with an existing ID
        folder.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFolderMockMvc.perform(post("/api/folders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(folder)))
            .andExpect(status().isBadRequest());

        // Validate the Folder in the database
        List<Folder> folderList = folderRepository.findAll();
        assertThat(folderList).hasSize(databaseSizeBeforeCreate);

        // Validate the Folder in Elasticsearch
        verify(mockFolderSearchRepository, times(0)).save(folder);
    }

    @Test
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = folderRepository.findAll().size();
        // set the field null
        folder.setPath(null);

        // Create the Folder, which fails.

        restFolderMockMvc.perform(post("/api/folders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(folder)))
            .andExpect(status().isBadRequest());

        List<Folder> folderList = folderRepository.findAll();
        assertThat(folderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFolders() throws Exception {
        // Initialize the database
        folderRepository.save(folder);

        // Get all the folderList
        restFolderMockMvc.perform(get("/api/folders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(folder.getId())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)));
    }


    @Test
    public void getFolder() throws Exception {
        // Initialize the database
        folderRepository.save(folder);

        // Get the folder
        restFolderMockMvc.perform(get("/api/folders/{id}", folder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(folder.getId()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH));
    }
    @Test
    public void getNonExistingFolder() throws Exception {
        // Get the folder
        restFolderMockMvc.perform(get("/api/folders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFolder() throws Exception {
        // Initialize the database
        folderService.save(folder);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockFolderSearchRepository);

        int databaseSizeBeforeUpdate = folderRepository.findAll().size();

        // Update the folder
        Folder updatedFolder = folderRepository.findById(folder.getId()).get();
        updatedFolder
            .path(UPDATED_PATH);

        restFolderMockMvc.perform(put("/api/folders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFolder)))
            .andExpect(status().isOk());

        // Validate the Folder in the database
        List<Folder> folderList = folderRepository.findAll();
        assertThat(folderList).hasSize(databaseSizeBeforeUpdate);
        Folder testFolder = folderList.get(folderList.size() - 1);
        assertThat(testFolder.getPath()).isEqualTo(UPDATED_PATH);

        // Validate the Folder in Elasticsearch
        verify(mockFolderSearchRepository, times(1)).save(testFolder);
    }

    @Test
    public void updateNonExistingFolder() throws Exception {
        int databaseSizeBeforeUpdate = folderRepository.findAll().size();

        // Create the Folder

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFolderMockMvc.perform(put("/api/folders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(folder)))
            .andExpect(status().isBadRequest());

        // Validate the Folder in the database
        List<Folder> folderList = folderRepository.findAll();
        assertThat(folderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Folder in Elasticsearch
        verify(mockFolderSearchRepository, times(0)).save(folder);
    }

    @Test
    public void deleteFolder() throws Exception {
        // Initialize the database
        folderService.save(folder);

        int databaseSizeBeforeDelete = folderRepository.findAll().size();

        // Get the folder
        restFolderMockMvc.perform(delete("/api/folders/{id}", folder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Folder> folderList = folderRepository.findAll();
        assertThat(folderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Folder in Elasticsearch
        verify(mockFolderSearchRepository, times(1)).deleteById(folder.getId());
    }

    @Test
    public void searchFolder() throws Exception {
        // Initialize the database
        folderService.save(folder);
        when(mockFolderSearchRepository.search(queryStringQuery("id:" + folder.getId())))
            .thenReturn(Collections.singletonList(folder));
        // Search the folder
        restFolderMockMvc.perform(get("/api/_search/folders?query=id:" + folder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(folder.getId())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Folder.class);
        Folder folder1 = new Folder();
        folder1.setId("id1");
        Folder folder2 = new Folder();
        folder2.setId(folder1.getId());
        assertThat(folder1).isEqualTo(folder2);
        folder2.setId("id2");
        assertThat(folder1).isNotEqualTo(folder2);
        folder1.setId(null);
        assertThat(folder1).isNotEqualTo(folder2);
    }
}
