package com.example.scrap_chef.service;

import com.example.scrap_chef.domain.RecipeDto;
import com.example.scrap_chef.domain.RecipeOpenApiDto;
import com.example.scrap_chef.domain.RecipeOpenApiResponseDto;
import com.example.scrap_chef.domain.RecipeResponseDto;
import com.example.scrap_chef.dto.ApiResponse;
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
    private static final String REQUEST_PATH = "/api/{API_KEY}/{SERVICE}/{TYPE}/{START_INDEX}/{END_INDEX}/";
    private static final String TYPE = "json";
    private static final String SERVICE = "COOKRCP01";
    private static final int START_INDEX = 0;
    private static final int END_INDEX = 5;

    @Autowired
    private final WebClient webClient;

    public Mono<ApiResponse<RecipeResponseDto>> getRecipes(List<String> ingredients) {
        String uri = buildUri(ingredients);
        System.out.println(uri);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(RecipeOpenApiResponseDto.class)
                .map(this::convertToRecipeResponse)
                .map(recipeResponse -> new ApiResponse<>("레시피 조회 성공", recipeResponse))
                .doOnError(error -> log.error("Error fetching recipes: {}", error.getMessage()));
    }

    /**
     * URI 빌더 로직을 별도의 메서드로 분리
     **/
    private String buildUri(List<String> ingredients) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUri(UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(REQUEST_HOST)
                .path(REQUEST_PATH)
                .build(API_KEY, SERVICE, TYPE, START_INDEX, END_INDEX));

        StringBuilder pathBuilder = new StringBuilder();

        // 재료 리스트를 경로에 추가
        for (String ingredient : ingredients) {
            if (!pathBuilder.isEmpty()) {
                pathBuilder.append(",");
            }
            pathBuilder.append(ingredient);
        }

        uriBuilder.path("/RCP_PARTS_DTLS=").path(pathBuilder.toString());

        return uriBuilder.toUriString();
    }

    /**
     * API 응답을 RecipeResponseDto로 변환하는 메서드
     **/
    private RecipeResponseDto convertToRecipeResponse(RecipeOpenApiResponseDto openApiResponse) {
        RecipeResponseDto recipeResponseDto = new RecipeResponseDto();
        RecipeOpenApiResponseDto.CookRcp01 openApiCookRcp = openApiResponse.getRecipes();
        RecipeResponseDto.CookRcp01 responseCookRcp = new RecipeResponseDto.CookRcp01();

        // totalCount 복사
        responseCookRcp.setTotalCount(openApiResponse.getRecipes().getTotalCount());

        // 각 레시피를 변환하여 저장
        if (openApiCookRcp.getRecipe() != null) {
            List<RecipeDto> recipeList = openApiCookRcp.getRecipe().stream()
                    .map(this::convertToRecipeDto) // item -> convertToRecipeDto(item)
                    .toList();
            responseCookRcp.setRecipe(recipeList);
        }

        // 변환된 CookRcp01 객체를 RecipeResponseDto에 설정
        recipeResponseDto.setRecipes(responseCookRcp);
        return recipeResponseDto;
    }

    /**
     * RecipeOpenApiResponseDto 내부 RecipeDto 변환 메서드
     **/
    private RecipeDto convertToRecipeDto(RecipeOpenApiDto openApiRecipeDto) {
        RecipeDto recipeDto = new RecipeDto();

        recipeDto.setId(openApiRecipeDto.getId());
        recipeDto.setImageUrl(openApiRecipeDto.getImageUrl());
        recipeDto.setRecipeName(openApiRecipeDto.getRecipeName());

        // 재료 문자열을 배열로 변환하여 저장
        String ingredientsString = openApiRecipeDto.getIngredients();
        if (ingredientsString != null) {
            List<String> ingredientsArray = List.of(ingredientsString.split("(\\n|\\s*,\\s*)"));
            recipeDto.setIngredients(ingredientsArray);
        }

        // 만드는 순서와 이미지 처리
        List<RecipeDto.ManualStepDto> manualStepList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String stepField = "MANUAL" + String.format("%02d", i);
            String stepImgField = "MANUAL_IMG" + String.format("%02d", i);

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
                }

            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("Error accessing fields for RecipeDto: {}", e.getMessage());
            }

        }
        // 배열의 타입을 위해 전달된 0, 리스트가 호출될 때 배열의 크기가 동적으로 전달
        recipeDto.setManualSteps(manualStepList.toArray(new RecipeDto.ManualStepDto[0]));
        return recipeDto;
    }
}
