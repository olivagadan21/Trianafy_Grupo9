package com.salesianostriana.dam.trianafyG9.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data @NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    @ManyToOne
    private Artist artist;
    private String album;
    private String year;

    public Song(String title, String album, String year) {
        this.title = title;
        this.album = album;
        this.year = year;
    }



    public Song(String title, String album, String year, Artist artist) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }
}
