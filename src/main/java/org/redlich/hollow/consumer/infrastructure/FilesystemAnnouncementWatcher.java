package org.redlich.hollow.consumer.infrastructure;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import org.redlich.hollow.producer.infrastructure.FilesystemAnnouncer;

import com.netflix.hollow.api.client.HollowAnnouncementWatcher;

public class FilesystemAnnouncementWatcher extends HollowAnnouncementWatcher {

    private final File publishDir;
    private long latestVersion;

    public FilesystemAnnouncementWatcher(File publishDir) {
        this.publishDir = publishDir;
        this.latestVersion = readLatestVersion();
        }

    @Override
    public long getLatestVersion() {
        return latestVersion;
        }

    @Override
    public void subscribeToEvents() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try {
                        long currentVersion = readLatestVersion();
                        if(latestVersion != currentVersion) {
                            latestVersion = currentVersion;
                            triggerAsyncRefresh();
                            }
                        Thread.sleep(1000);
                        }
                    catch(Throwable throwable) {
                        throwable.printStackTrace();
                        }
                    }
                }
            });

        thread.setDaemon(true);
        thread.start();
        }

    public long readLatestVersion() {
        File file = new File(publishDir,FilesystemAnnouncer.ANNOUNCEMENT_FILENAME);

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return Long.parseLong(reader.readLine());
            }
        catch(IOException exception) {
            throw new RuntimeException(exception);
            }
        }
    }
