package com.salesianostriana.dam.trianafyG9.dto;

import com.salesianostriana.dam.trianafyG9.model.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistDtoConverter {

    public Artist createArtistDtoToArtist (CreateArtistDto create){

        return new Artist(create.getName());

    }

    public GetArtistDto artistToGetArtistDto(Artist artist) {

        GetArtistDto result = new GetArtistDto();
        result.setName(artist.getName());
        return result;

    }

}
