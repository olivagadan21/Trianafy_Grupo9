package com.salesianostriana.dam.trianafyG9.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Lombok;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data@NoArgsConstructor@AllArgsConstructor
public class Playlist {

    private Long is;

    private String name;

    @Lob
    private String descripcion;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Song> songs = new ArrayList<>();

    public Playlist(String name, String descripcion, List<Song> songs) {
        this.name = name;
        this.descripcion = descripcion;
        this.songs = songs;
    }
}
