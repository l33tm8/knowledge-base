package ru.ilya.knowledge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ilya.knowledge.dto.*;
import ru.ilya.knowledge.service.ArticleService;
import ru.ilya.knowledge.service.ChangeService;

import java.util.List;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final ChangeService changeService;

    @GetMapping("/parent/{id}")
    public List<ArticleWithTitleDto> findByParent(@PathVariable Long id) {
        return articleService.findByParent(id);
    }

    @GetMapping("/root")
    public List<ArticleWithTitleDto> findRootArticles() {
        return articleService.findByParent(null);
    }

    @GetMapping("/{id}")
    public ArticleDto findById(@PathVariable Long id) {
        return articleService.findById(id);
    }

    @PostMapping
    public ArticleDto create(@RequestBody ArticleCreateDto articleCreateDto) {
        return articleService.create(articleCreateDto);
    }

    @PutMapping("/{id}")
    public ChangeWithDifferences update(@PathVariable Long id, @RequestBody ArticleEditDto articleEditDto) {
        return articleService.update(id, articleEditDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.ok("Article deleted");
    }

    @GetMapping("/{id}/changes")
    public List<ChangeWithDifferences> findChanges(@PathVariable Long id) {
        return changeService.getChangesOfArticle(id);
    }
}
