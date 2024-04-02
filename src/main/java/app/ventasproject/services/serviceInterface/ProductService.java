package app.ventasproject.services.serviceInterface;

import app.ventasproject.dtos.product.ProductDto;
import app.ventasproject.dtos.product.ProductToSaveDto;

import java.util.List;

public interface ProductService {
    ProductDto saveProduct(ProductToSaveDto product);
    ProductDto updateProduct(Long id, ProductToSaveDto product);
    ProductDto searchProductById(Long id);
    List<ProductDto> getAllProduct();
    List<ProductDto> searchProductByStock();
    List<ProductDto> searchProductByPriceAndStock(Float price, Integer stock);
    void removeProduct(Long id);
}