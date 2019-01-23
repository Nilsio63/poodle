package de.whs.poodle.integration.criterion.controllers;

import de.whs.poodle.integration.criterion.CriterionConnectionException;
import de.whs.poodle.integration.criterion.beans.Test;
import de.whs.poodle.integration.criterion.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/criterion/rest")
public class CriterionRestController {
    @Autowired
    private TestRepository testRepo;

    @RequestMapping(value = "tests", method = RequestMethod.POST)
    public Test createTest(@ModelAttribute Test test) throws CriterionConnectionException {
        return testRepo.create(test);
    }

    @RequestMapping(value = "tests/{testId}", method = RequestMethod.DELETE)
    public void deleteTest(@PathVariable int testId) throws CriterionConnectionException {
        testRepo.delete(testId);
    }
}
