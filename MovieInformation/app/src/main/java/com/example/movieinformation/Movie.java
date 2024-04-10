package com.example.movieinformation;

import android.graphics.Bitmap;

public class Movie {
    private String title;
    private String year;
    private String rating;
    private String runtime;
    private String actors;
    private String plot;
    private String posterUrl;
    private Bitmap posterBitmap;

    // Constructor, getters, and setters


    public Movie(String title, String year) {
        this.title = title;
        this.year = year;
    }

    public Movie(String title, String year, String rating, String runtime, String actors, String plot, String posterUrl, Bitmap posterBitmap) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.runtime = runtime;
        this.actors = actors;
        this.plot = plot;
        this.posterUrl = posterUrl;
    }

    public Bitmap getPosterBitmap() {
        return posterBitmap;
    }

    public void setPosterBitmap(Bitmap posterBitmap) {
        this.posterBitmap = posterBitmap;
    }

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
//                ", rating='" + rating + '\'' +
//                ", runtime='" + runtime + '\'' +
//                ", actors='" + actors + '\'' +
//                ", plot='" + plot + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}

