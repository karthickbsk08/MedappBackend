package com.example.medapp.repository.RegistrationStorage;

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
    private final Map<ByteArray, Set<RegisteredCredential>> credentialsByUserHandle = new ConcurrentHashMap<>();

    @Override
    public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(String username) {
        ByteArray userHandle = userHandleByUsername.get(username);
        if (userHandle == null) {
            return Collections.emptySet();
        }
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

    @Override
    public Optional<ByteArray> getUserHandleForUsername(String username) {
        return Optional.ofNullable(userHandleByUsername.get(username));
    }

    @Override
    public Optional<String> getUsernameForUserHandle(ByteArray userHandle) {
        return Optional.ofNullable(usernameByUserHandle.get(userHandle));
    }

    @Override
    public Optional<RegisteredCredential> lookup(ByteArray credentialId, ByteArray userHandle) {
        Set<RegisteredCredential> registeredCredentials = credentialsByUserHandle.get(userHandle);
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
        userHandleByUsername.put(username, userHandle);
        usernameByUserHandle.put(userHandle, username);
        credentialsByUserHandle.computeIfAbsent(userHandle, k -> new HashSet<>()).add(credential);
    }
}
