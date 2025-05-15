package com.example.medapp.repository.RegistrationStorage;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import java.util.Map;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;

@Component
public class RegistrationStorage {
    private final Map<String, PublicKeyCredentialCreationOptions> optionsMap = new ConcurrentHashMap<>();

    public void saveOptions(String userId, PublicKeyCredentialCreationOptions options) {
        optionsMap.put(userId, options);
    }

    public PublicKeyCredentialCreationOptions getOptions(String userId) {
        return optionsMap.get(userId);
    }

    public void removeOptions(String userId) {
        optionsMap.remove(userId);
    }

    public void getAllCredentialCreationOption() {

        optionsMap.entrySet().forEach(entry -> {
            String userId = entry.getKey();
            PublicKeyCredentialCreationOptions options = entry.getValue();
            System.out.println("User ID: " + userId);
            System.out.println("Credential Creation Options: " + options);
        });
    }
}
