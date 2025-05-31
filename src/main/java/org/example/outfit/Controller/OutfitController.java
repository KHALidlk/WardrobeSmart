package org.example.outfit.Controller;
import org.example.outfit.Service.OutfitService;
import org.example.outfit.model.Outfit;
import org.example.outfit.model.Piece;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/outfits")
public class OutfitController {

    @Autowired
    private OutfitService outfitService;

    @GetMapping("/saved")
    public List<Outfit> getSavedOutfits(@RequestParam boolean saved) {
        return outfitService.findBySaved(saved);
    }

    @GetMapping("/liked/")
    public List<Outfit> getLikedOutfits(@RequestParam("liked") boolean liked) {
        return outfitService.findByLiked(liked);
    }
    @PutMapping("/{id}/liked")
    public Outfit updateOutfitLikedStatus(@PathVariable("id") Long id, @RequestParam("liked") boolean liked) {
        Outfit outfit = outfitService.findById(id);
        outfit.setLiked(liked);
        return outfitService.save(outfit);
    }

    @GetMapping("/style")
    public List<Outfit> getOutfitsByStyle(@RequestParam Piece.Style style) {
        return outfitService.findByStyle(style);
    }

    @GetMapping("/occasion")
    public List<Outfit> getOutfitsByOccasion(@RequestParam Piece.Occasion occasion) {
        return outfitService.findByOccasion(occasion);
    }

    @GetMapping("/season")
    public List<Outfit> getOutfitsBySeason(@RequestParam Piece.Season season) {
        return outfitService.findBySeason(season);
    }

    @GetMapping("/search")
    public List<Outfit> searchOutfitsByName(@RequestParam String name) {
        return outfitService.findByName(name);
    }

    @PostMapping
    public void saveOutfit(@RequestBody Outfit outfit) {
        outfitService.save(outfit);
    }

    @DeleteMapping
    public void deleteOutfit(@RequestBody Outfit outfit) {
        outfitService.delete(outfit);
    }

    @DeleteMapping("/{id}")
    public void deleteOutfitById(@PathVariable Long id) {
        outfitService.deleteById(id);
    }

    @GetMapping
    public List<Outfit> getAllOutfits() {
        return outfitService.findAll();
    }
}