package app.ventasproject.repositorys;
import app.ventasproject.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductById(Long id);
    @Query("SELECT p FROM Product p WHERE p.stock >= 1")
    List<Product> ProductInStock();
    @Query("SELECT p FROM Product p WHERE p.price < ?1 and p.stock < ?2")
    List<Product> ProductFindPriceAndStock(Float price, Integer stock);
}