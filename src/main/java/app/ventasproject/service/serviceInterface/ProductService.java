package app.ventasproject.service.serviceInterface;

import app.ventasproject.dto.product.ProductDto;
import app.ventasproject.dto.product.ProductToSaveDto;

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