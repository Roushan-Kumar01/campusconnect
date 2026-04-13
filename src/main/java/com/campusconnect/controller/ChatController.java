package com.campusconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.model.Message;
import com.campusconnect.repository.MessageRepository;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository repo;

    // open chat page
    @GetMapping("/chat")
    public String chatPage(Model model) {
        model.addAttribute("messages", repo.findAll());
        return "chat";
    }

    // send message
    @PostMapping("/send")
    public String sendMessage(@RequestParam String sender,
                              @RequestParam String receiver,
                              @RequestParam String content) {

        Message m = new Message();
        m.setSender(sender);
        m.setReceiver(receiver);
        m.setContent(content);

        repo.save(m);

        return "redirect:/chat";
    }
}