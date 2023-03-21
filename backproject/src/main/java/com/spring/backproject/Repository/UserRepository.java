package com.spring.backproject.Repository;

import com.spring.backproject.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<Utilisateur,Long> {
boolean existsByEmail(String email);
Utilisateur findByEmail(String email);
}
