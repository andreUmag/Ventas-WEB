package app.ventasproject.dtos.client;

public record ClientToSaveDto(Long id, String name, String email,String password, String address, String role) {
}
