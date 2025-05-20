package org.example.outfit.Repository;

import org.example.outfit.model.Outfit;
import org.example.outfit.model.Piece;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutfitRepo extends JpaRepository<Outfit, Long> {
    List<Outfit> findBySaved(boolean saved);
    List<Outfit> findByLiked(boolean liked);
    List<Outfit> findByStyle(Piece.Style style);
    List<Outfit> findByOccasionsContaining(Piece.Occasion occasion);
    List<Outfit> findBySeasonsContaining(Piece.Season season);
    List<Outfit> findByNameContaining(String name);
}
