package io.github.julianjupiter.springbootmanytomany.controller;

import io.github.julianjupiter.springbootmanytomany.domain.Game;
import io.github.julianjupiter.springbootmanytomany.domain.Screenshot;
import io.github.julianjupiter.springbootmanytomany.domain.Video;
import io.github.julianjupiter.springbootmanytomany.service.GameService;
import io.github.julianjupiter.springbootmanytomany.service.GenreService;
import io.github.julianjupiter.springbootmanytomany.service.LanguageService;
import io.github.julianjupiter.springbootmanytomany.service.StorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;
    private final LanguageService languageService;
    private final GenreService genreService;
    @Qualifier("screenshotStorageService")
    private final StorageService screenshotStorageService;
    @Qualifier("videoStorageService")
    private final StorageService videoStorageService;

    public GameController(GameService gameService,
                          LanguageService languageService,
                          GenreService genreService,
                          StorageService screenshotStorageService,
                          StorageService videoStorageService) {
        this.gameService = gameService;
        this.languageService = languageService;
        this.genreService = genreService;
        this.screenshotStorageService = screenshotStorageService;
        this.videoStorageService = videoStorageService;
    }

    @ModelAttribute
    public void commonAttributes(Model model) {
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("genres", genreService.findAll());
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

        webDataBinder.registerCustomEditor(List.class, "genres", new CustomCollectionEditor(List.class) {
            @Override
            protected Object convertElement(Object element) {
                String id = (String) element;
                return genreService.findById(Long.valueOf(id)).orElse(null);
            }
        });
    }

    @GetMapping
    public String index(Model model) {
        Iterable<Game> games = gameService.findAll();
        model.addAttribute("games", games);

        return "game/index";
    }

    @GetMapping("/view/{id}")
    public String view(Model model, @PathVariable Long id) {
        Game game = gameService.findById(id)
                .orElse(null);
        model.addAttribute("game", game);

        return "game/view";
    }

    @GetMapping("/create")
    public String create(Model model, Game game) {
        return "game/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute @Valid Game game, BindingResult bindingResult, MultipartFile[] screenshotFiles, MultipartFile[] videoFiles) {
        if (screenshotFiles != null) {
            List<Screenshot> screenshots = Arrays.stream(screenshotFiles)
                    .filter(file -> !file.isEmpty())
                    .map(file -> {
                        screenshotStorageService.store(file);
                        String fileName = file.getOriginalFilename();
                        Path path = screenshotStorageService.load(fileName);
                        String filePath = MvcUriComponentsBuilder.fromMethodName(GameController.class,
                                "serveScreenshotFile", path.getFileName().toString()).build().toString();

                        Screenshot screenshot = new Screenshot();
                        screenshot.setName(fileName);
                        screenshot.setPath(filePath);
                        return screenshot;
                    })
                    .collect(Collectors.toList());
            if (screenshots.size() > 0) {
                game.setScreenshots(screenshots);
            }
        }

        if (videoFiles != null) {
            List<Video> videos = Arrays.stream(videoFiles)
                    .filter(file -> !file.isEmpty())
                    .map(file -> {
                        videoStorageService.store(file);
                        String fileName = file.getOriginalFilename();
                        Path path = videoStorageService.load(fileName);
                        String filePath = MvcUriComponentsBuilder.fromMethodName(GameController.class,
                                "serveVideoFile", path.getFileName().toString()).build().toString();

                        Video video = new Video();
                        video.setName(fileName);
                        video.setPath(filePath);
                        return video;
                    })
                    .collect(Collectors.toList());
            if (videos.size() > 0) {
                game.setVideos(videos);
            }
        }

        if (bindingResult.hasErrors()) {
            return "game/create";
        }

        gameService.save(game);

        return "redirect:/games/view/" + game.getId();
    }

    @GetMapping("/screenshots/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveScreenshotFile(@PathVariable String filename) {

        Resource file = screenshotStorageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/videos/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveVideoFile(@PathVariable String filename) {

        Resource file = videoStorageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
