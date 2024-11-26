package ru.ilya.knowledge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ilya.knowledge.aop.AdminRequired;
import ru.ilya.knowledge.dto.ChangeWithDifferences;
import ru.ilya.knowledge.service.ChangeService;


@RestController
@RequestMapping("/changes")
@RequiredArgsConstructor
public class ChangeController {
    private final ChangeService changeService;

    @GetMapping("/{id}")
    public ChangeWithDifferences get(@PathVariable Long id) {
        return changeService.getChange(id);
    }

    @AdminRequired
    @PostMapping("/{id}/apply")
    public ResponseEntity<String> apply(@PathVariable Long id) {
        changeService.applyChange(id);
        return ResponseEntity.ok("change applied");
    }

    @AdminRequired
    @PostMapping("/{id}/decline")
    public ResponseEntity<String> decline(@PathVariable Long id) {
        changeService.declineChange(id);
        return ResponseEntity.ok("change declined");
    }

    @AdminRequired
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        changeService.deleteChange(id);
        return ResponseEntity.ok("change deleted");
    }
}
