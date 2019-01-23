package de.whs.poodle.integration.criterion.repositories;

import de.whs.poodle.integration.criterion.CriterionConnectionException;
import de.whs.poodle.integration.criterion.CriterionProperties;
import de.whs.poodle.integration.criterion.beans.SuiteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SuiteResultRepository extends BaseRepository<SuiteResult[]> {
    @Autowired
    public SuiteResultRepository(CriterionProperties criterion) {
        super(criterion, SuiteResult[].class);
    }

    public SuiteResult[] get(int userId, String id) throws CriterionConnectionException {
        return get(String.format("suite/%s/result?userId=%d", id, userId));
    }
}
