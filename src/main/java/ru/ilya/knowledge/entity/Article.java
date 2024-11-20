package ru.ilya.knowledge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @ManyToOne
    private User author;

    @ManyToOne
    private Article parent;
}
