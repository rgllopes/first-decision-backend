package br.com.api.controller;

import br.com.api.controller.dto.LoginRequestDTO;
import br.com.api.entities.Role;
import br.com.api.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    private  final JwtEncoder jwtEncoder;
    private final IUserRepository iUserRepository;
    private BCryptPasswordEncoder passwordEncoder;
    @Value("${expires.token.time}")
    private Long expiresTokenTime;

    public TokenController(JwtEncoder jwtEncoder, IUserRepository iUserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.iUserRepository = iUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        var user = iUserRepository.findByUserName(loginRequestDTO.userName());
        if(user.isEmpty() || !user.get().isLoginCorrect(loginRequestDTO, passwordEncoder)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário ou senha estão inválidos!");
        }

        var now = Instant.now();
        var scopes = user.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("api-login")
                .subject(user.get().getIdUser().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresTokenTime))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário e/ou senha estão inválidos!");
    }
}
