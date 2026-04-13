package com.campusconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.campusconnect.model.Profile;
import com.campusconnect.repository.ProfileRepository;

import java.io.File;
import java.io.IOException;

@Controller
public class ProfileController {

    @Autowired
    private ProfileRepository repo;

    // show form
    @GetMapping("/profile")
    public String profilePage() {
        return "profile";
    }

    // save profile
    @PostMapping("/profile")
    public String saveProfile(@RequestParam String name,
                              @RequestParam String skills,
                              @RequestParam String bio,
                              @RequestParam("imageFile") MultipartFile file,
                              Model model) throws IOException {

        // ✅ CORRECT PATH (INSIDE STATIC)
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        file.transferTo(new File(uploadDir + fileName));

        Profile p = new Profile();
        p.setName(name);
        p.setSkills(skills);
        p.setBio(bio);
        p.setImage(fileName);

        repo.save(p);

        // ✅ LOAD DATA DIRECTLY (NO REDIRECT ISSUE)
        model.addAttribute("profiles", repo.findAll());
        return "view-profile";
    }

    // view profiles
    @GetMapping("/view-profile")
    public String viewProfile(Model model) {
        model.addAttribute("profiles", repo.findAll());
        return "view-profile";
    }
}