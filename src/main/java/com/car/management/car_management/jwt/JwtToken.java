package com.car.management.car_management.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {
    private String token;
    private String type = "Bearer";

    public JwtToken(String token) {
        this.token = token;
    }
}