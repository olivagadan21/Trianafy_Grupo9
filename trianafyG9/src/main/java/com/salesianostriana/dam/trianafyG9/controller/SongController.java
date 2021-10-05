package com.salesianostriana.dam.trianafyG9.controller;

import com.salesianostriana.dam.trianafyG9.dto.CreateSongDto;
import com.salesianostriana.dam.trianafyG9.dto.GetSongDto;
import com.salesianostriana.dam.trianafyG9.dto.SongDtoConverter;
import com.salesianostriana.dam.trianafyG9.model.Song;
import com.salesianostriana.dam.trianafyG9.model.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {

    private final SongRepository songRepository;
    private final SongDtoConverter dtoConverter;


    @GetMapping("")
    public ResponseEntity<List<GetSongDto>> findAll() {

        List<Song> data = songRepository.findAll();

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<GetSongDto> result =
                    data.stream()
                            .map(dtoConverter::songToGetSongDto)
                            .collect(Collectors.toList());
            return ResponseEntity.ok().body(result);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Song> findOneSong (@PathVariable Long id) {
        

        return ResponseEntity
                .ok()
                .body(songRepository.findById(id).orElse(null));
    }

    @PostMapping("{id}")
    public ResponseEntity<Song> createSong (@RequestBody CreateSongDto newSong) {

        Song nuevo = dtoConverter.createSongDtoToSong(newSong);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(songRepository.save(nuevo));
    }

    @PutMapping("{id}")
    public ResponseEntity<Song> edit (@RequestBody Song s, @PathVariable Long id) {
        return ResponseEntity.of(
                songRepository.findById(id).map(m -> {
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
        songRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }





}
