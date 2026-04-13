package com.campusconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.model.Job;
import com.campusconnect.model.Application;
import com.campusconnect.model.User;
import com.campusconnect.repository.JobRepository;
import com.campusconnect.repository.ApplicationRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class JobController {

    @Autowired
    private JobRepository repo;

    @Autowired
    private ApplicationRepository appRepo;

    // ✅ view jobs (protected)
    @GetMapping("/jobs")
    public String viewJobs(Model model, HttpSession session) {

        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        model.addAttribute("jobs", repo.findAll());
        return "jobs";
    }

    // ✅ add job page (protected)
    @GetMapping("/add-job")
    public String addJobPage(HttpSession session) {

        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        return "add-job";
    }

    // ✅ save job (protected)
    @PostMapping("/add-job")
    public String saveJob(Job job, HttpSession session) {

        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        repo.save(job);
        return "redirect:/jobs";
    }

    // ✅ APPLY JOB (with real logged user)
    @GetMapping("/apply/{title}")
    public String applyJob(@PathVariable String title, HttpSession session) {

        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        // get logged-in user
        User user = (User) session.getAttribute("user");

        Application app = new Application();
        app.setStudentName(user.getName()); // ✅ dynamic user
        app.setJobTitle(title);
        app.setStatus("APPLIED");

        appRepo.save(app);

        return "redirect:/jobs";
    }
}