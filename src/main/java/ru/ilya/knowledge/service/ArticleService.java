package ru.ilya.knowledge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ilya.knowledge.dto.ArticleDto;
import ru.ilya.knowledge.dto.ArticleWithTitleDto;
import ru.ilya.knowledge.entity.Article;
import ru.ilya.knowledge.entity.User;
import ru.ilya.knowledge.exception.NotFoundException;
import ru.ilya.knowledge.repository.ArticleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;

    public List<ArticleWithTitleDto> findByParent(Long parentId) {
        List<Article> articles = articleRepository.findByParentId(parentId);

        return articles.stream().map(article -> ArticleWithTitleDto
                        .builder()
                        .id(article.getId())
                        .title(article.getTitle())
                        .build())
                .toList();
    }

    public ArticleDto findById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Article not found"));

        return new ArticleDto(article);
    }

    public ArticleDto create(ArticleDto articleDto) {
        User user = userService.getCurrentUser();
        Long parentId = articleDto.getParentId();
        if (parentId != null && !articleRepository.existsById(parentId))
            throw new NotFoundException("Parent not found");
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setAuthor(user);
        if (parentId != null)
            article.setParent(articleRepository.findById(parentId).orElse(null));
        Article savedArticle = articleRepository.save(article);
        return new ArticleDto(savedArticle);
    }

    public ArticleDto update(Long id, ArticleDto articleDto) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null)
            throw new NotFoundException("Article not found");
        if (articleDto.getTitle() != null) {
            article.setTitle(articleDto.getTitle());
        }
        if (articleDto.getContent() != null) {
            article.setContent(articleDto.getContent());
        }

        articleRepository.save(article);
        return new ArticleDto(article);
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

}
