package br.com.api.controller;

import br.com.api.controller.dto.CreateUserDTO;
import br.com.api.entities.Role;
import br.com.api.entities.User;
import br.com.api.repository.IRoleRepository;
import br.com.api.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {
    private final IUserRepository iUserRepository;
    private final IRoleRepository iRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${min.password.caracters}")
    private Integer minPasswordCaracters;

    @Value("${max.password.caracters}")
    private Integer maxPasswordCaracters;

    @Value("${min.user.caracters}")
    private Integer minUserCaracters;

    @Value("${max.user.caracters}")
    private Integer maxUserCaracters;

    public UserController(IUserRepository iUserRepository, IRoleRepository iRoleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.iUserRepository = iUserRepository;
        this.iRoleRepository = iRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/api/create-user")
    @Transactional
    public ResponseEntity<String> newUser(@RequestBody CreateUserDTO createUserDTO) throws Exception {
        String validateDatas = null;

        //Validação nome de usuário
        validateDatas = validaUsuario(createUserDTO);
        if (validateDatas != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validateDatas);
        }

        //Validação formato de email
        validateDatas = validaEmail(createUserDTO);
        if (validateDatas != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validateDatas);
        }

        // Valida password
        validateDatas = validaPassword(createUserDTO);
        if(validateDatas != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validateDatas);
        }

        var user = new User();
        var basicRole = iRoleRepository.findByName((Role.Values.BASIC.name()));
        user.setUserName(createUserDTO.userName());
        user.setEmail(createUserDTO.email());
        user.setPassword(passwordEncoder.encode(createUserDTO.password()));
        user.setRoles(Set.of(basicRole));

        iUserRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
    }

    private String validaUsuario(CreateUserDTO createUserDTO) {
        String userValidated = null;
        var userFromDB = iUserRepository.findByUserName(createUserDTO.userName());
        if (userFromDB.isPresent() || !userFromDB.isEmpty()) {
            return userValidated = "Nome de usuário ja esta na base de dados.";
        } else if(createUserDTO.userName().length() < minUserCaracters || createUserDTO.userName().length() > maxUserCaracters) {
            return userValidated = "Nome de usuário deve conter entre " + minUserCaracters + " e "  + maxUserCaracters + " caracteres.";
        }
        return userValidated;
    }

    private String validaEmail(CreateUserDTO createUserDTO) {
        String userValidated = null;
        String checkEmail = createUserDTO.email();
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._]{1,16}+@{1}+[a-z]{1,7}\\.[a-z]{1,3}$");
        Matcher mail = pattern.matcher(checkEmail);
        if (!mail.find()) {
            return userValidated = "Não é um email válido.";
        }
        return userValidated;
    }

    private  String validaPassword(CreateUserDTO createUserDTO) {
        String userValidated = null;

        String checkPassword = createUserDTO.password();
        String checkConfirmPassword = createUserDTO.confirmPassword();
        if(checkPassword.isEmpty()) {
            return userValidated = "Campo 'senha' não pode estar vazio ";
        } else if (checkPassword.length() < minPasswordCaracters || checkPassword.length() > maxPasswordCaracters) {
            return userValidated = "Campo 'senha' deve conter entre " + minPasswordCaracters + " e " + maxPasswordCaracters + " caracteres.";
        } else if(!checkPassword.equals(checkConfirmPassword)) {
            return userValidated = "Senhas não coincidem!";
        }
        return userValidated;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/api/users")
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> listUsers() {
        var users = iUserRepository.findAll();
        return  ResponseEntity.ok(users);
    }
}
