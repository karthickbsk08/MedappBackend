package com.example.medapp.model.Login;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Component
@Entity
public class Medapp_login_history {


  /*   CREATE TABLE medapp_login_history (
    login_history_id SERIAL PRIMARY KEY,
    login_id INT NOT NULL REFERENCES medapp_login(login_id),
    login_date DATE NOT NULL,
    login_time TIME NOT NULL,
    logout_date DATE,
    logout_time TIME,
    created_by VARCHAR(30),
    created_date TIMESTAMP DEFAULT NOW(),
    updated_by VARCHAR(30),
    updated_date TIMESTAMP DEFAULT NOW()
); */
    @Id
    @Column(name = "login_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loginHistoryId;
    
    @Column(name = "login_id")
    private Integer loginId;
    @Column(name = "login_time")
    private LocalTime loginTime;
    @Column(name = "login_date")
    private LocalDate loginDate;
    @Column(name = "logout_time")
    private LocalTime logoutTime;
    @Column(name = "logout_date")
    private LocalDate logoutDate;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Integer getLoginHistoryId() {
        return loginHistoryId;
    }

    public void setLoginHistoryId(Integer loginHistoryId) {
        this.loginHistoryId = loginHistoryId;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer userId) {
        this.loginId = userId;
    }

    public LocalTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalTime loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDate getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(LocalDate loginDate) {
        this.loginDate = loginDate;
    }

    public LocalTime getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(LocalTime logoutTime) {
        this.logoutTime = logoutTime;
    }

    public LocalDate getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(LocalDate logoutDate) {
        this.logoutDate = logoutDate;
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

    public Medapp_login_history(Integer login_history_id, Integer user_id, LocalTime login_time, LocalDate login_date,
            LocalTime logout_time, LocalDate logout_date, String created_by, LocalDateTime created_date,
            String updated_by,
            LocalDateTime updated_date) {
        this.loginHistoryId = login_history_id;
        this.loginId = user_id;
        this.loginTime = login_time;
        this.loginDate = login_date;
        this.logoutTime = logout_time;
        this.logoutDate = logout_date;
        this.createdBy = created_by;
        this.createdDate = created_date;
        this.updatedBy = updated_by;
        this.updatedDate = updated_date;
    }

    public Medapp_login_history() {

    }

    @Override
    public String toString() {
        return "Login_History [login_history_id=" + loginHistoryId + ", user_id=" + loginId + ", login_time="
                + loginTime + ", login_date=" + loginDate + ", logout_time=" + logoutTime + ", logout_date="
                + logoutDate + ", created_by=" + createdBy + ", created_date=" + createdDate + ", updated_by="
                + updatedBy + ", updated_date=" + updatedDate + "]";
    }

}
