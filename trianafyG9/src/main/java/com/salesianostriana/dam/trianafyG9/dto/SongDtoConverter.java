package com.salesianostriana.dam.trianafyG9.dto;

import com.salesianostriana.dam.trianafyG9.model.Song;
import org.springframework.stereotype.Component;

@Component
public class SongDtoConverter {

    public Song createSongDtoToSong (CreateSongDto s) {
        return new Song(

                s.getTitle(),
                s.getAlbum(),
                s.getYear()
        );
    }

    public GetSongDto songToGetSongDto (Song s) {

        GetSongDto result = new GetSongDto();
        result.setTitle(s.getTitle());
        result.setAlbum(s.getAlbum());
        result.setYear(s.getYear());
        result.setArtist(s.getArtist().getName());

        return result;
    }
}
