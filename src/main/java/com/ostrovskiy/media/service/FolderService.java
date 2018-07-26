package com.ostrovskiy.media.service;

import com.ostrovskiy.media.domain.Folder;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Folder.
 */
public interface FolderService {

    /**
     * Save a folder.
     *
     * @param folder the entity to save
     * @return the persisted entity
     */
    Folder save(Folder folder);

    /**
     * Get all the folders.
     *
     * @return the list of entities
     */
    List<Folder> findAll();


    /**
     * Get the "id" folder.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Folder> findOne(String id);

    /**
     * Delete the "id" folder.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the folder corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Folder> search(String query);
}
