package org.redlich.hollow.consumer.history;

import com.netflix.hollow.api.client.HollowBlob;
import com.netflix.hollow.api.client.HollowUpdateListener;
import com.netflix.hollow.api.custom.HollowAPI;
import com.netflix.hollow.core.read.engine.HollowReadStateEngine;
import com.netflix.hollow.tools.history.HollowHistory;

public class ConsumerHistoryListener implements HollowUpdateListener {

    private HollowHistory history = null;

    public HollowHistory getHistory() {
        return history;
        }

    @Override
    public void dataInitialized(HollowAPI api,HollowReadStateEngine stateEngine,long version) throws Exception {
        if(history == null) {
            history = new HollowHistory(stateEngine,version,512);
            history.getKeyIndex().addTypeIndex("Movie","id");
            history.getKeyIndex().indexTypeField("Movie","id");
            }
        else {
            history.doubleSnapshotOccurred(stateEngine,version);
            }
        }

    @Override
    public void dataUpdated(HollowAPI api,HollowReadStateEngine stateEngine,long version) throws Exception {
        history.deltaOccurred(version);
        }

    @Override public void refreshStarted(long currentVersion,long requestedVersion) { }
    @Override public void refreshCompleted(long beforeVersion,long afterVersion,long requestedVersion) { }
    @Override public void refreshFailed(long beforeVersion,long afterVersion,long requestedVersion,Throwable failureCause) { }
    @Override public void transitionApplied(HollowBlob transition) { }
    @Override public void staleReferenceExistenceDetected(int count) { }
    @Override public void staleReferenceUsageDetected(int count) { }
    }
