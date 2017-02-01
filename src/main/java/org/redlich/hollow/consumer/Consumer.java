package org.redlich.hollow.consumer;

import java.io.File;

import org.redlich.hollow.consumer.api.generated.MovieAPI;
import org.redlich.hollow.consumer.api.generated.MovieAPIFactory;
import org.redlich.hollow.consumer.history.ConsumerHistoryListener;
import org.redlich.hollow.consumer.infrastructure.FilesystemAnnouncementWatcher;
import org.redlich.hollow.consumer.infrastructure.FilesystemBlobRetriever;
import org.redlich.hollow.consumer.api.generated.MovieHollow;
import org.redlich.hollow.producer.Producer;

import com.netflix.hollow.api.client.HollowAnnouncementWatcher;
import com.netflix.hollow.api.client.HollowBlobRetriever;
import com.netflix.hollow.api.client.HollowClient;
import com.netflix.hollow.api.client.HollowClientMemoryConfig;
import com.netflix.hollow.core.read.engine.HollowReadStateEngine;
import com.netflix.hollow.history.ui.jetty.HollowHistoryUIServer;

public class Consumer {

    private final HollowClient client;
    private final ConsumerHistoryListener historyListener;

    public Consumer(HollowBlobRetriever blobRetriever,HollowAnnouncementWatcher announcementWatcher) {
        this.historyListener = new ConsumerHistoryListener();

        this.client = new HollowClient(
                blobRetriever,
                announcementWatcher,
                historyListener,
                new MovieAPIFactory(),
                HollowClientMemoryConfig.DEFAULT_CONFIG);

        client.triggerRefresh();
        }

    public static void main(String args[]) throws Exception {
        File publishDir = new File(Producer.SCRATCH_DIR,"publish-dir");

        System.out.println("***** CONSUMER reading from " + publishDir.getAbsolutePath());

        HollowBlobRetriever blobRetriever = new FilesystemBlobRetriever(publishDir);
        HollowAnnouncementWatcher announcementWatcher = new FilesystemAnnouncementWatcher(publishDir);

        Consumer consumer = new Consumer(blobRetriever,announcementWatcher);

        HollowReadStateEngine readEngine = consumer.getStateEngine();

        MovieAPI movieAPI = consumer.getAPI();
        for(MovieHollow movie : movieAPI.getAllMovieHollow()) {
            System.out.println(movie._getId() + "," +
                    movie._getTitle()._getValue() + "," +
                    movie._getReleaseYear());
            }

        HollowHistoryUIServer historyUIServer = new HollowHistoryUIServer(consumer.historyListener.getHistory(),7777);
        historyUIServer.start();
        historyUIServer.join();
    }

    public MovieAPI getAPI() {
        return (MovieAPI) client.getAPI();
        }

    public HollowReadStateEngine getStateEngine() {
        return client.getStateEngine();
        }
    }
