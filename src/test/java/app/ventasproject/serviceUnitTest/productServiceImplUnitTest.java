package app.ventasproject.serviceUnitTest;

import app.ventasproject.dtos.product.ProductDto;
import app.ventasproject.dtos.product.ProductMapper;
import app.ventasproject.dtos.product.ProductToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Product;
import app.ventasproject.repositories.ProductRepository;
import app.ventasproject.services.serviceImplement.PaymentsServiceImpl;
import app.ventasproject.services.serviceImplement.ProdcutServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class productServiceImplUnitTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProdcutServiceImpl productsService;

    private Product product;
    private ProductDto productDto;
    private ProductToSaveDto productToSaveDto;

    @BeforeEach
    void setUp(){
        product = Product.builder().id(1L).name("Pepsi").stock(23).price(4200F).build();

        productToSaveDto = new ProductToSaveDto(product.getId(), product.getName(),product.getPrice(),product.getStock());

        productDto = ProductMapper.INSTANCE.productEntitytoProductDto(product);
    }

    @Test
    void testSavedProduct(){
        given(productRepository.save(any())).willReturn(product);

        when(productMapper.productEntitytoProductDto(any())).thenReturn(productDto);

        ProductDto productDto = productsService.saveProduct(productToSaveDto);

        assertThat(productDto).isNotNull(); assertThat(productDto.id()).isEqualTo(1L);
    }

    @Test
    void testUpdateProduct(){
        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

        when(productMapper.productEntitytoProductDto(any())).thenReturn(productDto);

        ProductDto updatedProduct = productsService.updateProduct(product.getId(), productToSaveDto);

        assertEquals(productDto, updatedProduct);
    }

    @Test
    void testSearchProductByIdFound(){
        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

        when(productMapper.productEntitytoProductDto(product)).thenReturn(productDto);

        ProductDto productFound = productsService.searchProductById(product.getId());

        assertEquals(productDto, productFound);
    }

    @Test
    void testSearchProductByIdNotFound() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productsService.searchProductById(product.getId()));
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = new ArrayList<>(); products.add(product);

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.productEntitytoProductDto(product)).thenReturn(productDto);

        List<ProductDto> allProducts = productsService.getAllProduct();

        assertEquals(productDto, allProducts.get(0));
    }

    @Test
    void testSearchProductByStock() {
        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productRepository.ProductInStock()).thenReturn(products);
        when(productMapper.productEntitytoProductDto(product)).thenReturn(productDto);

        List<ProductDto> productsFound = productsService.searchProductByStock();

        assertEquals(productDto, productsFound.get(0));
    }

    @Test
    void testSearchProductByPriceAndStock() {
        Float price = 10000f;
        Integer stock = 100;

        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productRepository.ProductFindPriceAndStock(price, stock)).thenReturn(products);
        when(productMapper.productEntitytoProductDto(product)).thenReturn(productDto);

        List<ProductDto> productsFound = productsService.searchProductByPriceAndStock(price, stock);

        assertEquals(productDto, productsFound.get(0));
        System.out.println(productsFound);
    }
    @Test
    public void testDeleteProduct() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        productsService.deleteProduct(product.getId());

        verify(productRepository).delete(product);
    }

    @Test
    public void testDeleteProductNotFound() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productsService.deleteProduct(product.getId()));
    }
}
