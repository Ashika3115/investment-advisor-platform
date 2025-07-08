package com.investment.common.apiresponse;


import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private String status; // "success" / "error"
    private String message;
    private T data;

    public ApiResponse(String s, HttpStatus httpStatus) {
        this.status = httpStatus.is2xxSuccessful() ? "success" : "error";
        this.message = s;
        this.data = null; // Default to null if no data is provided
    }
}
