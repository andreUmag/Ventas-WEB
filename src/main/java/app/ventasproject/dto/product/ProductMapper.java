package app.ventasproject.dto.product;
import app.ventasproject.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductDto productEntitytoProductDto(Product prodcut);
    Product productDtotoProductEntity(ProductDto productDto);
    Product productToSaveDToToEntity(ProductToSaveDto product);
}
