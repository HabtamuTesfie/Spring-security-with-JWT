package com.test.productmanagement;

import com.test.productmanagement.model.Role;
import com.test.productmanagement.model.User;
import com.test.productmanagement.model.enums.ERole;
import com.test.productmanagement.repository.RoleRepository;
import com.test.productmanagement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@SpringBootApplication
public class ProductManagementApplication implements ApplicationRunner {

    @Inject
    private RoleRepository roleRepository;

    @Inject
    private UserRepository userRepository;

    @Value("${super_admin.name}")
    private String superAdmin_name;

    @Value("${super_admin.password}")
    private String superAdmin_password;

    public static void main(String[] args) {
        SpringApplication.run(ProductManagementApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        registerRoles();
        registerSuperAdminUser();
    }

    private void registerRoles() {
        for (ERole role : ERole.values()) {
            Optional<Role> optionalRole = roleRepository.findByName(role);
            if (optionalRole.isEmpty()) {
                Role newRole = new Role();
                newRole.setName(role);
                roleRepository.save(newRole);
            }
        }
    }


    private void registerSuperAdminUser() {
        Optional<Role> optionalAdminRole = roleRepository.findByName(ERole.ROLE_ADMIN);

        Role adminRole = optionalAdminRole.orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName(ERole.ROLE_ADMIN);

            return roleRepository.save(newRole);
        });

        Optional<User> userData = userRepository.findByUsername(superAdmin_name);

        if (userData.isEmpty()) {
            User adminUser = User.builder()
                .username(superAdmin_name)
                .fullName("super admin")
                .createdDate(LocalDateTime.now())
                .email("admin@test.com")
                .password(superAdmin_password)
                .roles(Collections.singleton(adminRole))
                .build();

            userRepository.save(adminUser);
        }
    }

}
