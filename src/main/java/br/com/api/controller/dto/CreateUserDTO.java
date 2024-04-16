package br.com.api.controller.dto;

public record CreateUserDTO (String userName, String email, String password, String confirmPassword) {
}
