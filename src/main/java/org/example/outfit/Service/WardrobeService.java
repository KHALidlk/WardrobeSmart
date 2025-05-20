package org.example.outfit.Service;

import org.example.outfit.Repository.UserRepo;
import org.example.outfit.Repository.WardrobeRepo;
import org.example.outfit.model.Outfit;
import org.example.outfit.model.Piece;
import org.example.outfit.model.User;
import org.example.outfit.model.Wardrobe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
@Service
public class WardrobeService {

    @Autowired
    private WardrobeRepo wardrobeRepo;

    @Autowired
    private PieceService pieceService;

    @Autowired
    private OutfitService outfitService;
    @Autowired
    private UserRepo userRepo;

    public List<Wardrobe> findAllWardrobes() {
        return wardrobeRepo.findAll();
    }

    public Wardrobe findWardrobeById(Long id) {
        return wardrobeRepo.findById(id).orElse(null);
    }

    public Wardrobe saveWardrobe(Wardrobe wardrobe) {
        return wardrobeRepo.save(wardrobe);
    }


    public void deleteWardrobeById(Long id) {
        wardrobeRepo.deleteById(id);
    }

    /**
     * Génère une tenue complète à partir de critères utilisateur simples.
     *
     * @param style    Style préféré (ex: CASUAL, FORMAL, SPORT)
     * @param occasion Occasion ciblée (ex: WORK, DATE, PARTY)
     * @param season   Saison (ex: WINTER, SUMMER)
     * @return Tenue générée avec 2 à 3 pièces
     */
    public Outfit generateOutfit1(Piece.Style style, Piece.Occasion occasion, Piece.Season season) {
        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Find the user's wardrobe
User user = userRepo.findByLogin(username)
        .orElseThrow(() -> new RuntimeException("User not found: " + username));

Wardrobe wardrobe = wardrobeRepo.findByUserId(user.getId())
        .orElseThrow(() -> new RuntimeException("No wardrobe found for user: " + username));
        // Get pieces from this user's wardrobe only
        List<Piece> pieces = pieceService.getPiecesByWardrobeId(wardrobe.getId());

        // Filter only available pieces first
        pieces = pieces.stream()
                .filter(p -> p.getStatus() == Piece.Status.AVAILABLE)
                .filter(p -> p.getSeasons().contains(season))
                .toList();

        // Create new outfit with initialized collections
        Outfit outfit = new Outfit();

        // Initialize collections
        outfit.setOccasions(new ArrayList<>());
        outfit.setSeasons(new ArrayList<>());

        // Add the specific occasion and season
        outfit.getOccasions().add(occasion);
        outfit.getSeasons().add(season);

        // Set other properties
        outfit.setStyle(style);
        outfit.setName(style + " outfit for " + season + " - " + occasion);
        outfit.setDescription("Generated " + style + " outfit for " + occasion + " in " + season);
        outfit.setLiked(false);
        outfit.setSaved(false);

        // Set the user's wardrobe
        outfit.setWardrobe(wardrobe);

        if (pieces.isEmpty()) {
            return outfit; // Return outfit with basic properties if no pieces match
        }

        // 1. TOP
        Piece top = findBestPiece(pieces, Piece.Category.TOP, null, false, style, occasion, season);
        if (top == null) return outfit;
        outfitService.attachOutfitPiece(outfit, top);

        // 2. BOTTOM
        boolean hasPattern = top.isPattern();
        Piece bottom = findBestPiece(pieces, Piece.Category.BOTTOM, top, hasPattern, style, occasion, season);
        if (bottom != null) outfitService.attachOutfitPiece(outfit, bottom);

        // 3. SHOES (optionnel)
        Piece shoes = findBestPiece(pieces, Piece.Category.SHOES, top, hasPattern, style, occasion, season);
        if (shoes != null) outfitService.attachOutfitPiece(outfit, shoes);

        // Log before saving to verify collections are properly populated
        System.out.println("Before saving - User: " + username);
        System.out.println("Before saving - Wardrobe ID: " + wardrobe.getId());
        System.out.println("Before saving - Style: " + outfit.getStyle());
        System.out.println("Before saving - Seasons: " + outfit.getSeasons());
        System.out.println("Before saving - Occasions: " + outfit.getOccasions());

        // Save and return
        return outfitService.save(outfit);
    }
    private Piece findBestPiece(List<Piece> pieces, Piece.Category category, Piece referencePiece, boolean referenceHasPattern,
                                Piece.Style style, Piece.Occasion occasion, Piece.Season season) {

        return pieces.stream()
                .filter(p -> p.getCategory() == category)
                .filter(p -> p.getStatus() == Piece.Status.AVAILABLE)
                .filter(p -> p.getSeasons().contains(season))
                .max(Comparator.comparingInt(p -> calculateScore(
                        p, referencePiece, referenceHasPattern, style, occasion, season)))
                .orElse(null);
    }

    private int calculateScore(Piece piece, Piece referencePiece, boolean referenceHasPattern,
                               Piece.Style style, Piece.Occasion occasion, Piece.Season season) {
        int score = 0;

        // 1. Motifs : éviter double motif
        if (!referenceHasPattern || !piece.isPattern()) {
            score += 20;
        }

        // 2. Style
        if (piece.getStyle().equals(style)) {
            score += 15;
        }

        // 3. Status - now handled in filters

        // 4. Occasion
        if (piece.getOccasions().contains(occasion)) {
            score += 10;
        }

        // 5. Saison - we already filter by season, but give extra points for exact match vs ALL
        if (piece.getSeasons().contains(season)) {
            score += 10; // Higher score for exact season match
        }
        // 6. Rating - prefer higher rated pieces
        score += piece.getRating();

        return score;
    }
}

//    // Fixed method without duplicate repository reference
//    public Outfit generateOutfit1(Piece.Type type, Piece.Status status, Piece.Style style, Piece.Occasion occasion, Piece.Season season, Piece.Category category, Piece.Color color) {
//        List<Piece> pieces = pieceService.getAllPieces();
//        Outfit outfit = new Outfit();
//
//        // 1. Choisir une pièce de type TOP (par exemple)
//        Optional<Piece> optionalTop = pieces.stream()
//                .filter(p -> p.getStyle().equals(style))
//                .filter(p -> p.getCategory() == Piece.Category.TOP)
//                .filter(p -> p.getStatus().equals(status))
//                .filter(p -> p.getOccasions().contains(occasion))
//                .filter(p -> p.getSeasons().contains(season))
//                .filter(p -> p.getType().equals(type))
//                .filter(p -> p.getColors().contains(color))
//                .findFirst();
//        if (optionalTop.isEmpty()) {
//            // Aucun haut trouvé : on retourne la tenue vide ou un message
//            return new Outfit();
//        }
//        Piece top = optionalTop.get();
//        outfitService.attachOutfitPiece(outfit, top);
//        // 2. Récupérer  l'information sur le pattern
//        boolean hasPattern = top.isPattern();
//        // 3. Filtrer les autres pièces en fonction de `hasPattern` et autres critères
//        List<Piece> bottomCandidates = pieces.stream()
//                .filter(p -> p.getCategory() == Piece.Category.BOTTOM)
//                .filter(p -> hasPattern ? !p.isPattern() : true) // Éviter double motif
//                .filter(p -> p.getStyle().equals(top.getStyle())) // Garder cohérence de style
//                .filter(p -> p.getStatus().equals(status))
//                .filter(p -> p.getOccasions().contains(occasion))
//                .filter(p -> p.getSeasons().contains(season))
//                .filter(p -> p.getType().equals(type))
//                .filter(p -> p.getColors().contains(color))
//                .collect(Collectors.toList());
//
//        // 4. Ajouter la meilleure pièce (ex: premier match) au résultat
//        Piece bottom = bottomCandidates.stream().findFirst().orElse(null);
//        outfitService.attachOutfitPiece(outfit, bottom);
//
//        return outfit;
//    }

    /**
     * Generate an outfit based on specified criteria in order:
     * 1. hasPattern (if previous piece has pattern)
     * 2. style
     * 3. category
     * 4. status
     * 5. occasion
     * 6. season
     * 7. type
     * 8. color
     *
     * @param style Preferred style for the outfit
     * @param occasion Preferred occasion for the outfit
     * @param season Preferred season for the outfit
     * @param preferredColor Optional preferred color
     * @return Generated outfit following criteria
     */
