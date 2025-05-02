package com.example.medapp.controller.passkeyAuth.FinishRegistration;

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
}
