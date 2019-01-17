package io.github.julianjupiter.springbootmanytomany.controller;

import io.github.julianjupiter.springbootmanytomany.service.GameService;
import io.github.julianjupiter.springbootmanytomany.service.LayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/layers")
public class LayerController {
    private LayerService layerService;

    public LayerController(LayerService layerService) {
        this.layerService = layerService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("layers", layerService.findAll());

        return "layer/index";
    }
}
