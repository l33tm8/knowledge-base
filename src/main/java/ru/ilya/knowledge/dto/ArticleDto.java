package ru.ilya.knowledge.dto;

import lombok.Data;
import ru.ilya.knowledge.entity.Article;

@Data
public class ArticleDto {

    public ArticleDto (Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.author = article.getAuthor().getUsername();
        this.parentId = article.getParent().getId();
    }
    private Long id;
    private String title;
    private String content;
    private String author;
    private Long parentId;
}
