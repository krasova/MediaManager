package com.ostrovskiy.media.repository.search;

import com.ostrovskiy.media.domain.Folder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Folder entity.
 */
public interface FolderSearchRepository extends ElasticsearchRepository<Folder, String> {
}
