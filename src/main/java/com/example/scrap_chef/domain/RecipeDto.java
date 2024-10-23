package com.example.scrap_chef.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

public class RecipeDto {
    @Setter(onMethod = @__(@JsonSetter(value = "RCP_SEQ")))
    @Getter(onMethod = @__(@JsonGetter(value = "id")))
    private String id;

    @Setter(onMethod = @__(@JsonSetter(value = "ATT_FILE_NO_MAIN")))
    @Getter(onMethod = @__(@JsonGetter(value = "imageUrl")))
    private String imageUrl;

    @Setter(onMethod = @__(@JsonSetter(value = "RCP_NM")))
    @Getter(onMethod = @__(@JsonGetter(value = "name")))
    private String recipeName;

    @Setter(onMethod = @__(@JsonSetter(value = "RCP_PARTS_DTLS")))
    @Getter(onMethod = @__(@JsonGetter(value = "ingredients")))
    private String[] ingredients;

    @Getter
    @Setter
    private ManualStepDto[] manualSteps;


    @Getter
    @Setter
    public static class ManualStepDto {
        private String step; // 설명
        private String imageUrl; // 이미지 URL

        public ManualStepDto(String manual, String manualImg) {
            this.step = manual;
            this.imageUrl = manualImg;
        }

        @Override
        public String toString() {
            return "ManualStepDto{" +
                    "manual='" + step + '\'' +
                    ", manualImg='" + imageUrl + '\'' +
                    '}';
        }
    }
}
