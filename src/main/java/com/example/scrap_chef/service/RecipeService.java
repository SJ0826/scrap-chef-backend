package com.example.scrap_chef.service;

import com.example.scrap_chef.domain.RecipeResponseDto;
import io.netty.handler.codec.http.HttpScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {

    private static final String API_KEY = "1c2f5f6d21da479da907";
    private static final String REQUEST_HOST = "openapi.foodsafetykorea.go.kr";
    private static final String REQUEST_PATH = "/api/{API_KEY}/{SERVICE}/{TYPE}/{START_INDEX}/{END_INDEX}";
    private static final String TYPE = "json";
    private static final String SERVICE = "COOKRCP01";
    private static final int START_INDEX = 0;
    private static final int END_INDEX = 5;


    @Autowired
    private final WebClient webClient;

    public RecipeResponseDto getRecipes() {
        return webClient.get()
                .uri(UriComponentsBuilder.newInstance()
                        .scheme(HttpScheme.HTTP.toString())
                        .host(REQUEST_HOST)
                        .path(REQUEST_PATH)
                        .build(API_KEY, SERVICE, TYPE, START_INDEX, END_INDEX)
                        .toString())
                .retrieve()
                .bodyToMono(RecipeResponseDto.class)
                .block();

    }

}
