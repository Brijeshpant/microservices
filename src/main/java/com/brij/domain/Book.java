package com.brij.domain;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String genres;
    private String price;
    private String name;
    private String description;
    private String publisher;
    private String language;
}
