package com.example.spring.controller;

import com.example.spring.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/{exercicioId}")
    public Map<String, Object> verProgresso(@PathVariable Long exercicioId) {
        return dashboardService.progressoDoExercicio(exercicioId);
    }
}
