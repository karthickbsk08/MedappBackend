package com.example.medapp.repository.RegistrationStorage;

import com.yubico.webauthn.AssertionRequest;
import com.yubico.webauthn.CredentialRepository;
import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import com.yubico.webauthn.data.PublicKeyCredentialDescriptor;
import com.yubico.webauthn.data.PublicKeyCredentialRequestOptions;
import com.yubico.webauthn.data.UserIdentity;

import java.security.KeyStore.Entry;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class InMemoryCredentialRepository implements CredentialRepository {

    // Map to store user handles by username
    // private final Map<String, ByteArray> userHandleByUsername = new
    // ConcurrentHashMap<>();

    // Map to store usernames by user handle

    // Map to store credentials by user handle
    // private final Map<String, Set<RegisteredCredential>> credentialsByUserName =
    // new ConcurrentHashMap<>();
    // Use userHandle as the key
    private final Map<String, ByteArray> userHandleByUsername = new ConcurrentHashMap<>();
    private final Map<ByteArray, String> usernameByUserHandle = new ConcurrentHashMap<>();
    private final Map<ByteArray, Set<RegisteredCredential>> credentialsByUserHandle = new ConcurrentHashMap<>();
    private final Map<ByteArray, AssertionRequest> pendingRequests = new ConcurrentHashMap<>();
    private final Map<String, ByteArray> challengeMap = new ConcurrentHashMap<>();
    private final Map<String, UserIdentity> userIdentityMap = new ConcurrentHashMap<>();
    private final Map<String, PublicKeyCredentialCreationOptions> optionsMap = new ConcurrentHashMap<>();

    //    
    private final Map<String, ByteArray> credentialIdByUsername = new HashMap<>();
    

  
    // Save username ↔ credentialId
    public void storeCredentialId(String username, ByteArray credentialId) {
        credentialIdByUsername.put(username, credentialId);
    }

    public Optional<ByteArray> getCredentialIdForUsername(String username) {
        return Optional.ofNullable(credentialIdByUsername.get(username));
    }

    @Override
    public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(String username) {

        System.out.println("username xx1: " + username);
        // Check if the username exists in the map
        Optional<ByteArray> userHandleOpt = getUserHandleForUsername(username);

        if (userHandleOpt.isEmpty()) {
            System.out.println("username xx2:" + userHandleOpt);
            return Collections.emptySet();
        }
        System.out.println("username xx3:" + userHandleOpt);

        ByteArray userHandle = userHandleOpt.get();

        System.out.println("username xx4:" + userHandle);

        Set<RegisteredCredential> registeredCredentials = credentialsByUserHandle.get(userHandle);
        if (registeredCredentials == null) {
            System.out.println("username xx5:" + registeredCredentials);
            return Collections.emptySet();
        }

        System.out.println("username xx6:" + registeredCredentials);

        Set<PublicKeyCredentialDescriptor> descriptors = new HashSet<>();
        for (RegisteredCredential credential : registeredCredentials) {
            System.out.println("username xx7:" + credential);
            descriptors.add(PublicKeyCredentialDescriptor.builder()
                    .id(credential.getCredentialId())
                    .build());
        }
        System.out.println("descriptors :" + descriptors);

        return descriptors;
    }

    /*
     * @Override
     * public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(String
     * username) {
     * return credentialStore.values().stream()
     * .filter(cred ->
     * cred.getUserHandle().equals(ByteArray.fromBase64Url(username)))
     * .map(cred -> PublicKeyCredentialDescriptor.builder()
     * .id(cred.getCredentialId())
     * .type(PublicKeyCredentialType.PUBLIC_KEY)
     * .build())
     * .collect(Collectors.toSet());
     */

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

        System.out.println("LOOKUP called with credentialId: " + credentialId + ", pUserHandle: " + pUserHandle);

        for (java.util.Map.Entry<ByteArray, Set<RegisteredCredential>> iterable_element : credentialsByUserHandle
                .entrySet()) {
            System.out.println("LOOKUP iterable_element: " + iterable_element);

        }

        Set<RegisteredCredential> registeredCredentials = credentialsByUserHandle.get(credentialId);
        if (registeredCredentials != null) {
            for (RegisteredCredential credential : registeredCredentials) {
                if (credential.getCredentialId().equals(credentialId)) {
                    System.out.println(",credential.getCredentialId().equals(credentialId): "
                            + credential.getCredentialId().equals(credentialId));
                    return Optional.of(credential);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Set<RegisteredCredential> lookupAll(ByteArray credentialId) {
        System.out.println("lookupAll called with credentialId: " + credentialId);

        Set<RegisteredCredential> result = new HashSet<>();
        for (Set<RegisteredCredential> registeredCredentials : credentialsByUserHandle.values()) {
            for (RegisteredCredential credential : registeredCredentials) {
                System.out.println("Checking credential: " + credential.getCredentialId());
                if (credential.getCredentialId().equals(credentialId)) {
                    result.add(credential);
                }
            }
        }
        System.out.println("result : " + result);
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

    public void getChallengeAndUserIdentidyMap() {

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

    public AssertionRequest ModifyAssertionRequest(ByteArray pKey, ByteArray newUserHandle) {
        ByteArray key = pKey;// the key (e.g., userHandle) you're using
        AssertionRequest oldRequest = pendingRequests.get(key);

        System.out.println("MAS001 key: " + key);
        System.out.println("MAS002 oldRequest: " + oldRequest);

        // Modify the options
        PublicKeyCredentialRequestOptions oldOptions = oldRequest.getPublicKeyCredentialRequestOptions();
        System.out.println("MAS003 oldOptions: " + oldOptions);
        System.out.println("MAS004 getChallenge: " + oldOptions.getChallenge());
        System.out.println("MAS005 getRpId: " + oldOptions.getRpId());
        // System.out.println("MAS006 getTimeout: " +
        // oldOptions.getTimeout().orElse(null));
        System.out.println("MAS007 getAllowCredentials: " + oldOptions.getAllowCredentials().orElse(null));
        // System.out.println("MAS008 getUserVerification: " +
        // oldOptions.getUserVerification().orElse(null));
        System.out.println("MAS009 getExtensions: " + oldOptions.getExtensions());
        PublicKeyCredentialRequestOptions newOptions = PublicKeyCredentialRequestOptions.builder()
                .challenge(oldOptions.getChallenge())
                .rpId(oldOptions.getRpId())
                .timeout(oldOptions.getTimeout())
                .allowCredentials(oldOptions.getAllowCredentials().orElse(null))
                .userVerification(oldOptions.getUserVerification().orElse(null))
                .extensions(oldOptions.getExtensions())
                .build();

        System.out.println("MAS010 newOptions: " + newOptions);

        // Create a new AssertionRequest with modified userHandle or other fields
        AssertionRequest newRequest = AssertionRequest.builder()
                .publicKeyCredentialRequestOptions(newOptions)
                .username(oldRequest.getUsername())
                // .userHandle(Optional.of(newUserHandle)) // or whatever change you need
                .build();

        // Update your map
        pendingRequests.put(key, newRequest);
        return newRequest;
    }

    public boolean removeCredential(ByteArray userHandle, ByteArray credentialIdToRemove) {
        System.out.println(
                "removeCredential  userHandle:" + userHandle + " credentialIdToRemove: " + credentialIdToRemove);
        Set<RegisteredCredential> credentials = credentialsByUserHandle.get(userHandle);
        if (credentials != null) {
            boolean removed = credentials.removeIf(cred -> cred.getCredentialId().equals(credentialIdToRemove));
            System.out.println("removed :" + removed);
            // If the set becomes empty after removal, you can also remove the key from the
            // map (optional):
            if (credentials.isEmpty()) {
                credentialsByUserHandle.remove(userHandle);
            }
            return removed; // true if something was removed
        }
        return false;
    }

    public RegisteredCredential getCredentialIdByUserHandle(ByteArray userHandle, ByteArray credentailId) {
        Set<RegisteredCredential> credentials = credentialsByUserHandle.get(userHandle);

        if (credentials != null) {
            for (RegisteredCredential cred : credentials) {
                if (cred.getCredentialId().equals(credentailId)) {
                    System.out.println("Found credential for user handle: " + userHandle);
                    System.out.println("Credential ID: " + cred.getCredentialId());
                    return cred; // Return the found credential
                }

            }

            // redentials found and printed
        }

        System.out.println("No credentials found for user handle: " + userHandle);
        return null; // No credentials found for the given user handle
    }

    public void getCred() {

        for (java.util.Map.Entry<ByteArray, Set<RegisteredCredential>> iterable_element : credentialsByUserHandle
                .entrySet()) {
            System.out.println("getCred : " + iterable_element.getKey());
            System.out.println("getCred : " + iterable_element.getValue());

        }
    }

    public ByteArray getUserHandleByCredId(ByteArray CredID) {

        Set<RegisteredCredential> regCred = credentialsByUserHandle.get(CredID);

        for (RegisteredCredential registeredCredential : regCred) {
            return registeredCredential.getUserHandle();
        }
        return null;
    }

}
