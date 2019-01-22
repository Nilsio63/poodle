package de.whs.poodle.integration.criterion.repositories;

import de.whs.poodle.integration.criterion.CriterionProperties;
import de.whs.poodle.integration.criterion.beans.SuiteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminResultRepository extends BaseRepository<SuiteResult[]> {
    @Autowired
    public AdminResultRepository(CriterionProperties criterion) {
        super(criterion, SuiteResult[].class);
    }

    public SuiteResult[] getById(String id) {
        return get(String.format("admin/%s", id));
    }
}
