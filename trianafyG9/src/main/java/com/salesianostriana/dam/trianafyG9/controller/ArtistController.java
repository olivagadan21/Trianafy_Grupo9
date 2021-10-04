package com.salesianostriana.dam.trianafyG9.controller;

import com.salesianostriana.dam.trianafyG9.model.Artist;
import com.salesianostriana.dam.trianafyG9.model.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artist")
public class ArtistController {

    private ArtistRepository artistRepository;

    @GetMapping("")
    public ResponseEntity<List<Artist>> findAll(){

        return ResponseEntity.ok().body(artistRepository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> findOne(@PathVariable Long id){

        return ResponseEntity.ok().body(artistRepository.findById().orElse(null));

    }

    @PostMapping()
    public ResponseEntity<Artist> create(@RequestBody Artist nuevo){

        return ResponseEntity.status(HttpStatus.CREATED).body(artistRepository.save(nuevo));

    }


}
