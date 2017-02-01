package org.redlich.hollow.producer.datamodel;

public class Movie {
    long id;
    String title;
    int releaseYear;

    public Movie(long id,String title,int releaseYear) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        }
    }
