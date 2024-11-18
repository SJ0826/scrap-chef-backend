package com.example.scrap_chef.util;

import com.example.scrap_chef.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        ApiResponse<T> response = new ApiResponse<>(0, "success", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success() {
        return success(null);
    }

    // error response는 데이터가 항상 null이기 때문에 타입을 엄격히 지정할 필요가 없음
    public static ResponseEntity<ApiResponse<?>> error(int code, String message) {
        ApiResponse<?> response = new ApiResponse<>(-1, message, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}



