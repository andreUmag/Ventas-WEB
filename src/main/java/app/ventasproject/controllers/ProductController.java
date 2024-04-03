package app.ventasproject.controllers;

import app.ventasproject.dtos.product.ProductDto;
import app.ventasproject.dtos.product.ProductToSaveDto;
import app.ventasproject.exceptions.NotAbleToDeleteException;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.services.serviceInterface.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductToSaveDto productSaveDto){
        ProductDto productCreated = productService.saveProduct(productSaveDto);
        logger.info(productCreated.toString());
        return ResponseEntity.ok().body(productCreated);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getProduct(){
        List<ProductDto> productDtoList = productService.getAllProduct();
        productDtoList.forEach((productDto -> logger.info(productDto.toString())));
        return ResponseEntity.ok().body(productDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") long id){
        try {
            ProductDto productDto = productService.searchProductById(id);
            return ResponseEntity.ok().body(productDto);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id, @RequestBody ProductToSaveDto productSaveDto){
        try {
            ProductDto productUpdate = productService.updateProduct(id, productSaveDto);
            return ResponseEntity.ok().body(productUpdate);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/instock")
    public ResponseEntity<List<ProductDto>> inStock(){
        List<ProductDto> productDtoList = productService.searchProductByStock();
        return ResponseEntity.ok().body(productDtoList);
    }
    @GetMapping("/precio-stock")
    public ResponseEntity<List<ProductDto>> searchProductByPriceAndStock(@RequestParam("priceProduct") Float price, @RequestParam("stockProduct") Integer stock){
        List<ProductDto> productsSearch = productService.searchProductByPriceAndStock(price, stock);
        return ResponseEntity.ok().body(productsSearch);
    }
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().body("eliminado.");
        } catch (NotAbleToDeleteException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
