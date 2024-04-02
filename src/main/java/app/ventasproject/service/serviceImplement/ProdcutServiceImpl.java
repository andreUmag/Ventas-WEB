package app.ventasproject.service.serviceImplement;

import app.ventasproject.dto.product.ProductDto;
import app.ventasproject.dto.product.ProductMapper;
import app.ventasproject.dto.product.ProductToSaveDto;
import app.ventasproject.exception.NotFoundException;
import app.ventasproject.models.Product;
import app.ventasproject.repositorys.ProductRepository;
import app.ventasproject.service.serviceInterface.ProductService;

import java.util.List;

public class ProdcutServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProdcutServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto saveProduct(ProductToSaveDto productDto) {
        Product product = productMapper.productToSaveDToToEntity(productDto);
        Product productSaved = productRepository.save(product);
        return productMapper.productEntitytoProductDto(productSaved);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductToSaveDto product) {
        return null;
    }

    @Override
    public ProductDto searchProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("No encontrado"));
        return productMapper.productEntitytoProductDto(product);
    }

    @Override
    public List<ProductDto> getAllProduct() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> productMapper.productEntitytoProductDto(product))
                .toList();
    }

    @Override
    public List<ProductDto> searchProductByStock() {
        List<Product> products = productRepository.ProductInStock();
        return products.stream()
                .map(product -> productMapper.productEntitytoProductDto(product))
                .toList();
    }

    @Override
    public List<ProductDto> searchProductByPriceAndStock(Float price, Integer stock) {
        List<Product> products = productRepository.ProductFindPriceAndStock(price, stock);
        return products.stream()
                .map(product -> productMapper.productEntitytoProductDto(product))
                .toList();
    }

    @Override
    public void removeProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
    }
}
