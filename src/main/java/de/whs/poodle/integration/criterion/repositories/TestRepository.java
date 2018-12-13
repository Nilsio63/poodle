package de.whs.poodle.integration.criterion.repositories;

import de.whs.poodle.beans.ExerciseWorksheet;
import de.whs.poodle.integration.criterion.beans.Suite;
import de.whs.poodle.integration.criterion.beans.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TestRepository {
    private final SuiteRepository suiteRepo;

    @Autowired
    public TestRepository(SuiteRepository suiteRepo) {
        this.suiteRepo = suiteRepo;
    }

    public Test get(int testId) {
        return null;
    }

    public Test[] getTestsBySuite(int suiteId) {
        Suite suite = suiteRepo.get(suiteId);
        if (suite == null || suite.tests.length == 0) {
            return null;
        }
        return suite.tests;
    }

    public Map<Integer, Boolean> getExerciseToHasTestsMapForWorksheet(ExerciseWorksheet worksheet) {
        Map<Integer, Boolean> map = new HashMap<>();
        worksheet
                .getChapters()
                .forEach(c -> c
                        .getExercises()
                        .stream()
                        .mapToInt(e -> e
                                .getExercise()
                                .getRootId())
                        .forEach(id ->
                                map.put(id, getTestsBySuite(id) != null)
                        ));
        return map;
    }
}
