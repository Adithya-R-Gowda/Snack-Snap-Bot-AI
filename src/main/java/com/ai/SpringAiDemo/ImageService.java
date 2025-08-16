package com.ai.SpringAiDemo;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final OpenAiImageModel openAiImageModel;

    @Autowired
    public ImageService(OpenAiImageModel openAiImageModel) {
        this.openAiImageModel = openAiImageModel;
    }

    public ImageResponse generateImage(String prompt) {
        ImageResponse response = openAiImageModel.call(
                new ImagePrompt(prompt)
        );
        return response;
    }

    public ImageResponse generateImageWithOptions(String prompt,
                                                  String quality,
                                                  Integer n,
                                                  Integer width,
                                                  Integer height) {
        ImageResponse response = openAiImageModel.call(
                new ImagePrompt(prompt,
                        OpenAiImageOptions.builder()
                                .model("dall-e-2")
                                .quality(quality)
                                .N(n)
                                .height(height)
                                .width(width)
                                .build())
        );
        return response;
    }
}
