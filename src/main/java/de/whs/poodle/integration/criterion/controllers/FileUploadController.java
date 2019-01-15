package de.whs.poodle.integration.criterion.controllers;

import de.whs.poodle.beans.Student;
import de.whs.poodle.integration.criterion.repositories.TestFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    private final TestFileRepository testFileRepository;

    @Autowired
    public FileUploadController(TestFileRepository testFileRepository) {
        this.testFileRepository = testFileRepository;
    }

    @RequestMapping(value = "/uploadToCriterion", method = RequestMethod.POST)
    //@PreAuthorize("@studentSecurity.hasAccessToWorksheet(authentication.name, #exerciseId)")
    //Maybe needed to ensure student has access to that worksheet
    public String handleFileUpload(@ModelAttribute Student student,
                                   @RequestParam("id") String exerciseId,
                                   @RequestParam("file") MultipartFile file) {
        testFileRepository.upload(student.getUsername().hashCode(), exerciseId, file);
        //The use of HashCode is not optimal, but the user id has some kind of error deep inside poodle and
        //it´s value is always 1
        return "redirect:/";
    }
}
