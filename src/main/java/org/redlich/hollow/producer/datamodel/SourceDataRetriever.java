package org.redlich.hollow.producer.datamodel;

import java.util.List;
import java.util.Arrays;

public class SourceDataRetriever {

    private final List<Movie> movies;

    public SourceDataRetriever() {
        this.movies = bootstrapData();
        }

    private List<Movie> bootstrapData() {
        List<Movie> movies = Arrays.asList(
                new Movie(1,"Reservoir Dogs",1992),
                new Movie(2,"Pulp Fiction",1994),
                new Movie(3,"From Dusk till Dawn",1996),
                new Movie(4,"Jackie Brown",1997),
                new Movie(5,"Kill Bill, Volume 1",2003),
                new Movie(6,"Kill Bill, Volume 2",2004),
                new Movie(7,"Inglourious Basterds",2009),
                new Movie(8,"Django Unchained",2012),
                new Movie(9,"The Hateful Eight",2015)
            );
        return movies;
        }

    public List<Movie> retrieveAllMovies() {
        return movies;
        }
    }
