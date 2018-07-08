package com.ostrovskiy.media.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ostrovskiy.media.domain.Movie;
import com.ostrovskiy.media.service.MovieService;
import com.ostrovskiy.media.web.rest.errors.BadRequestAlertException;
import com.ostrovskiy.media.web.rest.util.HeaderUtil;
import com.ostrovskiy.media.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing Movie.
 */
@RestController
@RequestMapping("/api")
public class MovieResource {

  private final Logger log = LoggerFactory.getLogger(MovieResource.class);

  private static final String ENTITY_NAME = "movie";

  private final MovieService movieService;

  public MovieResource(MovieService movieService) {
    this.movieService = movieService;
  }

  /**
   * POST /movies : Create a new movie.
   *
   * @param movie the movie to create
   * @return the ResponseEntity with status 201 (Created) and with body the new movie, or with
   *         status 400 (Bad Request) if the movie has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PostMapping("/movies")
  @Timed
  public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movie)
      throws URISyntaxException {
    log.debug("REST request to save Movie : {}", movie);
    if (movie.getId() != null) {
      throw new BadRequestAlertException("A new movie cannot already have an ID", ENTITY_NAME,
          "idexists");
    }
    Movie result = movieService.save(movie);
    return ResponseEntity.created(new URI("/api/movies/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * PUT /movies : Updates an existing movie.
   *
   * @param movie the movie to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated movie, or with status
   *         400 (Bad Request) if the movie is not valid, or with status 500 (Internal Server Error)
   *         if the movie couldn't be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PutMapping("/movies")
  @Timed
  public ResponseEntity<Movie> updateMovie(@Valid @RequestBody Movie movie)
      throws URISyntaxException {
    log.debug("REST request to update Movie : {}", movie);
    if (movie.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    Movie result = movieService.save(movie);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movie.getId().toString()))
        .body(result);
  }

  /**
   * GET /movies : get all the movies.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of movies in body
   */
  @GetMapping("/movies")
  @Timed
  public ResponseEntity<List<Movie>> getAllMovies(Pageable pageable) {
    log.debug("REST request to get a page of Movies");
    Page<Movie> page = movieService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movies");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  /**
   * GET /movies/:id : get the "id" movie.
   *
   * @param id the id of the movie to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the movie, or with status 404
   *         (Not Found)
   */
  @GetMapping("/movies/{id}")
  @Timed
  public ResponseEntity<Movie> getMovie(@PathVariable String id) {
    log.debug("REST request to get Movie : {}", id);
    Optional<Movie> movie = movieService.findOne(id);
    return ResponseUtil.wrapOrNotFound(movie);
  }

  /**
   * DELETE /movies/:id : delete the "id" movie.
   *
   * @param id the id of the movie to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/movies/{id}")
  @Timed
  public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
    log.debug("REST request to delete Movie : {}", id);
    movieService.delete(id);
    return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id))
        .build();
  }

  /**
   * SEARCH /_search/movies?query=:query : search for the movie corresponding to the query.
   *
   * @param query the query of the movie search
   * @param pageable the pagination information
   * @return the result of the search
   */
  @GetMapping("/_search/movies")
  @Timed
  public ResponseEntity<List<Movie>> searchMovies(@RequestParam String query, Pageable pageable) {
    log.debug("REST request to search for a page of Movies for query {}", query);
    Page<Movie> page = movieService.search(query, pageable);
    HttpHeaders headers =
        PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/movies");
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

}
