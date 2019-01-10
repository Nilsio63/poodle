package de.whs.poodle.integration.criterion.repositories;

import de.whs.poodle.beans.ExerciseWorksheet;
import de.whs.poodle.integration.criterion.CriterionProperties;
import de.whs.poodle.integration.criterion.beans.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SuiteRepository extends BaseRepository<Suite> {
    @Autowired
    TestRepository testRepository;

    @Autowired
    public SuiteRepository(CriterionProperties criterion) {
        super(criterion, Suite.class);
    }

    public Suite get(int id) {
        return get(String.format("suite/%d", id));
    }

    public void delete(int id) {
        delete(String.format("suite/%d", id));
    }

    public Suite create(Suite suite) {
        return post("suite", suite);
    }

    private boolean getHasTests(int id) {
        Suite suite = get(id);

        return suite != null && suite.getTests() != null && suite.getTests().length != 0;
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
                                map.put(id, getHasTests(id))
                        ));
        return map;
    }
}
