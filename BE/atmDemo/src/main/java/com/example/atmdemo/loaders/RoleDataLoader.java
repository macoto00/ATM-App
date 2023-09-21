package com.example.atmdemo.loaders;

import com.example.atmdemo.models.Role;
import com.example.atmdemo.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RoleDataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public RoleDataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findAll().isEmpty()) {
            loadInitialColorPersonalities();
        }
    }

    private void loadInitialColorPersonalities() {

        Role user = createRole("USER");
        Role admin = createRole("ADMIN");

        Role[] roles = {user, admin};
        roleRepository.saveAll(Arrays.asList(roles));
    }

    private Role createRole (String name) {
        Role role = new Role();
        role.setName(name);
        return role;
    }
}
