package com.ostrovskiy.media.repository;

import com.ostrovskiy.media.domain.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Picture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PictureRepository extends MongoRepository<Picture, String> {

}
