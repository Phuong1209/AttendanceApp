package com.example.demo.ControllerUI;

import com.example.demo.dto.JobTypeDTO;
import com.example.demo.model.JobType;
import com.example.demo.service.JobType.IJobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/jobtypes")
public class JobTypeUIController {
    @Autowired
    private IJobTypeService jobTypeService;
    private Object jobtypes;

    //Show JobType List
    @GetMapping({"", "/"})
    public String listJobType(Model model) {
        Iterable<JobType> jobtypes = jobTypeService.findAll();
        model.addAttribute("jobTypes", jobtypes);
        return "jobtype/jobtype_list";
    }

    @GetMapping({ "/create"})

    public String createJobTypeForm(Model model) {
        /*JobType jobType = new JobType();*/
        model.addAttribute("jobTypes", new JobType());
        return "jobtype/jobtype_create";
    }
    // Handle Form Submission
    @PostMapping("/create")
    public String createJobType(JobType jobType) {
        jobTypeService.save(jobType);
        return "redirect:/jobtypes"; // Redirect back to the list after creation
    }

    // Show Update Form
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<JobType> optionalJobType = jobTypeService.findById(id);
        if (optionalJobType.isEmpty()) {
            return "redirect:/jobtypes"; // Redirect to list if not found
        }

        JobType jobType = optionalJobType.get();
        JobTypeDTO jobTypeDTO = new JobTypeDTO();
        jobTypeDTO.setId(jobType.getId());
        jobTypeDTO.setName(jobType.getName());

        model.addAttribute("jobTypeDTO", jobTypeDTO);
        return "jobtype/jobtype_update";
    }

    // Handle Update Submission
    @PostMapping("/update/{id}")
    public String updateJobType(@PathVariable Long id, @ModelAttribute("jobTypeDTO") JobTypeDTO jobTypeDTO) {
        try {
            // Fetch the existing JobType
            Optional<JobType> optionalJobType = jobTypeService.findById(id);
            if (optionalJobType.isEmpty()) {
                return "redirect:/jobtypes"; // Redirect if not found
            }

            JobType existingJobType = optionalJobType.get();
            existingJobType.setName(jobTypeDTO.getName()); // Update fields

            jobTypeService.save(existingJobType); // Save updated JobType
            return "redirect:/jobtypes"; // Redirect to list
        } catch (Exception e) {
            // Log the error (optional)
            return "redirect:/jobtypes";
        }
    }






/* // Show Update Form
 @GetMapping("/update/{id}")
 public String showUpdateForm(@PathVariable("id") Long id, Model model) {
     Optional<JobType> jobType = jobTypeService.findById(id);
     if (jobType.isPresent()) {
         model.addAttribute("jobType", jobType);
         return "jobtype/jobtype_update";
     } else {
         // Handle case when job type is not found
         return "redirect:/jobtypes";  // Redirect back to the list page
     }
 }

 // Handle Update Form Submission
 @PostMapping("/update/{id}")
 public String updateJobType(@PathVariable("id") Long id, @ModelAttribute("jobType") JobType jobType) {
     jobType.setId(id); // Ensure the ID stays the same
     jobTypeService.save(jobType); // Save the updated job type
     return "redirect:/jobtypes";  // Redirect back to the list page
 }*/
@PostMapping("/delete/{id}")
public String deleteJobType(@PathVariable Long id) {
    jobTypeService.remove(id);
    return "redirect:/jobtypes";
}

}



