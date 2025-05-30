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
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

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
        String imageUrl = "";

        // Télécharger l'image dans un fichier temporaire
        File tempFile = File.createTempFile("image", "https://res.cloudinary.com/fatimaelksakass-cloud/image/upload/v1748531274/t8tscjpadd5q5ep3pwl1.jpg\n");
        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.copy(in, tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        // Appel du service BLIP
        ImageAnalysisResponse response = blipService.sendImageFileToBlipServer(tempFile);

        // Supprimer le fichier temporaire après traitement si nécessaire
        tempFile.delete();

        return ResponseEntity.ok(response);
    }
}
