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

    public Song editSongDtoToSong (PutSongDto s) {
        return new Song(

                s.getTitle(),
                s.getAlbum(),
                s.getYear()
        );
    }

    public GetSongDto songToGetSongDto (Song s) {

        return GetSongDto
                .builder()
                .id(s.getId())
                .title(s.getTitle())
                .album(s.getAlbum())
                .year(s.getYear())
                .artist(s.getArtist()==null?null:s.getArtist().getName())
                .build();
    }
}
