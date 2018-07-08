package com.ostrovskiy.media.service.impl;

import com.ostrovskiy.media.service.MovieService;
import com.ostrovskiy.media.domain.Movie;
import com.ostrovskiy.media.repository.MovieRepository;
import com.ostrovskiy.media.repository.search.MovieSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Movie.
 */
@Service
public class MovieServiceImpl implements MovieService {

  private final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

  private final MovieRepository movieRepository;

  private final MovieSearchRepository movieSearchRepository;

  public MovieServiceImpl(MovieRepository movieRepository,
      MovieSearchRepository movieSearchRepository) {
    this.movieRepository = movieRepository;
    this.movieSearchRepository = movieSearchRepository;
  }

  /**
   * Save a movie.
   *
   * @param movie the entity to save
   * @return the persisted entity
   */
  @Override
  public Movie save(Movie movie) {
    log.debug("Request to save Movie : {}", movie);
    Movie result = movieRepository.save(movie);
    movieSearchRepository.save(result);
    return result;
  }

  /**
   * Get all the movies.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  @Override
  public Page<Movie> findAll(Pageable pageable) {
    log.debug("Request to get all Movies");
    return movieRepository.findAll(pageable);
  }


  /**
   * Get one movie by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Override
  public Optional<Movie> findOne(String id) {
    log.debug("Request to get Movie : {}", id);
    return movieRepository.findById(id);
  }

  /**
   * Delete the movie by id.
   *
   * @param id the id of the entity
   */
  @Override
  public void delete(String id) {
    log.debug("Request to delete Movie : {}", id);
    movieRepository.deleteById(id);
    movieSearchRepository.deleteById(id);
  }

  /**
   * Search for the movie corresponding to the query.
   *
   * @param query the query of the search
   * @param pageable the pagination information
   * @return the list of entities
   */
  @Override
  public Page<Movie> search(String query, Pageable pageable) {
    log.debug("Request to search for a page of Movies for query {}", query);
    return movieSearchRepository.search(queryStringQuery(query), pageable);
  }
}
