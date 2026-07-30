package br.com.nonna_back.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("health-check/liveness")
    public String liveness(){
        return "OK";
    }
}
