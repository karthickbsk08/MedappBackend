package com.example.medapp.model.Login;

public class UserDetails {

    private Integer loginId;
    private String role;
    private String userId;
    private Integer loginHistoryId;

    
   
    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLoginHistoryId() {
        return loginHistoryId;
    }

    public void setLoginHistoryId(Integer loginHistoryId) {
        this.loginHistoryId = loginHistoryId;
    }

    public UserDetails(Integer loginId, String role, String userId, Integer loginHistoryId) {
        this.loginId = loginId;
        this.role = role;
        this.userId = userId;
        this.loginHistoryId = loginHistoryId;
    }

    public UserDetails() {
    }
 
    @Override
    public String toString() {
        return "UserDetails [loginId=" + loginId + ", role=" + role + ", userId=" + userId + ", loginHistoryId="
                + loginHistoryId + "]";
    }

    

    

}
