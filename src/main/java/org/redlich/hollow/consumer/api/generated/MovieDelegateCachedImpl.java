package org.redlich.hollow.consumer.api.generated;

import com.netflix.hollow.api.objects.delegate.HollowObjectAbstractDelegate;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.schema.HollowObjectSchema;
import com.netflix.hollow.api.custom.HollowTypeAPI;
import com.netflix.hollow.api.objects.delegate.HollowCachedDelegate;

@SuppressWarnings("all")
public class MovieDelegateCachedImpl extends HollowObjectAbstractDelegate implements HollowCachedDelegate, MovieDelegate {

    private final Long id;
    private final int titleOrdinal;
    private final Integer releaseYear;
   private MovieTypeAPI typeAPI;

    public MovieDelegateCachedImpl(MovieTypeAPI typeAPI, int ordinal) {
        this.id = typeAPI.getIdBoxed(ordinal);
        this.titleOrdinal = typeAPI.getTitleOrdinal(ordinal);
        this.releaseYear = typeAPI.getReleaseYearBoxed(ordinal);
        this.typeAPI = typeAPI;
    }

    public long getId(int ordinal) {
        return id.longValue();
    }

    public Long getIdBoxed(int ordinal) {
        return id;
    }

    public int getTitleOrdinal(int ordinal) {
        return titleOrdinal;
    }

    public int getReleaseYear(int ordinal) {
        return releaseYear.intValue();
    }

    public Integer getReleaseYearBoxed(int ordinal) {
        return releaseYear;
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

    public MovieTypeAPI getTypeAPI() {
        return typeAPI;
    }

    public void updateTypeAPI(HollowTypeAPI typeAPI) {
        this.typeAPI = (MovieTypeAPI) typeAPI;
    }

}