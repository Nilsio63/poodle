package de.whs.poodle.integration.criterion.repositories;

import de.whs.poodle.beans.Chapter;
import de.whs.poodle.beans.ExerciseWorksheet;
import de.whs.poodle.integration.criterion.CriterionConnectionException;
import de.whs.poodle.integration.criterion.CriterionProperties;
import de.whs.poodle.integration.criterion.beans.SuiteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AdminResultRepository extends BaseRepository<SuiteResult[]> {
    @Autowired
    public AdminResultRepository(CriterionProperties criterion) {
        super(criterion, SuiteResult[].class);
    }

    public SuiteResult[] getById(int id) throws CriterionConnectionException {
        return get(String.format("admin/%d", id));
    }

    private boolean getHasTests(int id) {
        try {
            SuiteResult[] results = getById(id);

            return results != null && results.length > 0;
        } catch (CriterionConnectionException e) {
            return false;
        }
    }

    public Map<Integer, Boolean> getExerciseToHasTestResultsMapForWorksheet(ExerciseWorksheet worksheet) {
        Map<Integer, Boolean> map = new HashMap<>();

        for (Chapter chapter : worksheet.getChapters()) {
            for (Chapter.ExerciseInChapter exercise : chapter.getExercises()) {
                int id = exercise.getExercise().getRootId();

                map.put(id, getHasTests(id));
            }
        }

        return map;
    }
}
