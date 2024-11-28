package ru.ilya.knowledge.dto;

import lombok.Data;

@Data
public class ArticleCreateDto {
    private String title;
    private String content;
    private Long parentId;

}
