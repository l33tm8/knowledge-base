package ru.ilya.knowledge.entity;

import jakarta.annotation.Nullable;
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
    @Nullable
    private Article parent;
}
