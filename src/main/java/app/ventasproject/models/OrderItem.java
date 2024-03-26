package app.ventasproject.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer quantity;
    private Float unitPrice;

    @ManyToOne
    @JoinColumn(name = "idOrder")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Product product;
}
