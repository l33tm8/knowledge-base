package ru.ilya.knowledge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ilya.knowledge.entity.Article;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findByParentId(Long parentId);
    List<Article> findByParent(Article parent);
    boolean ExistsById(Long id);
}
