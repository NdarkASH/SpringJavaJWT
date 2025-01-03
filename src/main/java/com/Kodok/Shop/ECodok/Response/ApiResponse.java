package com.Kodok.Shop.ECodok.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse {
        private String message;
        private String data;
}
