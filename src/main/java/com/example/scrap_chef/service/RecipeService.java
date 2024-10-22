package com.example.scrap_chef.service;

import com.example.scrap_chef.domain.RecipeDto;
import com.example.scrap_chef.domain.RecipeOpenApiDto;
import com.example.scrap_chef.domain.RecipeOpenApiResponseDto;
import com.example.scrap_chef.domain.RecipeResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.*;


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

    public Mono<RecipeResponseDto> getRecipes() {
        return webClient.get()
                .uri(UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host(REQUEST_HOST)
                        .path(REQUEST_PATH)
                        .build(API_KEY, SERVICE, TYPE, START_INDEX, END_INDEX)
                )
                .retrieve() // 요청을 수행하고 응답을 가져옴
                .bodyToMono(RecipeOpenApiResponseDto.class) // json응답이 DTO객체로 역직렬화
                .map(openApiResponse -> {
                    // RecipeResponseDto로 변환
                    RecipeResponseDto recipeResponseDto = new RecipeResponseDto();
                    RecipeOpenApiResponseDto.CookRcp01 openApiCookRcp = openApiResponse.getRecipes();
                    RecipeResponseDto.CookRcp01 responseCookRcp = new RecipeResponseDto.CookRcp01();

                    // totalCount 복사
                    responseCookRcp.setTotalCount(openApiCookRcp.getTotalCount());

                    // 각 레시피를 변환하여 저장
                    if (openApiCookRcp.getRecipe() != null) {
                        List<RecipeDto> recipeList = new ArrayList<>();
                        openApiCookRcp.getRecipe().forEach(openApiRecipeDto -> {
                            RecipeDto recipeDto = new RecipeDto();
                            recipeDto.setId(openApiRecipeDto.getId());
                            recipeDto.setImageUrl(openApiRecipeDto.getImageUrl());
                            recipeDto.setRecipeName(openApiRecipeDto.getRecipeName());

                            // 재료 문자열을 배열로 변환하여 저장
                            String ingredientsString = openApiRecipeDto.getIngredients();
                            if (ingredientsString != null) {
                                String[] ingredientsArray = ingredientsString.split("(\\n|\\s*,\\s*)");
                                recipeDto.setIngredients(ingredientsArray);
                            }

                            // 만드는 순서와 이미지 처리
                            List<RecipeDto.ManualStepDto> manualStepList = new ArrayList<>();
                            for (int i = 1; i <= 20; i++) {
                                String stepField = "MANUAL" + String.format("%02d", i); // MANUAL01, MANUAL02, ...
                                String stepImgField = "MANUAL_IMG" + String.format("%02d", i); // MANUAL_IMG01, MANUAL_IMG02, ...

                                try {
                                    // 필드에 접근하기 위해 리플렉션을 사용
                                    Field step = openApiRecipeDto.getClass().getDeclaredField(stepField);
                                    Field img = openApiRecipeDto.getClass().getDeclaredField(stepImgField);

                                    // 필드 접근 허용
                                    step.setAccessible(true);
                                    img.setAccessible(true);

                                    // 필드 값 가져오기
                                    String manual = (String) step.get(openApiRecipeDto);
                                    String manualImg = (String) img.get(openApiRecipeDto);

                                    // null 체크 후 리스트에 추가
                                    if (!Objects.equals(manual, "")) {
                                        manualStepList.add(new RecipeDto.ManualStepDto(manual, manualImg));
                                        System.out.println(new RecipeDto.ManualStepDto(manual, manualImg));
                                    }

                                } catch (NoSuchFieldException | IllegalAccessException e) {
                                    log.error("Error accessing fields for RecipeDto: {}", e.getMessage());
                                }
                            }
                            log.info("Manual Step List: {}", manualStepList);
                            // 수집한 제조 단계를 RecipeDto에 설정
                            recipeDto.setManualSteps(manualStepList.toArray(new RecipeDto.ManualStepDto[0]));

                            recipeList.add(recipeDto);
                        });
                        responseCookRcp.setRecipe(recipeList);
                    }

                    // 변환된 CookRcp01 객체를 RecipeResponseDto에 설정
                    recipeResponseDto.setRecipes(responseCookRcp);
                    return recipeResponseDto;
                });
    }
}
