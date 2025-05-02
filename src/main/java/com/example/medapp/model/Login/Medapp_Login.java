package com.example.medapp.model.Login;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Component
@Entity
public class Medapp_Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id")
    private Integer loginId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Medapp_Login() {
    }

    public Medapp_Login(Integer login_id, String user_id, String password, String role, String created_by,
            LocalDateTime created_date, String updated_by, LocalDateTime updated_date) {
        this.loginId = login_id;
        this.userId = user_id;
        this.password = password;
        this.role = role;
        this.createdBy = created_by;
        this.createdDate = created_date;
        this.updatedBy = updated_by;
        this.updatedDate = updated_date;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Medapp_Login{" +
                "login_id=" + loginId +
                ", user_id='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", created_by='" + createdBy + '\'' +
                ", created_date='" + createdDate + '\'' +
                ", updated_by='" + updatedBy + '\'' +
                ", updated_date='" + updatedDate + '\'' +
                '}';
    }
}
