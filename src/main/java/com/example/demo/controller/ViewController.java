package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/camiones")
    public String camionesPage() {
        return "camiones";
    }

    @GetMapping("/conductores")
    public String conductoresPage() {
        return "conductores";
    }

    @GetMapping("/viajes")
    public String viagesPage() {
        return "viajes";
    }

    @GetMapping("/liquidaciones")
    public String liquidacionesPage() {
        return "liquidaciones";
    }

    @GetMapping("/estadisticas")
    public String estadisticasPage() {
        return "estadisticas";
    }
}
