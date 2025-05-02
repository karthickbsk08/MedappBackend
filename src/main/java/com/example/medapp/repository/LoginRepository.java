package com.example.medapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.example.medapp.model.Login.Medapp_Login;

@Repository
@Component
public interface LoginRepository extends JpaRepository<Medapp_Login, Integer> {
    
    Medapp_Login findByUserIdAndPassword(String userId, String password);

    boolean existsByUserIdAndPasswordAndRole(String userId, String password, String role);

}
