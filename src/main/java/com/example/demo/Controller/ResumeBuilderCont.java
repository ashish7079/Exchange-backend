package com.example.demo.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ResumeBuilderServ;

@RestController
@RequestMapping("/api/resumeBuild")
@CrossOrigin(origins = "http://localhost:5173")
public class ResumeBuilderCont {

    private final ResumeBuilderServ serv;

    public ResumeBuilderCont(ResumeBuilderServ serv) {
        this.serv = serv;
    }

    @PostMapping("/build-resume")
    public ResponseEntity<byte[]> resumeBuild(
            @RequestParam("user_text") String user_text) {

        byte[] pdf = serv.ResumeBuild(user_text);

        return ResponseEntity.ok()
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=Generated_Resume.pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}