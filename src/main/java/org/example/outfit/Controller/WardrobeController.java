package org.example.outfit.Controller;

import org.example.outfit.Service.WardrobeService;
import org.example.outfit.model.Outfit;
import org.example.outfit.model.Piece;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wardrobe")  // Keep your existing path
public class WardrobeController {

    @Autowired
    private WardrobeService wardrobeService;

    @GetMapping("/generate-outfit")
    public ResponseEntity<?> generateOutfit(
            @RequestParam Piece.Style style,
            @RequestParam Piece.Occasion occasion,
            @RequestParam Piece.Season season
    ) {
        try {
            // Get outfit using the currently logged-in user's wardrobe
            Outfit outfit = wardrobeService.generateOutfit1(style, occasion, season);
            return ResponseEntity.ok(outfit);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/styles")
    public ResponseEntity<List<Piece.Style>> getAllStyles() {
        return ResponseEntity.ok(Arrays.asList(Piece.Style.values()));
    }

    @GetMapping("/occasions")
    public ResponseEntity<List<Piece.Occasion>> getAllOccasions() {
        return ResponseEntity.ok(Arrays.asList(Piece.Occasion.values()));
    }

    @GetMapping("/seasons")
    public ResponseEntity<List<Piece.Season>> getAllSeasons() {
        return ResponseEntity.ok(Arrays.asList(Piece.Season.values()));
    }
}