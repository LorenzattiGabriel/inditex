package com.example.Inditex.prices.web.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError {

    @Setter
    @Getter
    private Integer errorCode;

    @Setter
    @Getter
    private String errorMessage;

    @Setter
    @Getter
    private String action;
}
