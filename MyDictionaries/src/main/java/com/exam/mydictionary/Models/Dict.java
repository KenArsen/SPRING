package com.exam.mydictionary.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "dictionaries")
@Data
public class Dict {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "english")
    private String english;
    @Column(name = "russian")
    private String russian;

    public Dict() {
    }

    public Dict(String english, String russian) {
        this.english = english;
        this.russian = russian;
    }
}
