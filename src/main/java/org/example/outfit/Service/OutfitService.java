package org.example.outfit.Service;

import org.example.outfit.Repository.OutfitRepo;
import org.example.outfit.model.Outfit;
import org.example.outfit.model.Piece;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutfitService {

    @Autowired
    private OutfitRepo outfitRepoImpl;

    public List<Outfit> findBySaved(boolean saved) {
        return outfitRepoImpl.findBySaved(saved);
    }

    public List<Outfit> findByLiked(boolean liked) {
        return outfitRepoImpl.findByLiked(liked);
    }

    public List<Outfit> findByStyle(Piece.Style style) {
        return outfitRepoImpl.findByStyle(style);
    }

    public List<Outfit> findByOccasion(Piece.Occasion occasion) {
        return outfitRepoImpl.findByOccasionsContaining(occasion);
    }

    public List<Outfit> findBySeason(Piece.Season season) {
        return outfitRepoImpl.findBySeasonsContaining(season);
    }
    public List<Outfit> findByName(String name) {
        return outfitRepoImpl.findByNameContaining(name);
    }

    public Outfit save(Outfit outfit) {
        outfitRepoImpl.save(outfit);
        return outfit;
    }

    public void delete(Outfit outfit) {
        outfitRepoImpl.delete(outfit);
    }

    public void deleteById(Long id) {
        outfitRepoImpl.deleteById(id);
    }

    public List<Outfit> findAll() {
        return outfitRepoImpl.findAll();
    }

    public void detachOutfitPiece(Outfit outfit, Piece piece) {
        if (outfit == null || piece == null) return;

        switch (piece.getCategory()) {
            case TOP:
                if (outfit.getTop() != null && outfit.getTop().equals(piece)) {
                    outfit.setTop(null);
                }
                break;
            case BOTTOM:
                if (outfit.getPants() != null && outfit.getPants().equals(piece)) {
                    outfit.setPants(null);
                }
                break;
            case SHOES:
                if (outfit.getShoes() != null && outfit.getShoes().equals(piece)) {
                    outfit.setShoes(null);
                }
                break;
            default:
                break;
        }

        outfitRepoImpl.save(outfit);
    }

    public void attachOutfitPiece(Outfit outfit, Piece piece) {
        if (outfit == null || piece == null) return;

        switch (piece.getCategory()) {
            case TOP:
                outfit.setTop(piece);
                break;
            case BOTTOM:
                outfit.setPants(piece);
                break;
            case SHOES:
                outfit.setShoes(piece);
                break;
            default:
                break;
        }

        outfitRepoImpl.save(outfit);
    }


}