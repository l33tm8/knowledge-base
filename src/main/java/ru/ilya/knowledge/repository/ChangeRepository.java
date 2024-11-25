package ru.ilya.knowledge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ilya.knowledge.entity.Change;

import java.util.List;

@Repository
public interface ChangeRepository extends CrudRepository<Change, Long> {
    List<Change> findChangesByArticleId(Long articleId);

}
