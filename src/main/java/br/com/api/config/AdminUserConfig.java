package br.com.api.config;

import br.com.api.entities.Role;
import br.com.api.entities.User;
import br.com.api.repository.IRoleRepository;
import br.com.api.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private IRoleRepository iRoleRepository;
    private IUserRepository iUserRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(IRoleRepository iRoleRepository,
                           IUserRepository iUserRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.iRoleRepository = iRoleRepository;
        this.iUserRepository = iUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = iRoleRepository.findByName(Role.Values.ADMIN.name());
        var userAdmin = iUserRepository.findByUserName("admin");
        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin ja existe!");
                },
                () -> {
                    var user = new User();
                    user.setUserName("admin");
                    user.setEmail("admin@admin.com");
                    user.setPassword(passwordEncoder.encode("123456"));
                    user.setRoles(Set.of(roleAdmin));
                    iUserRepository.save(user);
                }
        );
    }
}
