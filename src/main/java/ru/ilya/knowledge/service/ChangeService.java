package ru.ilya.knowledge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ilya.knowledge.dto.ChangeWithDifferences;
import ru.ilya.knowledge.entity.Change;
import ru.ilya.knowledge.entity.ChangeStatus;
import ru.ilya.knowledge.exception.NotFoundException;
import ru.ilya.knowledge.repository.ChangeRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChangeService {

    private final ChangeRepository changeRepository;
    public ChangeWithDifferences getChange(Long id) {
        Change change = changeRepository.findById(id).orElse(null);
        if (change == null) {
            return null;
        }
        ChangeWithDifferences changeWithDifferences = getChangeDiff(change.getOldContent(), change.getNewContent());
        changeWithDifferences.setId(change.getId());
        changeWithDifferences.setArticleId(change.getArticle().getId());
        return changeWithDifferences;
    }

    public List<Change> getChangesOfArticle(Long articleId) {
        return changeRepository.findChangesByArticleId(articleId);
    }

    public void applyChange(Long changeId) {
        Change change = changeRepository.findById(changeId).orElse(null);
        if (change == null) {
            throw new NotFoundException("Change not found");
        }
        change.setStatus(ChangeStatus.APPLIED);
        changeRepository.save(change);
    }

    public void declineChange(Long changeId) {
        Change change = changeRepository.findById(changeId).orElse(null);
        if (change == null) {
            throw new NotFoundException("Change not found");
        }
        change.setStatus(ChangeStatus.DECLINED);
    }

    private ChangeWithDifferences getChangeDiff(String oldContent, String newContent) {
        Map<Integer, String> additions = new HashMap<>();
        Map<Integer, String> deletions = new HashMap<>();
        String[] oldContentLines = oldContent.split("\n");
        String[] newContentLines = newContent.split("\n");
        int[][] lcs = new int[oldContentLines.length + 1][newContentLines.length + 1];

        for (int i = 1; i <= oldContentLines.length; i++) {
            for (int j = 1; j <= newContentLines.length; j++) {
                if (oldContentLines[i - 1].equals(newContentLines[j - 1])) {
                    lcs[i][j] = lcs[i - 1][j - 1] + 1;
                } else {
                    lcs[i][j] = Math.max(lcs[i - 1][j], lcs[i][j - 1]);
                }
            }
        }
        int i = oldContentLines.length, j = newContentLines.length;
        while (i > 0 && j > 0) {
            if (oldContentLines[i - 1].equals(newContentLines[j - 1])) {
                i--;
                j--;
            } else if (lcs[i - 1][j] >= lcs[i][j - 1]) {
                deletions.put(i - 1, oldContentLines[i - 1]);
                i--;
            } else {
                additions.put(j - 1, newContentLines[j - 1]);
                j--;
            }
        }

        while (i > 0) {
            deletions.put(i - 1, oldContentLines[i - 1]);
            i--;
        }
        while (j > 0) {
            additions.put(j - 1, newContentLines[j - 1]);
            j--;
        }
        ChangeWithDifferences changeWithDifferences = new ChangeWithDifferences();
        changeWithDifferences.setAdditions(additions);
        changeWithDifferences.setDeletions(deletions);
        return changeWithDifferences;
    }
}
