package com.salesianostriana.dam.trianafyG9.dto;

import com.salesianostriana.dam.trianafyG9.model.Song;
import org.springframework.stereotype.Component;

@Component
public class SongDtoConverter {

    public Song createSongDtoToSong (CreateSongDto s) {
        return new Song(
                s.getAlbum(),
                s.getTitle(),
                s.getYear()
        );
    }

    public GetSongDto songToGetSongDto (Song s) {

        GetSongDto result = new GetSongDto();

        result.setAlbum(s.getAlbum());
        result.setTitle(s.getTitle());
        result.setArtist(s.getArtist().getNombre());

        return result;
    }
}
