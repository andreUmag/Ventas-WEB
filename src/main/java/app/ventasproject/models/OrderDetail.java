package app.ventasproject.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String addressOrder;
    private String transporter;
    private String guide;

    @ManyToOne
    @JoinColumn(name = "idOrder")
    private Order order;
}
