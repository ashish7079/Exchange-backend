package com.example.demo.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class ResumeBuilderServ {

    private final RestClient restClient;

    public ResumeBuilderServ() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:5000")
                .build();
    }

    public byte[] ResumeBuild(String user_text) {

        MultiValueMap<String, Object> body =
                new LinkedMultiValueMap<>();

        body.add("user_text", user_text);

        return restClient.post()
                .uri("/build-resume")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(byte[].class);
    }
}