package com.salesianostriana.dam.trianafyG9.controller;

import com.salesianostriana.dam.trianafyG9.model.Artist;
import com.salesianostriana.dam.trianafyG9.model.ArtistRepository;
import com.salesianostriana.dam.trianafyG9.model.Song;
import com.salesianostriana.dam.trianafyG9.model.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    @GetMapping("")
    public ResponseEntity<List<Artist>> findAll(){

        return ResponseEntity
                .ok()
                .body(artistRepository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> findOne(@PathVariable Long id){

        return ResponseEntity.of(artistRepository.findById(id));

    }

    @PostMapping("")
    public ResponseEntity<Artist> create(@RequestBody Artist newArtist){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(artistRepository.save(newArtist));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> edit(@RequestBody Artist artist, @PathVariable Long id){

        return ResponseEntity.of(artistRepository.findById(id).map(m -> {
            m.setName(artist.getName());
            artistRepository.save(m);
            return m;
        }));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        artistRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }


}
