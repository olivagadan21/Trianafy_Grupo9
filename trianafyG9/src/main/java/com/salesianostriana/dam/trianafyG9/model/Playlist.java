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

    private Long id;

    private String name;

    @Lob
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Song> songs;

    public Playlist(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
