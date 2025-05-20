package org.example.outfit.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pieces")
public class Piece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Category {
        TOP, BOTTOM, SHOES,
    }

    @Enumerated(EnumType.STRING)
    private Category category;

    public enum Status {
        AVAILABLE, UNAVAILABLE, DONATED, SOLD
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(length = 1000)
    private String description;

    public enum Color {
        BLACK,
        WHITE,
        BLUE,
        RED,
        GREEN,
        YELLOW,
        PURPLE,
        PINK,
        GRAY,   // ← changé de GREY à GRAY pour correspondre à "gray" du frontend
        BROWN
    }


    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "piece_colors", joinColumns = @JoinColumn(name = "piece_id"))
    @Column(name = "color")
    private List<Color> colors;

    public enum Season {
        SPRING,
        SUMMER,
        FALL,
        WINTER
    }

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "piece_seasons", joinColumns = @JoinColumn(name = "piece_id"))
    @Column(name = "season")
    private List<Season> seasons;
    private Double price;
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;
//MODERN TRADITIONELLE
public enum Style {
    MODERN,
    TRADITIONAL
}


    @Enumerated(EnumType.STRING)
    private Style style;

    public enum Occasion {
        CASUAL,
        SMARTCASUAL,
        WORK,
        EVENING,
        FORMAL,
        LOUNGEWEAR,
        SPORT,
        OUTDOOR
    }


    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "piece_occasions", joinColumns = @JoinColumn(name = "piece_id"))
    @Column(name = "occasion")
    private List<Occasion> occasions;

    public enum Fabric {
        COTTON,
        LINEN,
        DENIM,
        POLYESTER,
        SILK,
        WOOL,
        LEATHER
    }


    @Enumerated(EnumType.STRING)
    private Fabric fabric;

    public enum Type {
        SHIRT,
        TSHIRT,
        BLOUSE,
        SWEATER,
        HOODIE,
        CROPTOP,
        JACKET,
        COAT,
        VEST,
        CARDIGAN,
        PANTS,
        SHORTS,
        SKIRT,
        LEGGINGS,
        TIGHTS,
        OVERALLS,
        DRESS,
        JUMPSUIT,
        ROMPER,
        KAFTAN,
        ABAYA,
        JELLABA,
        JABADOR,
        GANDOURA,
        SNEAKERS,
        BOOTS,
        HEELS,
        SANDALS,
        FLAT,
        LOAFERS,
        SLIPPERS,
        FLIPFLOPS
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = true)
    private Type type;

    private boolean pattern;

    private int rating;

    private boolean available = true;
    @ManyToOne
    @JoinColumn(name = "wardrobe_id")
    @JsonBackReference
    private Wardrobe wardrobe;
    private String imageUrl;
    @Override
    public String toString() {
        return "Piece{" +
                "id=" + id +
                ", category=" + category +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", colors=" + colors +
                ", seasons=" + seasons +
                ", price=" + price +
                ", purchaseDate=" + purchaseDate +
                ", style=" + style +
                ", occasions=" + occasions +
                ", fabric=" + fabric +
                ", type=" + type +
                ", pattern=" + pattern +
                ", rating=" + rating +
                ", imageUrl='" + imageUrl + '\'' +
                ", available=" + available +
                '}';
    }
}

