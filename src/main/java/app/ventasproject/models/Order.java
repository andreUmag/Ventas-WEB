package app.ventasproject.models;
import jakarta.persistence.*;
import lombok.*;
import app.ventasproject.Enum.StatusEnum;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime orderDate;
    private StatusEnum status; //poner un enum con los estados PENDIENTE, ENVIADO y ENTREGADO

    @OneToMany(mappedBy = "orders")
    List<Payment> payments;

    @OneToMany(mappedBy = "orders")
    List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "idClient", nullable = false)
    private Client client;
}
