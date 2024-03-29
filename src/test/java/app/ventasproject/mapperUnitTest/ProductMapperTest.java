package app.ventasproject.mapperUnitTest;

import app.ventasproject.dto.product.ProductDto;
import app.ventasproject.dto.product.ProductMapper;
import app.ventasproject.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductMapperTest {
    Product product;
    ProductDto productDto;

    @BeforeEach
    void setUp(){
        product = Product.builder().name("Pepsi").price(23F).stock(2).build();

        productDto = new ProductDto(
                1L,
                "Pepsi",
                23F,
                4,
                null);
    }
    @Test
    public void productEntitytoDTO(){
        ProductDto productDto = ProductMapper.INSTANCE.productEntitytoProductDto(product);

        assertNotNull(productDto);
        assertEquals(product.getName(), productDto.name());
    }
    @Test
    public void productDTOtoEntity(){
        Product product = ProductMapper.INSTANCE.productDtotoProductEntity(productDto);

        assertNotNull(product);
        assertEquals(productDto.stock(), product.getStock());
    }
}
