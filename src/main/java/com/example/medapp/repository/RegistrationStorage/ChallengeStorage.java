package com.example.medapp.repository.RegistrationStorage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.UserIdentity;

@Component
public class ChallengeStorage {
    private final Map<String, ByteArray> challengeMap = new ConcurrentHashMap<>();
    private final Map<String, UserIdentity> userIdentityMap = new ConcurrentHashMap<>();

    public void saveChallenge(String userId, ByteArray challenge) {
        challengeMap.put(userId, challenge);
    }

    public ByteArray getChallenge(String userId) {
        return challengeMap.get(userId);
    }

    public void removeChallenge(String userId) {
        challengeMap.remove(userId);
    }

    public void saveUserIdentity(String userId, UserIdentity userIden) {
        userIdentityMap.put(userId, userIden);
    }

    public UserIdentity getUserIdentity(String userId) {
        return userIdentityMap.get(userId);
    }

    public void removeUserIdentity(String userId) {
        userIdentityMap.remove(userId);
    }

    public void getChallengeAndUserIdentidyMap(){

        challengeMap.entrySet().forEach(entry -> {
            String userId = entry.getKey();
            ByteArray challenge = entry.getValue();  
            System.out.println("User ID: " + userId);
            System.out.println("Challenge: " + challenge);
        });

        userIdentityMap.entrySet().forEach(entry -> {
            String userId = entry.getKey();
            UserIdentity userIden = entry.getValue();  
            System.out.println("User ID: " + userId);
            System.out.println("User Identity: " + userIden);
        });

    }

}
