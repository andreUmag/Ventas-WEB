package app.ventasproject.repositoryIntegrationTest;

import app.ventasproject.models.Product;
import app.ventasproject.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
public class productRepositoryTest{
    ProductRepository productRepository;

    @Autowired
    public productRepositoryTest(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @BeforeEach
    void setUp(){
        productRepository.deleteAll();
    }

    @Test
    void findProductByID(){
        Product product = Product.builder()
                .name("test")
                .price(100f)
                .stock(12).build();
        productRepository.save(product);

        Long productID = product.getId();
        Optional<Product> foundProduct = productRepository.findProductById(productID);

        assertTrue(foundProduct.isPresent());
        Product product1 = foundProduct.get();
        assertEquals(product.getName(), product1.getName());
    }

    @Test
    void findProductInStock(){
        Product product1 = Product.builder()
                .name("test")
                .price(100f)
                .stock(12).build();

        Product product2 = Product.builder()
                .name("testA")
                .price(150f)
                .stock(0).build();

        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> productInStock = productRepository.ProductInStock();

        assertEquals(1, productInStock.size());
        assertEquals("test",productInStock.get(0).getName());
    }

    @Test
    void findProductByPriceandStock(){
        Product product1 = Product.builder()
                .name("test")
                .price(100f)
                .stock(12).build();

        Product product2 = Product.builder()
                .name("testA")
                .price(150f)
                .stock(0).build();

        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> products = productRepository.ProductFindPriceAndStock(120f,20);

        assertEquals(1, products.size());
        assertEquals("test",products.get(0).getName());
    }

    @Test
    void deletePaymentbyID(){
        Product product = Product.builder().build();

        productRepository.save(product);

        productRepository.deleteById(product.getId());

        assertFalse(productRepository.existsById(product.getId()));
    }
}
