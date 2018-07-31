package com.ostrovskiy.media.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.ostrovskiy.media.domain.Folder;
import com.ostrovskiy.media.domain.Picture;
import com.ostrovskiy.media.repository.FolderRepository;
import com.ostrovskiy.media.repository.search.FolderSearchRepository;
import com.ostrovskiy.media.service.FolderService;
import com.ostrovskiy.media.service.PictureService;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.imageio.ImageIO;
import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.indexers.parallel.ParallelIndexer;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.searchers.ImageSearcher;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing Folder.
 */
@Service
public class FolderServiceImpl implements FolderService {

    private final Logger log = LoggerFactory.getLogger(FolderServiceImpl.class);

    private final PictureService pictureService;

    private final FolderRepository folderRepository;

    private final FolderSearchRepository folderSearchRepository;

    public FolderServiceImpl(FolderRepository folderRepository,
        FolderSearchRepository folderSearchRepository, PictureService pictureService) {
        this.folderRepository = folderRepository;
        this.folderSearchRepository = folderSearchRepository;
        this.pictureService = pictureService;
    }

    /**
     * Save a folder.
     *
     * @param folder the entity to save
     * @return the persisted entity
     */
    @Override
    public Folder save(Folder folder) {
        log.debug("Request to save Folder : {}", folder);
        Folder result = folderRepository.save(folder);
        folderSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the folders.
     *
     * @return the list of entities
     */
    @Override
    public List<Folder> findAll() {
        log.debug("Request to get all Folders");
        return folderRepository.findAll();
    }


    /**
     * Get one folder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Folder> findOne(String id) {
        log.debug("Request to get Folder : {}", id);
        return folderRepository.findById(id);
    }

    /**
     * Delete the folder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Folder : {}", id);
        folderRepository.deleteById(id);
        folderSearchRepository.deleteById(id);
    }

    /**
     * Search for the folder corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<Folder> search(String query) {
        log.debug("Request to search Folders for query {}", query);
        return StreamSupport
            .stream(folderSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * Save pictures from folder.
     *
     * @param path path to the folder
     */
    @Override
    public void savePicturesFromFolder(String path) {
        int numOfThreads = 6; // the number of thread used.
        ParallelIndexer indexer = new ParallelIndexer(numOfThreads, "index", path);
        indexer.addExtractor(CEDD.class);
        indexer.addExtractor(AutoColorCorrelogram.class);
        indexer.run();
        log.debug("Finished indexing.");
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths
                .filter(Files::isRegularFile)
                .forEach(file -> {
                        Picture picture = new Picture()
                            .name(file.getFileName().toString())
                            .path(file.toAbsolutePath().toString())
                            .setSize(Long.toString(new File(file.toString()).length()));
                        // use ParallelIndexer to index all photos from args[0] into "index" ... use 6 threads (actually 7 with the I/O thread).
                        IndexReader ir;
                        try {
                            ir = DirectoryReader.open(FSDirectory.open(Paths.get("index")));

                            ImageSearcher searcher = new GenericFastImageSearcher(30, CEDD.class);

                            BufferedImage img = ImageIO.read(file.toFile());
                            // searching with a image file ...
                            ImageSearchHits hits = searcher.search(img, ir);
                            // searching with a Lucene document instance ...
//        ImageSearchHits hits = searcher.search(ir.document(0), ir);
                            picture.setMd5("no duplicates");
                            for (int i = 0; i < hits.length(); i++) {
                                String fileName = ir.document(hits.documentID(i)).getValues(
                                    DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];

                                log.debug(hits.score(i) + ": \t" + fileName);
                                if (hits.score(i) < 10 && !file.toAbsolutePath().toString().equals(fileName)){
                                    picture.setMd5(hits.score(i) + ": \t" + fileName);
                                }
                                picture.setMd5(hits.score(i) + ": \t" + fileName);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        pictureService.save(picture);
                    }
                );
        } catch (IOException e) {
            log.error("Something happens when we were saving pictures", e.getMessage());
        }
    }
}
