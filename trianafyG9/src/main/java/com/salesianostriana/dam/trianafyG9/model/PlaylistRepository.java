package com.salesianostriana.dam.trianafyG9.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {


    @Query("select p from Playlist p where ?1 MEMBER OF p.songs")
    List<Playlist> listasConCancion(Song s);


}
