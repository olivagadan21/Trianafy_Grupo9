package com.salesianostriana.dam.trianafyG9.controller;

import com.salesianostriana.dam.trianafyG9.dto.CreateSongDto;
import com.salesianostriana.dam.trianafyG9.dto.GetSongDto;
import com.salesianostriana.dam.trianafyG9.dto.PutSongDto;
import com.salesianostriana.dam.trianafyG9.dto.SongDtoConverter;
import com.salesianostriana.dam.trianafyG9.model.Artist;
import com.salesianostriana.dam.trianafyG9.model.ArtistRepository;
import com.salesianostriana.dam.trianafyG9.model.Song;
import com.salesianostriana.dam.trianafyG9.model.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {

    private final SongRepository songRepository;
    private final SongDtoConverter dtoConverter;
    private final ArtistRepository artistRepository;

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
        Optional<Song> data = songRepository.findById(id);

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(songRepository.findById(id).orElse(null));
        }

    }

    @PostMapping("")
    public ResponseEntity<Song> createSong (@RequestBody CreateSongDto newSong) {

        if (newSong.getArtistId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Song nuevo = dtoConverter.createSongDtoToSong(newSong);

        Artist artist = artistRepository.findById(newSong.getArtistId()).orElse(null);

        nuevo.setArtist(artist);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(songRepository.save(nuevo));
    }

    @PutMapping("{id}")
    public ResponseEntity<Song> edit (@RequestBody PutSongDto s, @PathVariable Long id) {

        List<Song> data = songRepository.findAll();

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {

            Song song = dtoConverter.editSongDtoToSong(s);
            Artist artist = artistRepository.findById(s.getArtist()).orElse(null);
            song.setArtist(artist);
            return ResponseEntity.of(
                    songRepository.findById(id).map(m -> {
                        m.setTitle(s.getTitle());
                        m.setAlbum(s.getAlbum());
                        m.setYear(s.getYear());
                        m.setArtist(artist);
                        songRepository.save(m);
                        return m;
                    })
            );

        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id) {
        songRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }





}
