package br.com.api.controller.dto;

public record LoginResponseDTO(String accessToken, Long expiresIn) {
}
