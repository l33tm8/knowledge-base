package ru.ilya.knowledge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ilya.knowledge.dto.ArticleDto;
import ru.ilya.knowledge.dto.ArticleWithTitleDto;
import ru.ilya.knowledge.service.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;


    @GetMapping("/parent/{id}")
    public List<ArticleWithTitleDto> findByParent(@PathVariable Long id) {
        return articleService.findByParent(id);
    }

    @GetMapping("/{id}")
    public ArticleDto findById(@PathVariable Long id) {
        return articleService.findById(id);
    }

    @PostMapping
    public ArticleDto create(@RequestBody ArticleDto articleDto) {
        return articleService.create(articleDto);
    }

    @PutMapping("/{id}")
    public ArticleDto update(@PathVariable Long id, @RequestBody ArticleDto articleDto) {
        return articleService.update(id, articleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.ok("Article deleted");
    }
}
