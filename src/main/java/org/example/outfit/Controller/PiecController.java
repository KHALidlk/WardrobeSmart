package org.example.outfit.Controller;

import org.example.outfit.dto.PieceDTO;
import org.example.outfit.mapper.PieceMapper;
import org.example.outfit.model.Piece;
import org.example.outfit.Service.PieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pieces")
public class PiecController {
    @Autowired
    private PieceService pieceService;

    @Autowired
    private PieceMapper pieceMapper;

    // ✅ Ajouter une pièce
    @PostMapping
    public void addPiece(@RequestBody PieceDTO pieceDTO) {
        Piece piece = pieceMapper.pieceDTOToPiece(pieceDTO);
        pieceService.addPiece(piece);
    }
    // ✅ Récupérer toutes les pièces
    @GetMapping
    public List<PieceDTO> getAllPieces() {
        return pieceService.findAll().stream()
                .map(pieceMapper::pieceToPieceDTO)
                .collect(Collectors.toList());
    }

    // ✅ Rechercher par catégorie
    @GetMapping("/category/{category}")
    public List<PieceDTO> getByCategory(@PathVariable Piece.Category category) {
        return pieceService.getPiecesByCategory(category).stream()
                .map(pieceMapper::pieceToPieceDTO)
                .collect(Collectors.toList());
    }

    // Other methods similarly updated...
    @DeleteMapping("/delete/{id}")
    public void deletePiece(@PathVariable Long id) {
        pieceService.deleteById(id);
    }
    // For example, update the style endpoint
    @GetMapping("/style/{style}")
    public List<PieceDTO> getByStyle(@PathVariable Piece.Style style) {
        return pieceService.getPiecesByStyle(style).stream()
                .map(pieceMapper::pieceToPieceDTO)
                .collect(Collectors.toList());
    }

    // Add a convenience endpoint to get pieces by wardrobe ID
    @GetMapping("/wardrobe/{wardrobeId}")
    public List<PieceDTO> getByWardrobe(@PathVariable Long wardrobeId) {
        // You'll need to add this method to your PieceService
        return pieceService.getPiecesByWardrobeId(wardrobeId).stream()
                .map(pieceMapper::pieceToPieceDTO)
                .collect(Collectors.toList());
    }

    // Add a new endpoint to update a piece
    @PutMapping("/update/{id}")
    public ResponseEntity<PieceDTO> updatePiece(@PathVariable Long id, @RequestBody PieceDTO pieceDTO) {
        // Set the ID from the path variable to ensure we're updating the correct piece
        pieceDTO.setId(id);

        try {
            // Convert DTO to entity
            Piece piece = pieceMapper.pieceDTOToPiece(pieceDTO);

            // Update the piece
            Piece updatedPiece = pieceService.update(piece);

            // Convert back to DTO and return
            return ResponseEntity.ok(pieceMapper.pieceToPieceDTO(updatedPiece));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Add an endpoint to get a piece by ID
    @GetMapping("/{id}")
    public ResponseEntity<PieceDTO> getPieceById(@PathVariable Long id) {
        try {
            Piece piece = pieceService.findById(id);
            return ResponseEntity.ok(pieceMapper.pieceToPieceDTO(piece));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Add an endpoint to toggle the liked status of a piece
    @PutMapping("/{id}/toggle-like")
    public ResponseEntity<PieceDTO> toggleLiked(@PathVariable Long id) {
        try {
            Piece updatedPiece = pieceService.toggleLiked(id);
            return ResponseEntity.ok(pieceMapper.pieceToPieceDTO(updatedPiece));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Add an endpoint to like a piece (set liked status to true)
    @PutMapping("/like/{id}")
    public ResponseEntity<PieceDTO> likePiece(@PathVariable Long id) {
        try {
            Piece updatedPiece = pieceService.likePiece(id);
            return ResponseEntity.ok(pieceMapper.pieceToPieceDTO(updatedPiece));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Add an endpoint to dislike a piece (set liked status to false)
    @PutMapping("/dislike/{id}")
    public ResponseEntity<PieceDTO> dislikePiece(@PathVariable Long id) {
        try {
            Piece updatedPiece = pieceService.dislikePiece(id);
            return ResponseEntity.ok(pieceMapper.pieceToPieceDTO(updatedPiece));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Add an endpoint to get all liked pieces
    @GetMapping("/liked")
    public List<PieceDTO> getLikedPieces() {
        return pieceService.getLikedPieces().stream()
                .map(pieceMapper::pieceToPieceDTO)
                .collect(Collectors.toList());
    }
}
