package com.salesianostriana.dam.trianafyG9.controller;

import com.salesianostriana.dam.trianafyG9.model.Song;
import com.salesianostriana.dam.trianafyG9.model.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {

    private final SongRepository songRepository;

    @GetMapping("")
    public ResponseEntity<List<Song>> findAll() {

        return ResponseEntity
                .ok()
                .body(songRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Song> findOneSong (@PathVariable Long id) {
        

        return ResponseEntity
                .ok()
                .body(songRepository.findById(id).orElse(null));
    }

    @PostMapping("{id}")
    public ResponseEntity<Song> createSong (@RequestBody Song newSong) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(songRepository.save(newSong));
    }

    @PutMapping("{id}")
    public ResponseEntity<Song> edit (@RequestBody Song s, @PathVariable Long id) {
        return ResponseEntity.of(
                songRepository.findById(id).map(s -> {
                    s.setAlbum(s.getAlbum());
                    s.setArtist(s.getArtist());
                    s.setTitle(s.getTitle());
                    s.setArtist(s.getArtist());
                    return s;
                })
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id) {
        songRepository.deleteAllById(id);
        return ResponseEntity.noContent().build();
    }





}
