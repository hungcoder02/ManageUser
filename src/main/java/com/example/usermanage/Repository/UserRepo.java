package com.example.usermanage.Repository;

import com.example.usermanage.Model.Role;
import com.example.usermanage.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
        Optional<User> findByName(String name);
}
