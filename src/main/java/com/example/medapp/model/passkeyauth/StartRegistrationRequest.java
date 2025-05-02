package com.example.medapp.model.passkeyauth;

import java.io.ByteArrayInputStream;

public class StartRegistrationRequest {
    private ByteArrayInputStream uniqueId; // Unique user ID (16-32 bytes)
    private String userId; // Username (email or custom ID)
    private String displayName;

    public ByteArrayInputStream getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(ByteArrayInputStream uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String username) {
        this.userId = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public StartRegistrationRequest(ByteArrayInputStream uniqueId, String username, String displayName) {
        this.uniqueId = uniqueId;
        this.userId = username;
        this.displayName = displayName;
    }

    public StartRegistrationRequest() {
    }

    @Override
    public String toString() {
        return "StartRegistrationRequest [uniqueId=" + uniqueId + ", username=" + userId + ", displayName="
                + displayName + "]";
    }

}
