package org.example.outfit.Controller;

import org.example.outfit.model.ImageAnalysisResponse;
import org.example.outfit.Service.BlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/blip")
public class BlipController {

    private final BlipService blipService;

    @Autowired
    public BlipController(BlipService blipService) {
        this.blipService = blipService;
    }

    @GetMapping("/test-blip")
    public ResponseEntity<ImageAnalysisResponse> testBlip() throws IOException {
        File imageFile = new File("C:/Users/pc/Desktop/download.jpg");

        // Vérifie que le fichier existe
        if (!imageFile.exists()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Appelle une nouvelle méthode dans BlipService qui prend un fichier classique
        ImageAnalysisResponse response = blipService.sendImageFileToBlipServer(imageFile);
        return ResponseEntity.ok(response);
    }
}
