package com.example.medapp.repository.RegistrationStorage;

import com.yubico.webauthn.AssertionRequest;
import com.yubico.webauthn.CredentialRepository;
import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialDescriptor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class InMemoryCredentialRepository implements CredentialRepository {

    // Map to store user handles by username
    private final Map<String, ByteArray> userHandleByUsername = new ConcurrentHashMap<>();

    // Map to store usernames by user handle
    private final Map<ByteArray, String> usernameByUserHandle = new ConcurrentHashMap<>();

    // Map to store credentials by user handle
    // private final Map<String, Set<RegisteredCredential>> credentialsByUserName =
    // new ConcurrentHashMap<>();
    // Use userHandle as the key
    Map<ByteArray, Set<RegisteredCredential>> credentialsByUserHandle = new ConcurrentHashMap<>();

    private final Map<ByteArray, AssertionRequest> pendingRequests = new ConcurrentHashMap<>();

    @Override
    public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(String username) {

        Optional<ByteArray> userHandleOpt = getUserHandleForUsername(username);

        if (userHandleOpt.isEmpty()) {
            return Collections.emptySet();
        }
    
        ByteArray userHandle = userHandleOpt.get();

        Set<RegisteredCredential> registeredCredentials = credentialsByUserHandle.get(userHandle);
        if (registeredCredentials == null) {
            return Collections.emptySet();
        }
        Set<PublicKeyCredentialDescriptor> descriptors = new HashSet<>();
        for (RegisteredCredential credential : registeredCredentials) {
            descriptors.add(PublicKeyCredentialDescriptor.builder()
                    .id(credential.getCredentialId())
                    .build());
        }
        return descriptors;
    }

    public Optional<String> getUsernameForCredential(RegisteredCredential target) {
        for (Map.Entry<ByteArray, Set<RegisteredCredential>> entry : credentialsByUserHandle.entrySet()) {
            if (entry.getValue().contains(target)) {
                return Optional.of(getUsernameForUserHandle(entry.getKey()).orElse(null));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<RegisteredCredential> lookup(ByteArray credentialId, ByteArray pUserHandle) {

        Set<RegisteredCredential> registeredCredentials = credentialsByUserHandle.get(pUserHandle);
        if (registeredCredentials != null) {
            for (RegisteredCredential credential : registeredCredentials) {
                if (credential.getCredentialId().equals(credentialId)) {
                    return Optional.of(credential);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Set<RegisteredCredential> lookupAll(ByteArray credentialId) {
        Set<RegisteredCredential> result = new HashSet<>();
        for (Set<RegisteredCredential> registeredCredentials : credentialsByUserHandle.values()) {
            for (RegisteredCredential credential : registeredCredentials) {
                if (credential.getCredentialId().equals(credentialId)) {
                    result.add(credential);
                }
            }
        }
        return result;
    }

    // Method to add a new credential
    public void addCredential(String username, ByteArray userHandle, RegisteredCredential credential) {
        // userHandleByUsername.put(username, userHandle);
        // usernameByUserHandle.put(userHandle, username);
        credentialsByUserHandle.computeIfAbsent(userHandle, k -> new HashSet<>()).add(credential);
    }

    @Override
    public Optional<ByteArray> getUserHandleForUsername(String username) {
        return Optional.ofNullable(userHandleByUsername.get(username));
    }

    @Override
    public Optional<String> getUsernameForUserHandle(ByteArray userHandle) {
        return Optional.ofNullable(usernameByUserHandle.get(userHandle));
    }

    public Map<ByteArray, AssertionRequest> getPendingRequests() {
        return pendingRequests;
    }

    public void GetMemoryDetails() {
        System.out.println("GetMemoryDetails(+)");

        // private final Map<String, ByteArray> userHandleByUsername = new
        // ConcurrentHashMap<>();

        // // Map to store usernames by user handle
        // private final Map<ByteArray, String> usernameByUserHandle = new
        // ConcurrentHashMap<>();

        // // Map to store credentials by user handle
        // private final Map<String, Set<RegisteredCredential>> credentialsByUserName =
        // new ConcurrentHashMap<>();

        // private final Map<String, AssertionRequest> pendingRequests = new
        // ConcurrentHashMap<>();

        userHandleByUsername.entrySet().forEach(entry -> {
            String username = entry.getKey();
            ByteArray userHandle = entry.getValue();
            System.out.println("Username: " + username);
            System.out.println("User Handle: " + userHandle);
        });
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        usernameByUserHandle.entrySet().forEach(entry -> {
            ByteArray userHandle = entry.getKey();
            String username = entry.getValue();
            System.out.println("User Handle: " + userHandle);
            System.out.println("Username: " + username);
        });
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        credentialsByUserHandle.entrySet().forEach(entry -> {
            ByteArray userHandle = entry.getKey();
            Set<RegisteredCredential> registeredCredentials = entry.getValue();
            System.out.println("User Handle: " + userHandle);
            System.out.println("Registered Credentials: " + registeredCredentials);
        });
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        pendingRequests.entrySet().forEach(entry -> {
            ByteArray userHandle = entry.getKey();
            AssertionRequest request = entry.getValue();
            System.out.println("User Handle: " + userHandle);
            System.out.println("Assertion Request: " + request);
        });
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("GetMemoryDetails(-)");
    }

    public void storeUserNamewithUserHandle(String username, ByteArray userHandle) {
        userHandleByUsername.put(username, userHandle);
    }

    public void storeUserHandlewithUserName(String username, ByteArray userHandle) {
        usernameByUserHandle.put(userHandle, username);
    }

}
