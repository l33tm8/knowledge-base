package ru.ilya.knowledge;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ilya.knowledge.dto.ArticleWithTitleDto;
import ru.ilya.knowledge.entity.Article;
import ru.ilya.knowledge.repository.ArticleRepository;
import ru.ilya.knowledge.service.ArticleService;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @Test
    void FindByParentWithNull_shouldReturnRootArticlesWithTitleDto() {
        Article article = new Article();
        article.setTitle("title");
        Article article2 = new Article();
        article2.setTitle("title2");
        Article article3 = new Article();
        article3.setTitle("title3");
        List<ArticleWithTitleDto> rootArticlesDto = List.of(new ArticleWithTitleDto(article),
                new ArticleWithTitleDto(article2),
                new ArticleWithTitleDto(article3));

        when(articleRepository.findByParentId(null)).thenReturn(List.of(article, article2, article3));
        List<ArticleWithTitleDto> result = articleService.findByParent(null);
        Assertions.assertEquals(rootArticlesDto, result);
    }
}
