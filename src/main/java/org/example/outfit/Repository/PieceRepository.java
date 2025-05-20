package org.example.outfit.Repository;
import org.example.outfit.model.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PieceRepository extends JpaRepository<Piece, Long> {
    List<Piece> findByCategory(Piece.Category category);
    List<Piece> findByStyle(Piece.Style style);
    List<Piece> findByOccasionsContaining(Piece.Occasion occasion); // corrigé
    List<Piece> findBySeasonsContaining(Piece.Season season);       // corrigé
    List<Piece> findByColorsContaining(Piece.Color color);          // corrigé
    List<Piece> findByDescription(String description);
    List<Piece> findByRating(int rating);
    List<Piece> findByWardrobeId(Long wardrobeId);

}

