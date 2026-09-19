package com.example.demo.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Service
public class ResumeBuilderServ {

    private final RestClient restClient;

    public ResumeBuilderServ(
            @Value("${RESUME_BUILDER_AI_SERVICE_URL}")
            String resumeBuilderAiServiceUrl
    ) {

        SimpleClientHttpRequestFactory factory =
                new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(Duration.ofSeconds(30));
        factory.setReadTimeout(Duration.ofSeconds(120));

        this.restClient = RestClient.builder()
                .baseUrl(resumeBuilderAiServiceUrl)
                .requestFactory(factory)
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
