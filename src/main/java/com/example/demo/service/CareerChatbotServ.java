package com.example.demo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CareerChatbotServ {

    private final RestClient restClient;

    public CareerChatbotServ(
            @Value("${CHATBOT_AI_SERVICE_URL}")
            String chatbotAiServiceUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(chatbotAiServiceUrl)
                .build();
    }

    // =========================================
    // Upload PDF
    // =========================================

    public String uploadPdf(MultipartFile pdf) throws IOException {

        MultiValueMap<String, Object> body =
                new LinkedMultiValueMap<>();

        body.add(
                "pdf",
                new org.springframework.core.io.ByteArrayResource(
                        pdf.getBytes()
                ) {
                    @Override
                    public String getFilename() {
                        return pdf.getOriginalFilename();
                    }
                }
        );

        return restClient.post()
                .uri("/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(String.class);
    }

    // =========================================
    // Chat
    // =========================================

    public String chat(
            String userText,
            String sessionId) {

        MultiValueMap<String, Object> body =
                new LinkedMultiValueMap<>();

        body.add("user_text", userText);

        if (sessionId != null &&
                !sessionId.trim().isEmpty()) {

            body.add("session_id", sessionId);
        }

        return restClient.post()
                .uri("/chat")
                .contentType(
                        MediaType.APPLICATION_FORM_URLENCODED
                )
                .body(body)
                .retrieve()
                .body(String.class);
    }
}
