package com.salesianostriana.dam.trianafyG9.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
