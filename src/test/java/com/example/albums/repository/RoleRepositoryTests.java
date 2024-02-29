package com.example.albums.repository;

import com.example.albums.entity.Role;
import com.example.albums.enumeration.ERole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void cleanup() {
        roleRepository.deleteAll();
    }

    @Test
    void testSaveRole() {
        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
            Role userRole = Role.builder()
                    .name(ERole.ROLE_USER)
                    .build();
            roleRepository.save(userRole);
        }

        Optional<Role> optionalRole = roleRepository.findByName(ERole.ROLE_USER);
        assertThat(optionalRole).isPresent();
    }

    @Test
    void testSaveAllRoles() {
        List<Role> rolesToSave = List.of(
                Role.builder().name(ERole.ROLE_USER).build(),
                Role.builder().name(ERole.ROLE_ADMIN).build()
        );

        for (Role role : rolesToSave) {
            if (roleRepository.findByName(role.getName()).isEmpty()) {
                roleRepository.save(role);
            }
        }

        List<Role> savedRoles = roleRepository.findAll();

        assertThat(savedRoles).isNotNull();
        assertThat(savedRoles.size()).isEqualTo(2);
    }

    @Test
    void testFindByName_Success() {
        roleRepository.deleteAll();

        Role adminRole = Role.builder()
                .name(ERole.ROLE_ADMIN)
                .build();
        roleRepository.save(adminRole);

        Optional<Role> retrievedAdminRole = roleRepository.findByName(ERole.ROLE_ADMIN);

        assertThat(retrievedAdminRole).isPresent();
    }

}
