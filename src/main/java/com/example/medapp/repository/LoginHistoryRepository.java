package com.example.medapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.medapp.model.Login.Medapp_login_history;

@Repository
@Component
public interface LoginHistoryRepository extends JpaRepository<Medapp_login_history,Integer>{

}
