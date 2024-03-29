package app.ventasproject.dto.product;

public record ProductToSaveDto(Long id, String name, Float price, Integer stock) {
}
