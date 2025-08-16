package com.ai.SpringAiDemo;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class GenAIController {

    private ChatService chatService;

    private ImageService imageService;

    private RecipeService recipeService;

    @Autowired
    public GenAIController(ChatService chatService, ImageService imageService, RecipeService recipeService) {
        this.chatService = chatService;
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/ask-ai")
    public String getResponse(@RequestParam String prompt) {
        return chatService.getResponse(prompt);
    }

    @GetMapping("/ask-ai-options")
    public String getResponseOptions(@RequestParam String prompt) {
        return chatService.getResponseWithOptions(prompt);
    }

    @GetMapping("/generate-image")
    public void generateImage(HttpServletResponse response, @RequestParam String prompt) throws IOException {
        ImageResponse imageResponse = imageService.generateImage(prompt);
        String imageUrl = imageResponse.getResult().getOutput().getUrl();
        response.sendRedirect(imageUrl);
    }

    @GetMapping("/generate-image-options")
    public List<String> generateImageWithOptions(@RequestParam String prompt,
                                                 @RequestParam(defaultValue = "hd") String quality,
                                                 @RequestParam(defaultValue = "1") Integer n,
                                                 @RequestParam(defaultValue = "1024") Integer width,
                                                 @RequestParam(defaultValue = "1024") Integer height) throws IOException {
        ImageResponse imageResponse = imageService.generateImageWithOptions(prompt, quality, n, width, height);
        return imageResponse.getResults().stream().
                map(result -> result.getOutput().getUrl())
                .toList();
    }

    @GetMapping("/recipe-creator")
    public String recipeCreator(@RequestParam String ingredients,
                                @RequestParam(defaultValue = "any") String cuisine,
                                @RequestParam(defaultValue = "") String dietaryRestrictions) {
        return recipeService.createRecipe(ingredients, cuisine, dietaryRestrictions);
    }
}
