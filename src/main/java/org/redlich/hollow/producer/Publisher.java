package org.redlich.hollow.producer;

import java.io.File;

public interface Publisher {
    public void publishSnapshot(File snapshotFile,long stateVersion);
    public void publishDelta(File deltaFile,long previousVersion,long currentVersion);
    public void publishReverseDelta(File reverseDeltaFile,long previousVersion,long currentVersion);
    }
