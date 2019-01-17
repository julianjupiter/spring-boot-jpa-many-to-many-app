package io.github.julianjupiter.springbootmanytomany.controller;

import io.github.julianjupiter.springbootmanytomany.domain.Game;
import io.github.julianjupiter.springbootmanytomany.service.GameService;
import io.github.julianjupiter.springbootmanytomany.service.LanguageService;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GameController {
    private GameService gameService;
    private LanguageService languageService;

    public GameController(GameService gameService, LanguageService languageService) {
        this.gameService = gameService;
        this.languageService = languageService;
    }

    @ModelAttribute
    public void commonAttributes(Model model) {
        model.addAttribute("languages", languageService.findAll());
    }

    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(List.class, "languages", new CustomCollectionEditor(List.class) {
            @Override
            protected Object convertElement(Object element) {
                String id = (String) element;
                return languageService.findById(Long.valueOf(id)).orElse(null);
            }
        });
    }

    @GetMapping
    public String index(Model model) {
        Iterable<Game> games = gameService.findAll();
        model.addAttribute("games", games);

        return "game/index";
    }

    @GetMapping("/create")
    public String create(Model model, Game game) {

        return "game/create";
    }

    @PostMapping("/create")
    public String create(@Valid Game game, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "game/create";
        }

        gameService.save(game);

        return "redirect:/games";
    }
}
