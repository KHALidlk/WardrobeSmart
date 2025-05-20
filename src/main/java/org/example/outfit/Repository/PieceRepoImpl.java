//package org.example.outfit.Repository;
//
//import org.example.outfit.model.Piece;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class PieceRepoImpl {
//    @Autowired
//    private PieceRepository pieceRepository;
//
//    public List<Piece> findByCategory(Piece.Category category) {
//        return pieceRepository.findByCategory(category);
//    }
//    public void addPiece(Piece piece)
//    {
//        pieceRepository.save(piece);
//    }
//
//    public List<Piece> findByStyle(Piece.Style style) {
//        return pieceRepository.findByStyle(style);
//    }
//
//    public List<Piece> findByOccasion(Piece.Occasion occasions) {
//        return pieceRepository.findByOccasionsContaining(occasions);
//    }
//
//    public List<Piece> findBySeason(Piece.Season seasons) {
//        return pieceRepository.findBySeasonsContaining(seasons);
//    }
//
//    public List<Piece> findByColor(Piece.Color color) {
//        return pieceRepository.findByColorsContaining(color);
//    }
//
//    public List<Piece> findByDescription(String description) {
//        return pieceRepository.findByDescription(description);
//    }
//
//    public List<Piece> findByRating(int rating) {
//        return pieceRepository.findByRating(rating);
//    }
//
//    public void save(Piece piece) {
//        pieceRepository.save(piece);
//    }
//
//    public void delete(Piece piece) {
//        pieceRepository.delete(piece);
//    }
//
//    public void deleteById(Long id) {
//        pieceRepository.deleteById(id);
//    }
//
//    public List<Piece> findAll() {
//        return pieceRepository.findAll();
//    }
//
//    public void update(Piece piece) {
//        pieceRepository.save(piece); // JPA's adduser method can be used for updates as well
//    }
//    public List<Piece> findByWardrobeId(Long wardrobeId) {
//        return pieceRepository.findByWardrobeId(wardrobeId);
//    }}