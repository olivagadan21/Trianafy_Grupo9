package com.salesianostriana.dam.trianafyG9.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPlaylistDto {

    private String name;
    private String description;
    private String song;

}
