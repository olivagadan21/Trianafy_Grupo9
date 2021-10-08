package com.salesianostriana.dam.trianafyG9.controller;

import com.salesianostriana.dam.trianafyG9.dto.GetSongDto;
import com.salesianostriana.dam.trianafyG9.model.Artist;
import com.salesianostriana.dam.trianafyG9.model.ArtistRepository;
import com.salesianostriana.dam.trianafyG9.model.Song;
import com.salesianostriana.dam.trianafyG9.model.SongRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Artist", description = "Es la clase controlador de Artist")
public class ArtistController {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    @Operation(summary = "Muestra una lista de todos los artistas")
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
    public ResponseEntity<List<Artist>> findAll(){

        List<Artist> data = artistRepository.findAll();

        if (data.isEmpty()) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.ok().body(data);

        }

    }

    @Operation(summary = "Obtiene un artista en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado al artista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado al artista",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Artist> findOne(@PathVariable Long id){
        Optional<Artist> data = artistRepository.findById(id);

        if (data.isEmpty()) {

            return ResponseEntity.notFound().build();

        } else {

            return ResponseEntity.of(data);

        }
    }

    @Operation(summary = "Crea un nuevo artista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha creado el nuevo artista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha creado el artista",
                    content = @Content),
    })
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

    @Operation(summary = "Edita un artista anteriormente creado, buscando por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado al artista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha editado al artista",
                    content = @Content),
    })
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

    @Operation(summary = "Borra un artista en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha borrado al artista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha borrado al artista",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        List <Song> listSong = songRepository.findAll();
        for(Song song : listSong){
            if (id == song.getArtist().getId())
            song.setArtist(null);}
            artistRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
