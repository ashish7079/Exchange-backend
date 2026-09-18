package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {

    private final RestClient restClient;

    public ResumeService(
            @Value("${AI_SERVICE_URL}") String aiServiceUrl
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(aiServiceUrl)
                .build();
    }

    public String analyzeResume(MultipartFile resume, String jd) {

        try {

            ByteArrayResource pdfResource =
                    new ByteArrayResource(resume.getBytes()) {

                        @Override
                        public String getFilename() {
                            return resume.getOriginalFilename();
                        }
                    };

            MultiValueMap<String, Object> body =
                    new LinkedMultiValueMap<>();

            body.add("resume", pdfResource);
            body.add("jd", jd);

            String response = restClient.post()
                    .uri("/analyze")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(body)
                    .retrieve()
                    .body(String.class);

            return response;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error while calling Python AI service: "
                    + e.getMessage()
            );
        }
    }
}
