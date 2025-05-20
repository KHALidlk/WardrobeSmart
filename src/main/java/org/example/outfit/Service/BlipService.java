package org.example.outfit.Service;

import org.example.outfit.model.ImageAnalysisResponse;
import org.example.outfit.Utils.MultipartInputStreamFileResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

@Service
public class BlipService {

    private final RestTemplate restTemplate;

    @Autowired
    public BlipService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Envoie une image locale au serveur BLIP et récupère la réponse.
     */
    public ImageAnalysisResponse sendImageFileToBlipServer(File imageFile) {
        try (FileInputStream inputStream = new FileInputStream(imageFile)) {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Créer le corps multipart avec le fichier image
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            Resource imageResource = new MultipartInputStreamFileResource(inputStream, imageFile.getName());
            body.add("image", imageResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            String serverUrl = "http://localhost:5000/analyze";

            ResponseEntity<ImageAnalysisResponse> responseEntity = restTemplate.exchange(
                    serverUrl,
                    HttpMethod.POST,
                    requestEntity,
                    ImageAnalysisResponse.class
            );

            ImageAnalysisResponse response = responseEntity.getBody();
            System.out.println(response.getAttributes()+response.getDescription());
            if (response == null) {
                throw new IllegalStateException("La réponse du serveur BLIP est vide");
            }

            if (response.getAttributes() == null) {
                response.setAttributes(new HashMap<>()); // éviter les NullPointerException
            }

            return response;

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'image au serveur BLIP", e);
        }
    }
}
