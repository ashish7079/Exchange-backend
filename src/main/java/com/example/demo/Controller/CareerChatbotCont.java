package com.example.demo.Controller;
import com.example.demo.service.CareerChatbotServ;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/chatbot")
public class CareerChatbotCont {

    private final CareerChatbotServ serv;

    public CareerChatbotCont(
            CareerChatbotServ serv) {

        this.serv = serv;
    }


    // =========================================
    // Upload PDF
    // =========================================

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPdf(
            @RequestParam("pdf") MultipartFile pdf) {

        try {

            return ResponseEntity.ok(
                    serv.uploadPdf(pdf)
            );

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                        "{\"error\":\"" +
                        e.getMessage() +
                        "\"}"
                    );
        }
    }


    // =========================================
    // Chat
    // =========================================

    @PostMapping("/chat")
    public ResponseEntity<?> chat(

            @RequestParam("user_text")
            String userText,

            @RequestParam(
                    value = "session_id",
                    required = false
            )
            String sessionId) {

        try {

            return ResponseEntity.ok(
                    serv.chat(
                            userText,
                            sessionId
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                        "{\"error\":\"" +
                        e.getMessage() +
                        "\"}"
                    );
        }
    }
}
