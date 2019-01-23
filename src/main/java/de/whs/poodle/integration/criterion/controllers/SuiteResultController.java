package de.whs.poodle.integration.criterion.controllers;

import de.whs.poodle.beans.Student;
import de.whs.poodle.integration.criterion.CriterionConnectionException;
import de.whs.poodle.integration.criterion.beans.SuiteResult;
import de.whs.poodle.integration.criterion.repositories.AdminResultRepository;
import de.whs.poodle.integration.criterion.repositories.SuiteResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class SuiteResultController {
    private final SuiteResultRepository suiteResultRepository;
    private final AdminResultRepository adminResultRepository;

    @Autowired
    public SuiteResultController(SuiteResultRepository suiteResultRepository, AdminResultRepository adminResultRepository) {
        this.suiteResultRepository = suiteResultRepository;
        this.adminResultRepository = adminResultRepository;
    }

    @RequestMapping(value = "/getSuiteResult", method = RequestMethod.GET)
    public String getSuiteResult(@ModelAttribute Student student,
                                 Model model,
                                 @RequestParam("id") String exerciseId) throws ParseException, CriterionConnectionException {

        SuiteResult[] result = suiteResultRepository.get(student.getUsername().hashCode(), exerciseId);
        //The use of HashCode is not optimal, but the user id has some kind of error deep inside poodle and
        //it´s value is always 1
        if (result == null) {
            SuiteResult[] res = new SuiteResult[0];
            model.addAttribute("suiteResult", res);
            return "student/suiteResult";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.GERMAN);
        SimpleDateFormat out = new SimpleDateFormat();
        for (SuiteResult rs : result) {
            Date date = sdf.parse(rs.creationTime);
            System.out.println(out.format(date));
            rs.creationTime = out.format(date);
        }
        Arrays.sort(result, Collections.reverseOrder(Comparator.comparing(o -> o.creationTime)));

        model.addAttribute("suiteResult", result);
        return "student/suiteResult";
    }

    @RequestMapping(value = "/getInstructorSuiteResult", method = RequestMethod.GET)
    public String getInstructorSuiteResult(@ModelAttribute Student student,
                                           Model model,
                                           @RequestParam("id") String exerciseId) throws CriterionConnectionException {

        SuiteResult[] result = adminResultRepository.getById(Integer.parseInt(exerciseId));

        if (result == null) {
            SuiteResult res = new SuiteResult();
            res.SetInfo(0, 0, "", 0, 0, 0, null, null);
            model.addAttribute("suiteResults", res);
            return "instructor/instructorResult";
        }

        SuiteResult[] suiteResults = new SuiteResult[result.length];
        for (int i = 0; i < result.length; i++) {
            suiteResults[i] = new SuiteResult();
            suiteResults[i].SetInfo(0, Integer.parseInt(exerciseId), "", 0, result[i].successCount, result[i].testCount, result[i].creationTime, null);
        }
        model.addAttribute("suiteResults", suiteResults);
        return "instructor/instructorResult";
    }
}
