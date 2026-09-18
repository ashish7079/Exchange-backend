package com.example.demo.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.InterViewServ;

@RestController
@RequestMapping("/api/interview")
public class InterViewContr {

    private final InterViewServ serv;


    public InterViewContr(InterViewServ serv) {
        this.serv = serv;
    }

    @PostMapping("/start")
    public ResponseEntity<?> startInterview(@RequestParam("language") String language) {

        return ResponseEntity.ok(
                serv.startInterview(language)
        );
    }

    @PostMapping("/answer")
    public ResponseEntity<?> submitAnswer( @RequestParam("interview_id") String interviewId,
    											@RequestParam("answer") String answer) {
    			 return ResponseEntity.ok( serv.submitAnswer( interviewId,answer)  
        );
    }
}
