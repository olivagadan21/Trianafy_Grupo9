package com.salesianostriana.dam.trianafyG9.controller;

import com.salesianostriana.dam.trianafyG9.dto.GetSongDto;
import com.salesianostriana.dam.trianafyG9.model.Artist;
import com.salesianostriana.dam.trianafyG9.model.ArtistRepository;
import com.salesianostriana.dam.trianafyG9.model.Song;
import com.salesianostriana.dam.trianafyG9.model.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    @GetMapping("")
    public ResponseEntity<List<Artist>> findAll(){

        List<Artist> data = artistRepository.findAll();

        if (data.isEmpty()) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.ok().body(data);

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> findOne(@PathVariable Long id){
        Optional<Artist> data = artistRepository.findById(id);

        if (data.isEmpty()) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.of(data);

        }
    }

    @PostMapping("")
    public ResponseEntity<Artist> create(@RequestBody Artist newArtist){

        if (newArtist.getName().isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(artistRepository.save(newArtist));

        }


    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> edit(@RequestBody Artist artist, @PathVariable Long id){

        if (id == null) {

            return ResponseEntity.notFound().build();

        } else{

            return ResponseEntity.of(artistRepository.findById(id).map(m -> {
                m.setName(artist.getName());
                artistRepository.save(m);
                return m;
            }));

        }



    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        if (id == null) {
            return ResponseEntity.notFound().build();
        }else{
            artistRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

    }


}
