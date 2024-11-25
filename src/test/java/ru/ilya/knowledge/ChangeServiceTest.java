package ru.ilya.knowledge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ilya.knowledge.dto.ChangeWithDifferences;
import ru.ilya.knowledge.entity.Change;
import ru.ilya.knowledge.service.ChangeService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class ChangeServiceTest {
    @Autowired
    private ChangeService changeService;

    @Test
    void getChangeDiffs_ShouldReturnValidDiffs() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Change change = new Change();
        change.setOldContent("1\n2\n3\n4\n6\n");
        change.setNewContent("2\n2\n3\n4\n4\n5\n");
        Method diffMethod = changeService.getClass().getDeclaredMethod("getChangeDiff", String.class, String.class);
        diffMethod.setAccessible(true);

        Map<Integer, String> expectedAdditions = new HashMap<>();
        expectedAdditions.put(0, "2");
        expectedAdditions.put(3, "4");
        expectedAdditions.put(5, "5");

        Map<Integer, String> expectedDeletions = new HashMap<>();
        expectedDeletions.put(0, "1");
        expectedDeletions.put(4, "6");

        ChangeWithDifferences result = (ChangeWithDifferences) diffMethod.invoke(changeService,
                change.getOldContent(),
                change.getNewContent());

        Assertions.assertEquals(expectedAdditions, result.getAdditions());
        Assertions.assertEquals(expectedDeletions, result.getDeletions());
    }
}
