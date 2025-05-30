package org.example.outfit.Service;

import org.example.outfit.Repository.PieceRepository;
import org.example.outfit.model.Piece;
//import org.example.outfit.Repository.PieceRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PieceService {

    @Autowired
    private PieceRepository pieceRepository;

    public List<Piece> getPiecesByCategory(Piece.Category category) {
        return pieceRepository.findByCategory(category);
    }
    public void addPiece(Piece piece)
    {
        pieceRepository.save(piece);
    }
    public List<Piece> getPiecesByStyle(Piece.Style style) {
        return pieceRepository.findByStyle(style);
    }

    public List<Piece> getPiecesByOccasion(Piece.Occasion occasion) {
        return pieceRepository.findByOccasionsContaining(occasion);
    }

    public List<Piece> findBySeason(Piece.Season seasons) {
        return pieceRepository.findBySeasonsContaining(seasons);
    }

    public List<Piece> findByColor(Piece.Color color) {
        return pieceRepository.findByColorsContaining(color);
    }

    public List<Piece> findByDescription(String description) {
        return pieceRepository.findByDescription(description);
    }

    public List<Piece> findByRating(int rating) {
        return pieceRepository.findByRating(rating);
    }

    public void save(Piece piece) {
        pieceRepository.save(piece);
    }

    public void delete(Long id) {
        Piece piece = pieceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piece not found with id: " + id));
        pieceRepository.delete(piece);
    }

    public void deleteById(Long id) {
        pieceRepository.deleteById(id);
    }

    public List<Piece> findAll() {
        return pieceRepository.findAll();
    }

    public Piece update(Piece piece) {
        if (piece.getId() == null) {
            throw new IllegalArgumentException("Cannot update piece without an ID");
        }

        // Check if the piece exists
        pieceRepository.findById(piece.getId())
            .orElseThrow(() -> new RuntimeException("Piece not found with id: " + piece.getId()));

        // Save and return the updated piece
        return pieceRepository.save(piece);
    }

    public Piece findById(Long id) {
        return pieceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Piece not found with id: " + id));
    }

    public List<Piece> getPiecesByWardrobeId(Long wardrobeId) {
        return pieceRepository.findByWardrobeId(wardrobeId);
    }

    /**
     * Toggle the liked status of a piece
     * @param id The ID of the piece to toggle liked status
     * @return The updated piece with the new liked status
     */
    public Piece toggleLiked(Long id) {
        Piece piece = findById(id);
        piece.setLiked(!piece.isLiked()); // Toggle the current value
        return pieceRepository.save(piece);
    }

    /**
     * Set the liked status of a piece to a specific value
     * @param id The ID of the piece to update
     * @param liked The new liked status to set
     * @return The updated piece with the new liked status
     */
    public Piece setLiked(Long id, boolean liked) {
        Piece piece = findById(id);
        piece.setLiked(liked);
        return pieceRepository.save(piece);
    }

    /**
     * Get all pieces that are liked
     * @return List of liked pieces
     */
    public List<Piece> getLikedPieces() {
        return pieceRepository.findByLiked(true);
    }
}
