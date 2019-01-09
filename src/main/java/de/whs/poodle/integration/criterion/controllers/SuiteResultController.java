package de.whs.poodle.integration.criterion.controllers;

import de.whs.poodle.beans.Student;
import de.whs.poodle.integration.criterion.beans.SuiteResult;
import de.whs.poodle.integration.criterion.repositories.SuiteResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SuiteResultController {
    private final SuiteResultRepository suiteResultRepository;

    @Autowired
    public SuiteResultController(SuiteResultRepository suiteResultRepository) { this.suiteResultRepository = suiteResultRepository; }

    @RequestMapping(value = "/getSuiteResult", method = RequestMethod.GET)
    public String getSuiteResult(@ModelAttribute Student student,
                                 Model model,
                                 @RequestParam("id") String exerciseId) {

        SuiteResult result = suiteResultRepository.get(student.getId(), exerciseId);
        if (result == null) {
            SuiteResult res = new SuiteResult();
            res.SetInfo(0, 0, "", 0, 0, 0);
            model.addAttribute("suiteResult", res);
            return "student/suiteResult";
        }
        // Remove test value
        SuiteResult res = new SuiteResult();
        res.SetInfo(result.id, result.suiteId, result.compileError, result.status, result.successCount, result.testCount);
        model.addAttribute("suiteResult", res);
        //model.addAttribute("testId", 2);
        // if empty redirect to start
        return "student/suiteResult";
    }

    @RequestMapping(value = "/getInstructorSuiteResult", method = RequestMethod.GET)
    public String getInstructorSuiteResult(@ModelAttribute Student student,
                                 Model model,
                                 @RequestParam("id") String exerciseId) {

        SuiteResult result = suiteResultRepository.get(student.getId(), exerciseId);
        // Remove test value
        SuiteResult res = new SuiteResult();
        res.SetInfo(0, 0, "There were no Errors", 1, 0, 0);
        model.addAttribute("suiteResult", res);
        //model.addAttribute("testId", 2);
        // if empty redirect to start
        return "instructor/suiteResult";
    }
}
