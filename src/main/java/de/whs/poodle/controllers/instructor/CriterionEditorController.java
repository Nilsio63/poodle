package de.whs.poodle.controllers.instructor;

import de.whs.poodle.beans.Exercise;
import de.whs.poodle.integration.criterion.beans.Suite;
import de.whs.poodle.integration.criterion.beans.Test;
import de.whs.poodle.integration.criterion.repositories.SuiteRepository;
import de.whs.poodle.integration.criterion.repositories.TestRepository;
import de.whs.poodle.repositories.ExerciseRepository;
import de.whs.poodle.repositories.exceptions.ForbiddenException;
import de.whs.poodle.repositories.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("instructor/exercises/{exerciseId}/editTests")
public class CriterionEditorController {
    @Autowired
    private ExerciseRepository exerciseRepo;

    @Autowired
    private SuiteRepository suiteRepo;

    @Autowired
    private TestRepository testRepo;

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("@instructorSecurity.hasAccessToExercise(authentication.name, #exerciseId)")
    public String get(
            @PathVariable int exerciseId,
            Model model) {
        Exercise exercise = exerciseRepo.getById(exerciseId);

        if (exercise == null)
            throw new NotFoundException();

        Suite suite = suiteRepo.get(exercise.getRootId());

        if (suite == null) {
            suite = new Suite();

            suite.setId(exercise.getRootId());

            suite = suiteRepo.create(suite);
        }

        model.addAttribute("exercise", exercise);
        model.addAttribute("suite", suite);

        return "instructor/criterionEditor";
    }

    @RequestMapping(method = RequestMethod.POST, params = "delete")
    @PreAuthorize("@instructorSecurity.hasAccessToExercise(authentication.name, #exerciseId)")
    public String delete(Model model, @PathVariable int exerciseId, RedirectAttributes redirectAttributes) {
        Exercise exercise = exerciseRepo.getById(exerciseId);

        Suite suite = suiteRepo.get(exercise.getRootId());

        for (Test test : suite.getTests()) {
            testRepo.delete(test.getId());
        }

        redirectAttributes.addFlashAttribute("okMessageCode", "allTestsDeleted");
        return "redirect:/instructor/exercises/" + exerciseId;
    }
}
