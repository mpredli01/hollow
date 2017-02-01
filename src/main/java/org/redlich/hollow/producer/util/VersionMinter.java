package org.redlich.hollow.producer.util;

import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class VersionMinter {

    private static AtomicInteger versionCounter = new AtomicInteger();

    /**
     * Create a new state version.<p>
     *
     * State versions should be ascending -- later states have greater versions.<p>
     *
     * Here we use an easily readable timestamp format.
     *
     * @return a new state version
     */
    public static long mintANewVersion() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = dateFormat.format(new Date());

        String versionStr = formattedDate + String.format("%03d",versionCounter.incrementAndGet() % 1000);

        return Long.parseLong(versionStr);
        }
    }
