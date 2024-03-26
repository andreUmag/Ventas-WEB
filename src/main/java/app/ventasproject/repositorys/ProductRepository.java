package app.ventasproject.repositorys;

import app.ventasproject.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public class ProductRepository extends JpaRepository<Product, Long> {

}
