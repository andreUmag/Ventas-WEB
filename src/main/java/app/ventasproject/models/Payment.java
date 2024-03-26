package app.ventasproject.models;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import app.ventasproject.Enum.MethodsEnum;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Float totalPayment;
    private LocalDateTime datePayment;
    private MethodsEnum method;//se usara un enum con los metodos de pago

    @ManyToOne
    @JoinColumn(name = "idOrder")
    private Order order;
}
