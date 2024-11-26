package ru.ilya.knowledge.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ilya.knowledge.dto.ArticleDto;
import ru.ilya.knowledge.dto.ArticleWithTitleDto;
import ru.ilya.knowledge.dto.ChangeDto;
import ru.ilya.knowledge.dto.ChangeWithDifferences;
import ru.ilya.knowledge.entity.Article;
import ru.ilya.knowledge.entity.ChangeStatus;
import ru.ilya.knowledge.entity.User;
import ru.ilya.knowledge.exception.NotFoundException;
import ru.ilya.knowledge.repository.ArticleRepository;
import ru.ilya.knowledge.repository.ChangeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ChangeService changeService;
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

    @Transactional
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

    @Transactional
    public ChangeWithDifferences update(Long id, ArticleDto articleDto) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null)
            throw new NotFoundException("Article not found");
        if (articleDto.getContent() != null && !articleDto.getContent().equals(article.getContent())) {
            ChangeDto changeDto = new ChangeDto();
            changeDto.setArticleId(article.getId());
            changeDto.setNewContent(articleDto.getContent());
            changeDto.setOldContent(article.getContent());
            changeDto.setStatus(ChangeStatus.IN_REVIEW);
            ChangeWithDifferences changeWithDifferences =  changeService.create(changeDto);
            changeWithDifferences.setArticleId(article.getId());
            changeWithDifferences.setStatus(ChangeStatus.IN_REVIEW);
            return changeWithDifferences;
        }
        if (articleDto.getTitle() != null && !articleDto.getTitle().equals(article.getTitle())) {
            article.setTitle(articleDto.getTitle());
        }
        articleRepository.save(article);
        ChangeWithDifferences changeWithDifferences = new ChangeWithDifferences();
        changeWithDifferences.setArticleId(article.getId());

        return changeWithDifferences;
    }
    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

}
