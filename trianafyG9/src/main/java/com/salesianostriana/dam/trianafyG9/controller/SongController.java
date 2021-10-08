package com.salesianostriana.dam.trianafyG9.controller;

import com.salesianostriana.dam.trianafyG9.dto.CreateSongDto;
import com.salesianostriana.dam.trianafyG9.dto.GetSongDto;
import com.salesianostriana.dam.trianafyG9.dto.PutSongDto;
import com.salesianostriana.dam.trianafyG9.dto.SongDtoConverter;
import com.salesianostriana.dam.trianafyG9.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final PlaylistRepository playlistRepository;

    @Operation(summary = "Muestra una lista de todas las canciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los artistas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado los artistas",
                    content = @Content),
    })
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

    @Operation(summary = "Obtiene una canción en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la canción",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado la canción",
                    content = @Content),
    })
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

    @Operation(summary = "Crea una nueva canción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha creado la nueva canción",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha creado la nueva canción",
                    content = @Content),
    })
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

    @Operation(summary = "Edita una canción anteriormente creada, buscando por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado la canción",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha editado la canción",
                    content = @Content),
    })
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

    @Operation(summary = "Borra una canción en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha borrado la canción",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha borrado la canción",
                    content = @Content),
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id) {

        Optional<Song> song = songRepository.findById(id);
        if (song.isPresent()) {


            List<Playlist> playlists = playlistRepository.listasConCancion(songRepository.getById(id));
            for (Playlist playlist : playlists) {
                playlist.getSongs().remove(song.get());
                playlistRepository.save(playlist);
            }
            songRepository.deleteById(id);
        }

        return ResponseEntity.noContent().build();

    }





}
