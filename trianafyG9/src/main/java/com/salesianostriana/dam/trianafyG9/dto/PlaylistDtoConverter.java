package com.salesianostriana.dam.trianafyG9.dto;

import com.salesianostriana.dam.trianafyG9.model.Playlist;
import com.salesianostriana.dam.trianafyG9.model.Song;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlaylistDtoConverter {

    public Playlist createPlaylistDtoToPlaylist(CreatePlaylistDto c){
        return new Playlist(
                c.getName(),
                c.getDescription()

        );
    }

    public GetPlaylistDto playlistToGetPlaylistDto(Playlist p){

        return GetPlaylistDto
                .builder().
                name(p.getName())
                .description(p.getDescription())
                .song(p.getSongs().stream().map(Song::getTitle).collect(Collectors.toList()))
                .build();

    }

}
