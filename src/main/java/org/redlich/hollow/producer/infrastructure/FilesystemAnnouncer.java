package org.redlich.hollow.producer.infrastructure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.redlich.hollow.producer.Announcer;

public class FilesystemAnnouncer implements Announcer {

    public static final String ANNOUNCEMENT_FILENAME = "announced.version";

    private final File publishDir;

    public FilesystemAnnouncer(File publishDir) {
        this.publishDir = publishDir;
        }

    @Override
    public void announce(long stateVersion) {
        File announceFile = new File(publishDir,ANNOUNCEMENT_FILENAME);

        try(FileWriter writer = new FileWriter(announceFile)) {
            writer.write(String.valueOf(stateVersion));
            }
        catch(IOException exception) {
            throw new RuntimeException("Unable to write to announcement file",exception);
            }
        }
    }
