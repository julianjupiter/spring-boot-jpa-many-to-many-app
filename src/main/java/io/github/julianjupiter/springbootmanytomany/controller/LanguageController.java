package io.github.julianjupiter.springbootmanytomany.controller;

import io.github.julianjupiter.springbootmanytomany.domain.Language;
import io.github.julianjupiter.springbootmanytomany.service.LanguageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/languages")
public class LanguageController {
    private LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("languages", languageService.findAll());
        return "language/index";
    }

    @GetMapping("/create")
    public String create(Language language) {
        return "language/create";
    }

    @PostMapping("/create")
    public String create(@Valid Language language, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "language/create";
        }

        languageService.save(language);

        return "redirect:/languages";
    }
}
