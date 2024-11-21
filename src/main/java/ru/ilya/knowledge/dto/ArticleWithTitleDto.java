package ru.ilya.knowledge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.ilya.knowledge.entity.Article;

@Data
@Builder
@AllArgsConstructor
public class ArticleWithTitleDto {
    public ArticleWithTitleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
    }

    private long id;
    private String title;
}
