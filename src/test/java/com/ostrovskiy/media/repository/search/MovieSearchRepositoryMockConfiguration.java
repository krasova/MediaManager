package com.ostrovskiy.media.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of MovieSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MovieSearchRepositoryMockConfiguration {

    @MockBean
    private MovieSearchRepository mockMovieSearchRepository;

}
