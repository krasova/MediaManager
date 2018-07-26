package com.ostrovskiy.media.repository;

import com.ostrovskiy.media.domain.Folder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Folder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FolderRepository extends MongoRepository<Folder, String> {

}
