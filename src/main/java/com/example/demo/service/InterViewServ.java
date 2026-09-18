package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class InterViewServ {

    private final RestClient restClient;

    public InterViewServ(
            @Value("${INTERVIEW_AI_SERVICE_URL}") String interviewAiServiceUrl
    ) {

        this.restClient = RestClient.builder()
                .baseUrl(interviewAiServiceUrl)
                .build();
    }


    public String startInterview(String language) {

        MultiValueMap<String, Object> body =
                new LinkedMultiValueMap<>();

        body.add("language", language);

        return restClient.post()
                .uri("/interview/start")
                .contentType(
                        MediaType.APPLICATION_FORM_URLENCODED
                )
                .body(body)
                .retrieve()
                .body(String.class);
    }


    public String submitAnswer(
            String interviewId,
            String answer
    ) {

        MultiValueMap<String, Object> body =
                new LinkedMultiValueMap<>();

        body.add("interview_id", interviewId);
        body.add("answer", answer);

        return restClient.post()
                .uri("/interview/answer")
                .contentType(
                        MediaType.APPLICATION_FORM_URLENCODED
                )
                .body(body)
                .retrieve()
                .body(String.class);
    }
}
