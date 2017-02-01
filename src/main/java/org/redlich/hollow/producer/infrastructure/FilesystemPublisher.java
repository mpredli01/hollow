package org.redlich.hollow.producer.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.redlich.hollow.producer.Publisher;

import org.apache.commons.io.IOUtils;

public class FilesystemPublisher implements Publisher {

    private final File publishDir;

    public FilesystemPublisher(File publishDir) {
        this.publishDir = publishDir;
        }

    @Override
    public void publishSnapshot(File snapshotFile,long stateVersion) {
        File publishedFile = new File(publishDir,"snapshot-" + stateVersion);
        copyFile(snapshotFile,publishedFile);
        }

    @Override
    public void publishDelta(File deltaFile,long previousVersion,long currentVersion) {
        File publishedFile = new File(publishDir,"delta-" + previousVersion + "-" + currentVersion);
        copyFile(deltaFile,publishedFile);
        }

    @Override
    public void publishReverseDelta(File reverseDeltaFile,long previousVersion,long currentVersion) {
        File publishedFile = new File(publishDir,"reversedelta-" + currentVersion + "-" + previousVersion);
        copyFile(reverseDeltaFile,publishedFile);
        }

    private void copyFile(File sourceFile,File destFile) {
        try(
                InputStream is = new FileInputStream(sourceFile);
                OutputStream os = new FileOutputStream(destFile);
        ) {
            IOUtils.copy(is,os);
            }
        catch(IOException exception) {
            throw new RuntimeException("Unable to publish file!",exception);
            }
        }
    }
