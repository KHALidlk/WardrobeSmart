package org.example.outfit.Controller;

import org.example.outfit.dto.PieceDTO;
import org.example.outfit.mapper.PieceMapper;
import org.example.outfit.model.Piece;
import org.example.outfit.Service.PieceService;
import org.springframework.beans.factory.annotation.Autowired;
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
}