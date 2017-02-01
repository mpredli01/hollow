package org.redlich.hollow.consumer.api.generated;

import com.netflix.hollow.api.objects.delegate.HollowObjectDelegate;


@SuppressWarnings("all")
public interface MovieDelegate extends HollowObjectDelegate {

    public long getId(int ordinal);

    public Long getIdBoxed(int ordinal);

    public int getTitleOrdinal(int ordinal);

    public int getReleaseYear(int ordinal);

    public Integer getReleaseYearBoxed(int ordinal);

    public MovieTypeAPI getTypeAPI();

}