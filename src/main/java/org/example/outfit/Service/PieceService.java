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

    public void delete(Piece piece) {
        pieceRepository.delete(piece);
    }

    public void deleteById(Long id) {
        pieceRepository.deleteById(id);
    }

    public List<Piece> findAll() {
        return pieceRepository.findAll();
    }

    public void update(Piece piece) {
        pieceRepository.save(piece); // JPA's adduser method can be used for updates as well
    }
    public List<Piece> getPiecesByWardrobeId(Long wardrobeId) {
        return pieceRepository.findByWardrobeId(wardrobeId);
    }
    }
