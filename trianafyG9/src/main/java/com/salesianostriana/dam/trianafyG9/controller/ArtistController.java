package com.salesianostriana.dam.trianafyG9.controller;

import com.salesianostriana.dam.trianafyG9.dto.ArtistDtoConverter;
import com.salesianostriana.dam.trianafyG9.dto.CreateArtistDto;
import com.salesianostriana.dam.trianafyG9.dto.GetArtistDto;
import com.salesianostriana.dam.trianafyG9.model.Artist;
import com.salesianostriana.dam.trianafyG9.model.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistRepository artistRepository;
    private final ArtistDtoConverter dtoConverter;

    @GetMapping("")
    public ResponseEntity<List<GetArtistDto>> findAll(){

        List<Artist> data = artistRepository.findAll();

        if (data.isEmpty()) {

            return ResponseEntity.notFound().build();

        } else {

            List<GetArtistDto> result = data.stream().map(dtoConverter::artistToGetArtistDto).collect(Collectors.toList());

            return ResponseEntity.ok().body(result);

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> findOne(@PathVariable Long id){

        return ResponseEntity.of(artistRepository.findById(id));

    }

    @PostMapping("")
    public ResponseEntity<Artist> create(@RequestBody CreateArtistDto dto){

        if (dto.getId() == null) {

            return ResponseEntity.badRequest().build();

        }

        Artist nuevo = dtoConverter.createArtistDtoToArtist(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(artistRepository.save(nuevo));
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> edit(@RequestBody Artist artist, @PathVariable Long id){

        return ResponseEntity.of(artistRepository.findById(id).map(m -> {
            m.setName(artist.getName());
            artistRepository.save(m);
            return m;
        }));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        artistRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }


}
