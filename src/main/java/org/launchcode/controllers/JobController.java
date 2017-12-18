package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam int id) {
        Job aJob = jobData.findById(id);
        model.addAttribute("job", aJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "new-job";
        }





        String newString = jobForm.getName();
        Employer newEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location newLocation = jobForm.getLocation();
        PositionType newPosition = jobForm.getPositionType();
        CoreCompetency newCore = jobForm.getCoreCompetency();

        Job newJob = new Job(newString, newEmployer, newLocation, newPosition, newCore);



        jobData.add(newJob);
        Job aJob = jobData.findById(newJob.getId());
        model.addAttribute("job", aJob);


        return "redirect:/job?id="+ aJob.getId();

    }
}
