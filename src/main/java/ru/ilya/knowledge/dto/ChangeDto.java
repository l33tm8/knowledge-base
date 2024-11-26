package ru.ilya.knowledge.dto;

import lombok.Data;
import ru.ilya.knowledge.entity.ChangeStatus;

@Data
public class ChangeDto {
    private int id;
    private String oldContent;
    private String newContent;
    private ChangeStatus status;
    private Long articleId;
}
