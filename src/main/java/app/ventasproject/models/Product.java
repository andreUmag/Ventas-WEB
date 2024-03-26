package app.ventasproject.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Float price;
    private Integer stock;

    @OneToMany(mappedBy = "products")
    List<OrderItem> orderItems;
}
