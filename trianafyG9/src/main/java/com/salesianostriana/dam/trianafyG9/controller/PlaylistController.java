package com.salesianostriana.dam.trianafyG9.controller;

import com.salesianostriana.dam.trianafyG9.dto.CreatePlaylistDto;
import com.salesianostriana.dam.trianafyG9.dto.GetPlaylistDto;
import com.salesianostriana.dam.trianafyG9.dto.PlaylistDtoConverter;
import com.salesianostriana.dam.trianafyG9.model.Playlist;
import com.salesianostriana.dam.trianafyG9.model.PlaylistRepository;
import com.salesianostriana.dam.trianafyG9.model.Song;
import com.salesianostriana.dam.trianafyG9.model.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final PlaylistDtoConverter dtoConverter;


    //Listar todos
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

    //Listar uno solo por id
    @GetMapping("/{id}")
    public ResponseEntity<Playlist> findOne(@PathVariable("id") Long id){

        return ResponseEntity.of(playlistRepository.findById(id));
    }

    //Crear nueva playlist
    @PostMapping("")
    public ResponseEntity<Playlist> create(@RequestBody CreatePlaylistDto dto){

        if (dto.getName().isEmpty()){
            return ResponseEntity.badRequest().build();
        }


        Playlist nuevo = dtoConverter.createPlaylistDtoToPlaylist(dto);


        return ResponseEntity.status(HttpStatus.CREATED).body(playlistRepository.save(nuevo));
    }

    //Modificar Playlist
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

    //Borrar una Playlist
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        playlistRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }

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





}
