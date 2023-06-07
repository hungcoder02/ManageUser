package com.example.usermanage.Repository;

import com.example.usermanage.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
        Role findByName(String s);
}
