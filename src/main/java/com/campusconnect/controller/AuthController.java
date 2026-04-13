package com.campusconnect.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.model.User;
import com.campusconnect.repository.UserRepository;
import com.campusconnect.repository.JobRepository;
import com.campusconnect.repository.ApplicationRepository;

// 🔐 ADD THIS IMPORT
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private ApplicationRepository appRepo;

    // 🔐 ADD THIS
    @Autowired
    private BCryptPasswordEncoder encoder;

    // LOGIN PAGE
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // REGISTER PAGE
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // 🔐 REGISTER (ENCRYPT PASSWORD)
    @PostMapping("/register")
    public String register(User user) {

        user.setPassword(encoder.encode(user.getPassword())); // 🔐 encryption

        repo.save(user);
        return "redirect:/login";
    }

    // 🔐 LOGIN (MATCH ENCRYPTED PASSWORD)
    @PostMapping("/login")
    public String login(User user, Model model, HttpSession session) {

        User u = repo.findByEmail(user.getEmail());

        if (u != null && encoder.matches(user.getPassword(), u.getPassword())) {

            session.setAttribute("user", u);

            if (u.getRole().equals("STUDENT")) {
                return "redirect:/dashboard";
            } else if (u.getRole().equals("FACULTY")) {
                return "redirect:/faculty-dashboard";
            } else {
                return "redirect:/company-dashboard";
            }
        }

        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {

        if (session.getAttribute("user") == null)
            return "redirect:/login";

        long jobsCount = jobRepo.count();
        long applicationsCount = appRepo.count();

        model.addAttribute("jobsCount", jobsCount);
        model.addAttribute("applicationsCount", applicationsCount);

        return "dashboard";
    }

    // FACULTY DASHBOARD
    @GetMapping("/faculty-dashboard")
    public String facultyDashboard(HttpSession session) {
        if (session.getAttribute("user") == null)
            return "redirect:/login";
        return "faculty-dashboard";
    }

    // COMPANY DASHBOARD
    @GetMapping("/company-dashboard")
    public String companyDashboard(HttpSession session) {
        if (session.getAttribute("user") == null)
            return "redirect:/login";
        return "company-dashboard";
    }
}