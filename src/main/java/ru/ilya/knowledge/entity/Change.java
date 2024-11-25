package ru.ilya.knowledge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "changes")
public class Change {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Article article;

    @Column
    private String oldContent;

    @Column
    private String newContent;

    @Enumerated(EnumType.STRING)
    @Column
    private ChangeStatus status;

}
