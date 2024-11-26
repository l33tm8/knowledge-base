package ru.ilya.knowledge.dto;

import lombok.Data;
import ru.ilya.knowledge.entity.ChangeStatus;

import java.util.Map;

@Data
public class ChangeWithDifferences {
   private Long id;
   private Long articleId;
   private Map<Integer, String> additions;
   private Map<Integer, String> deletions;
   private String oldContent;
   private String newContent;
   private ChangeStatus status;

}
