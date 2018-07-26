package com.ostrovskiy.media.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ostrovskiy.media.domain.Folder;
import com.ostrovskiy.media.service.FolderService;
import com.ostrovskiy.media.web.rest.errors.BadRequestAlertException;
import com.ostrovskiy.media.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Folder.
 */
@RestController
@RequestMapping("/api")
public class FolderResource {

    private final Logger log = LoggerFactory.getLogger(FolderResource.class);

    private static final String ENTITY_NAME = "folder";

    private final FolderService folderService;

    public FolderResource(FolderService folderService) {
        this.folderService = folderService;
    }

    /**
     * POST  /folders : Create a new folder.
     *
     * @param folder the folder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new folder, or with status 400 (Bad Request) if the folder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/folders")
    @Timed
    public ResponseEntity<Folder> createFolder(@Valid @RequestBody Folder folder) throws URISyntaxException {
        log.debug("REST request to save Folder : {}", folder);
        if (folder.getId() != null) {
            throw new BadRequestAlertException("A new folder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Folder result = folderService.save(folder);
        return ResponseEntity.created(new URI("/api/folders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /folders : Updates an existing folder.
     *
     * @param folder the folder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated folder,
     * or with status 400 (Bad Request) if the folder is not valid,
     * or with status 500 (Internal Server Error) if the folder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/folders")
    @Timed
    public ResponseEntity<Folder> updateFolder(@Valid @RequestBody Folder folder) throws URISyntaxException {
        log.debug("REST request to update Folder : {}", folder);
        if (folder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Folder result = folderService.save(folder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, folder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /folders : get all the folders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of folders in body
     */
    @GetMapping("/folders")
    @Timed
    public List<Folder> getAllFolders() {
        log.debug("REST request to get all Folders");
        return folderService.findAll();
    }

    /**
     * GET  /folders/:id : get the "id" folder.
     *
     * @param id the id of the folder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the folder, or with status 404 (Not Found)
     */
    @GetMapping("/folders/{id}")
    @Timed
    public ResponseEntity<Folder> getFolder(@PathVariable String id) {
        log.debug("REST request to get Folder : {}", id);
        Optional<Folder> folder = folderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(folder);
    }

    /**
     * DELETE  /folders/:id : delete the "id" folder.
     *
     * @param id the id of the folder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/folders/{id}")
    @Timed
    public ResponseEntity<Void> deleteFolder(@PathVariable String id) {
        log.debug("REST request to delete Folder : {}", id);
        folderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/folders?query=:query : search for the folder corresponding
     * to the query.
     *
     * @param query the query of the folder search
     * @return the result of the search
     */
    @GetMapping("/_search/folders")
    @Timed
    public List<Folder> searchFolders(@RequestParam String query) {
        log.debug("REST request to search Folders for query {}", query);
        return folderService.search(query);
    }

}
