package com.salesianostriana.dam.trianafyG9.controller;

import com.salesianostriana.dam.trianafyG9.dto.CreatePlaylistDto;
import com.salesianostriana.dam.trianafyG9.dto.GetPlaylistDto;
import com.salesianostriana.dam.trianafyG9.dto.PlaylistDtoConverter;
import com.salesianostriana.dam.trianafyG9.model.*;
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
import java.util.stream.Stream;

@RestController
@RequestMapping("/lists")
@RequiredArgsConstructor
@Tag(name = "Playlist", description = "Es la clase controlador de Playlist")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final PlaylistDtoConverter dtoConverter;

    @Operation(summary = "Muestra una lista de todos los artistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado la playlist",
                    content = @Content),
    })
    @GetMapping("")
    public ResponseEntity<List<GetPlaylistDto>> findAll(){
        List<Playlist> data = playlistRepository.findAll();

        if (data.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            List<GetPlaylistDto> result = data.stream().map(dtoConverter::playlistToGetPlaylistDto).collect(Collectors.toList());
            return ResponseEntity.ok().body(result);
        }

    }

    @Operation(summary = "Obtiene una lista en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado a la playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado a la playlist",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Playlist> findOne(@PathVariable("id") Long id){

        return ResponseEntity.of(playlistRepository.findById(id));
    }

    @Operation(summary = "Crea una nueva playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha creado la nueva playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha creado la nueva playlist",
                    content = @Content),
    })
    @PostMapping("")
    public ResponseEntity<Playlist> create(@RequestBody CreatePlaylistDto dto){

        if (dto.getName().isEmpty()){
            return ResponseEntity.badRequest().build();
        }


        Playlist nuevo = dtoConverter.createPlaylistDtoToPlaylist(dto);


        return ResponseEntity.status(HttpStatus.CREATED).body(playlistRepository.save(nuevo));
    }

    @Operation(summary = "Edita una playlist anteriormente creada, buscando por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado la playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha editado la playlist",
                    content = @Content),
    })
    @PutMapping("/{id}")
    public  ResponseEntity<Playlist> edit(@RequestBody Playlist playlist, @PathVariable Long id){
        return ResponseEntity.of(
                playlistRepository.findById(id).map(list -> {
                    list.setName(playlist.getName());
                    list.setDescription(playlist.getDescription());
                    playlistRepository.save(list);
                    return list;
                })
        );
    }

    @Operation(summary = "Borra una playlist en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha borrado la playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha borrado la playlist",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        playlistRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "Añade una canción a una playlist ambas ya existentes en base a sus ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha añadido la canción a la playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha añadido la canción a la playlist",
                    content = @Content),
    })
    @PostMapping("/{idPlaylist}/songs/{idSong}")
    public ResponseEntity<Playlist> newPlaySong(@RequestBody Playlist playlist, @PathVariable Long idPlaylist,
                                                @PathVariable Long idSong) {


        if ((playlistRepository.findById(idPlaylist) == null) || (songRepository.findById(idSong) == null)){
            return ResponseEntity.badRequest().build();
        }else {
            Playlist playlist1 = playlistRepository.findById(idPlaylist).orElse(null);

            Song song1 = songRepository.findById(idSong).orElse(null);
            playlist1.getSongs().add(song1);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(playlistRepository.save(playlist1));

        }


    }

    @Operation(summary = "Muestra todas las canciones de una playlist en específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han mostrado todas las canciones de la playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han mostrado todas las canciones de la playlist",
                    content = @Content),
    })
    @GetMapping("/{idPlaylist}/songs")
    public ResponseEntity<Playlist> findAllPlaySong(@PathVariable Long idPlaylist){

        return ResponseEntity.of(playlistRepository.findById(idPlaylist));

    }

    @Operation(summary = "Obtiene una canción de una playlist en base a sus ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la canción de la playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado la canción de la playlist",
                    content = @Content),
    })
    @GetMapping("/{idPlaylist}/song/{idSong}")
    public ResponseEntity<Stream<Song>> findSongOfPlayList(@PathVariable Long idPlaylist, @PathVariable Long idSong){
        return ResponseEntity.of(playlistRepository.findById(idPlaylist)
                .map(m -> (m.getSongs()
                        .stream().filter(song -> song.getId().equals(idSong)))
                ));

    }

    @Operation(summary = "Borra una canción de una playlist en base a sus ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha borrado la canción de la playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha borrado la canción de la playlist",
                    content = @Content),
    })
    @DeleteMapping("/{idPlaylist}/song/{idSong}")
    public ResponseEntity<Playlist> deleteSongOfPlayList(@PathVariable Long idPlaylist, @PathVariable Long idSong){

        Optional<Playlist> lista = playlistRepository.findById(idPlaylist);

        if (playlistRepository.findById(idPlaylist).isEmpty() ||
            !playlistRepository.findById(idPlaylist).get().getSongs().contains(songRepository.getById(idSong))){

            return ResponseEntity.notFound().build();

        }else {

            lista.get().getSongs().remove(songRepository.findById(idSong).get());

            playlistRepository.save(lista.get());

            return ResponseEntity.noContent().build();
        }




    }





       


}


