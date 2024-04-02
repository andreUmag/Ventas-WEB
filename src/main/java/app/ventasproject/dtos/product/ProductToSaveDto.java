package app.ventasproject.dtos.product;

public record ProductToSaveDto(Long id, String name, Float price, Integer stock) {
}
