package com.salesianostriana.dam.trianafyG9.dto;

import com.salesianostriana.dam.trianafyG9.model.Playlist;
import org.springframework.stereotype.Component;

@Component
public class PlaylistDtoConverter {

    public Playlist createPlaylistDtoToPlaylist(CreatePlaylistDto c){
        return new Playlist(
                c.getName(),
                c.getDescription()

        );
    }

    public GetPlaylistDto playlistToGetPlaylistDto(Playlist p){

        GetPlaylistDto result = new GetPlaylistDto();

        result.setName(p.getName());
        result.setDescription(p.getDescription());
        result.setSong(p.getSongs());

        return result;

    }

}
