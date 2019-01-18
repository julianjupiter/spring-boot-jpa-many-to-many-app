package io.github.julianjupiter.springbootmanytomany.controller;

import io.github.julianjupiter.springbootmanytomany.domain.Genre;
import io.github.julianjupiter.springbootmanytomany.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("genres", genreService.findAll());

        return "genre/index";
    }

    @GetMapping("/create")
    public String create(Genre genre) {
        return "genre/create";
    }

    @PostMapping("/create")
    public String create(@Valid Genre genre, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "genre/create";
        }

        genreService.save(genre);

        return "redirect:/genres";
    }
}
