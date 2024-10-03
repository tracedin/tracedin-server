package com.univ.tracedin.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/health")
@Hidden
public class HealthCheckApi {

    @GetMapping
    public String healthCheck() {
        return "health check success";
    }
}
