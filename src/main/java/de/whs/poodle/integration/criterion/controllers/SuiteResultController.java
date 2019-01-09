package de.whs.poodle.integration.criterion.controllers;

import de.whs.poodle.beans.Student;
import de.whs.poodle.integration.criterion.beans.SuiteResult;
import de.whs.poodle.integration.criterion.repositories.AdminResultRepository;
import de.whs.poodle.integration.criterion.repositories.SuiteResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SuiteResultController {
    private final SuiteResultRepository suiteResultRepository;
    private final AdminResultRepository adminResultRepository;

    @Autowired
    public SuiteResultController(SuiteResultRepository suiteResultRepository, AdminResultRepository adminResultRepository) { this.suiteResultRepository = suiteResultRepository; this.adminResultRepository = adminResultRepository; }

    @RequestMapping(value = "/getSuiteResult", method = RequestMethod.GET)
    public String getSuiteResult(@ModelAttribute Student student,
                                 Model model,
                                 @RequestParam("id") String exerciseId) {

        SuiteResult result = suiteResultRepository.get(student.getId(), exerciseId);
        if (result == null) {
            SuiteResult res = new SuiteResult();
            res.SetInfo(0, 0, "", 0, 0, 0, 0);
            model.addAttribute("suiteResult", res);
            return "student/suiteResult";
        }
        SuiteResult res = new SuiteResult();
        res.SetInfo(result.id, result.suiteId, result.compileError, result.status, result.successCount, result.testCount, result.errorCount);
        model.addAttribute("suiteResult", res);
        return "student/suiteResult";
    }

    @RequestMapping(value = "/getInstructorSuiteResult", method = RequestMethod.GET)
    public String getInstructorSuiteResult(@ModelAttribute Student student,
                                 Model model,
                                 @RequestParam("id") String exerciseId) {

        SuiteResult[] result = adminResultRepository.get(exerciseId);

        if (result == null) {
            SuiteResult res = new SuiteResult();
            res.SetInfo(0, 0, "", 0, 0, 0, 0);
            model.addAttribute("suiteResults", res);
            return "instructor/instructorResult";
        }

        SuiteResult[] res = new SuiteResult[result.length];
        for (int i = 0; i < result.length; i++) {
            res[i] = new SuiteResult();
            res[i].SetInfo(0, Integer.parseInt(exerciseId), "", 0, result[i].successCount, 0, result[i].errorCount);
        }
        model.addAttribute("suiteResults", res);
        return "instructor/instructorResult";
    }
}
