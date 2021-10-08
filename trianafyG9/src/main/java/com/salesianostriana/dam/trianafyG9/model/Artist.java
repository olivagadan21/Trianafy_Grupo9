package com.salesianostriana.dam.trianafyG9.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor
public class Artist {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Artist(String name) {
        this.name = name;
    }



}
