package org.redlich.hollow.consumer.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;

import com.netflix.hollow.api.client.HollowBlob;
import com.netflix.hollow.api.client.HollowBlobRetriever;

public class FilesystemBlobRetriever implements HollowBlobRetriever {

    private final File publishDir;

    public FilesystemBlobRetriever(File publishDir) {
        this.publishDir = publishDir;
        }

    @Override
    public HollowBlob retrieveSnapshotBlob(long desiredVersion) {
        File exactFile = new File(publishDir,"snapshot-" + desiredVersion);

        if(exactFile.exists())
            return new FilesystemBlob(exactFile,desiredVersion);

        long maxVersionBeforeDesired = Long.MIN_VALUE;
        String maxVersionBeforeDesiredFilename = null;

        for(String filename : publishDir.list()) {
            if(filename.startsWith("snapshot-")) {
                long version = Long.parseLong(filename.substring(filename.lastIndexOf("-") + 1));
                if(version < desiredVersion && version > maxVersionBeforeDesired) {
                    maxVersionBeforeDesired = version;
                    maxVersionBeforeDesiredFilename = filename;
                    }
                }
            }

        if(maxVersionBeforeDesired > Long.MIN_VALUE)
            return new FilesystemBlob(new File(publishDir,maxVersionBeforeDesiredFilename),maxVersionBeforeDesired);

        return null;
        }

    @Override
    public HollowBlob retrieveDeltaBlob(long currentVersion) {
        for(String filename : publishDir.list()) {
            if(filename.startsWith("delta-" + currentVersion)) {
                long destinationVersion = Long.parseLong(filename.substring(filename.lastIndexOf("-") + 1));
                return new FilesystemBlob(new File(publishDir,filename),currentVersion,destinationVersion);
                }
            }

        return null;
        }

    @Override
    public HollowBlob retrieveReverseDeltaBlob(long currentVersion) {
        for(String filename : publishDir.list()) {
            if(filename.startsWith("reversedelta-" + currentVersion)) {
                long destinationVersion = Long.parseLong(filename.substring(filename.lastIndexOf("-") + 1));
                return new FilesystemBlob(new File(publishDir,filename),currentVersion,destinationVersion);
                }
            }

        return null;
        }


    private static class FilesystemBlob extends HollowBlob {

        private final File file;

        public FilesystemBlob(File snapshotFile,long toVersion) {
            super(toVersion);
            this.file = snapshotFile;
            }

        public FilesystemBlob(File deltaFile,long fromVersion,long toVersion) {
            super(fromVersion,toVersion);
            this.file = deltaFile;
            }

        @Override
        public InputStream getInputStream() throws IOException {
            return new BufferedInputStream(new FileInputStream(file));
            }
        }
    }
