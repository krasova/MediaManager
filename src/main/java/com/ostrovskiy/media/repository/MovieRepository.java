package com.ostrovskiy.media.repository;

import com.ostrovskiy.media.domain.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Movie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

}
