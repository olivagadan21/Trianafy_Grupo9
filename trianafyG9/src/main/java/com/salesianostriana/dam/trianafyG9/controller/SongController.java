package com.salesianostriana.dam.trianafyG9.controller;

import com.salesianostriana.dam.trianafyG9.model.Song;
import com.salesianostriana.dam.trianafyG9.model.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Song> findOne (@PathVariable Long id) {
        return
    }





}
