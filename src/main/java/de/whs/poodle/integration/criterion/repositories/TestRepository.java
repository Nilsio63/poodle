package de.whs.poodle.integration.criterion.repositories;

import de.whs.poodle.integration.criterion.CriterionConnectionException;
import de.whs.poodle.integration.criterion.CriterionProperties;
import de.whs.poodle.integration.criterion.beans.Test;
import de.whs.poodle.repositories.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository extends BaseRepository<Test> {
    @Autowired
    public TestRepository(CriterionProperties criterion) {
        super(criterion, Test.class);
    }

    public Test create(Test test) throws CriterionConnectionException {
        test = post("test", test);

        if (test == null)
            throw new BadRequestException("testFailedToSave");

        return test;
    }

    public void delete(int testId) throws CriterionConnectionException {
        delete(String.format("test/%d", testId));
    }
}
