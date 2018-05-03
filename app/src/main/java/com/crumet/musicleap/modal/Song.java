package com.crumet.musicleap.modal;

/**
 * Created by ravi on 2/24/2018.
 */

public class Song {
    private long id;
    private String title, artist;
    private long duration;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }


    public Song(long id, String title, String artist, long duration) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }
}
