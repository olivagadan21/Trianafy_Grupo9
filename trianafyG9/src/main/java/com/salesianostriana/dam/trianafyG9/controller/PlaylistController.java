package com.salesianostriana.dam.trianafyG9.controller;

import com.salesianostriana.dam.trianafyG9.model.Playlist;
import com.salesianostriana.dam.trianafyG9.model.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistRepository playlistRepository;

    //Listar todos
    @GetMapping("")
    public ResponseEntity<List<Playlist>> findAll(){
        return ResponseEntity.ok().body(playlistRepository.findAll());

    }

    //Listar uno solo por id
    @GetMapping("/{id}")
    public ResponseEntity<Playlist> findOne(@PathVariable("id") Long id){
        return ResponseEntity.of(playlistRepository.findById(id));
    }

    //Crear nueva playlist
    @PostMapping("")
    public ResponseEntity<Playlist> create(@RequestBody Playlist playlist){
        return ResponseEntity.status(201).body(playlistRepository.save(playlist));
    }

    //Modificar Playlist
    @PutMapping("/{id}")
    public  ResponseEntity<Playlist> edit(@RequestBody Playlist playlist, @PathVariable Long id){
        return ResponseEntity.of(
                playlistRepository.findById(id).map(list -> {
                  list.setName(playlist.getName());
                  list.setDescripcion(playlist.getDescripcion());
                  list.setSongs(playlist.getSongs());
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



}
