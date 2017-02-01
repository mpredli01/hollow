package org.redlich.hollow.consumer.api.generated;

import com.netflix.hollow.api.objects.HollowObject;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class MovieHollow extends HollowObject {

    public MovieHollow(MovieDelegate delegate, int ordinal) {
        super(delegate, ordinal);
    }

    public long _getId() {
        return delegate().getId(ordinal);
    }

    public Long _getIdBoxed() {
        return delegate().getIdBoxed(ordinal);
    }

    public StringHollow _getTitle() {
        int refOrdinal = delegate().getTitleOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getStringHollow(refOrdinal);
    }

    public int _getReleaseYear() {
        return delegate().getReleaseYear(ordinal);
    }

    public Integer _getReleaseYearBoxed() {
        return delegate().getReleaseYearBoxed(ordinal);
    }

    public MovieAPI api() {
        return typeApi().getAPI();
    }

    public MovieTypeAPI typeApi() {
        return delegate().getTypeAPI();
    }

    protected MovieDelegate delegate() {
        return (MovieDelegate)delegate;
    }

}