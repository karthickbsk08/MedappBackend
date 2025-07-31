package com.example.medapp.controller.passkeyAuth.StartRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import com.yubico.webauthn.data.ResidentKeyRequirement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.medapp.model.passkeyauth.StartRegistrationRequest;
import com.example.medapp.repository.RegistrationStorage.InMemoryCredentialRepository;
import com.example.medapp.service.PasskeyAuth.FinishRegistration;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartRegistrationOptions;
import com.yubico.webauthn.data.AuthenticatorAttachment;
import com.yubico.webauthn.data.AuthenticatorSelectionCriteria;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.UserIdentity;
import com.yubico.webauthn.data.UserVerificationRequirement;

@RestController
public class PasskeyRegistrationAndAuthenticate {

    @Autowired
    private RelyingParty relyingParty;

    // @Autowired
    // private ChallengeStorage challengeStorage;

    @Autowired
    private FinishRegistration finishRegistration;
    // @Autowired
    // private RegistrationStorage creationOptionsStorage;

    @Autowired
    private InMemoryCredentialRepository inMemorycredentialRepository;

    @PostMapping("/webauthn/register-options")
    public PublicKeyCredentialCreationOptions startRegistration(@RequestBody StartRegistrationRequest request) {

        System.out.println("001 : " + request);
        // Declare the declared variables
        ByteArray userId = null;
        PublicKeyCredentialCreationOptions options = null;

        // Step 1: Check if this user already has a userHandle
        if (inMemorycredentialRepository.getUserHandleForUsername(request.getUserId()).isPresent()) {
            userId = inMemorycredentialRepository.getUserHandleForUsername(request.getUserId()).get();
            System.out.println("002 : " + userId);
        } else {
            // Step 2 :Generate a random UUID as the userId and convert it to ByteArray
            String base64UrlUserId = finishRegistration.generateBase64UrlUserId();
            byte[] userIdByteArray = finishRegistration.base64UrlToByteArray(base64UrlUserId);
            userId = new ByteArray(userIdByteArray);
            System.out.println("002.1 : " + userId);
        }

        System.out.println("003 : " + userId);

        // Step 3 : Create the UserIdentity object
        UserIdentity user = UserIdentity.builder()
                .name(request.getUserId())
                .displayName(request.getDisplayName())
                .id(userId)
                .build();

        System.out.println("004 : " + user);

        // Step 4 : Store username and userHandle mapping
        inMemorycredentialRepository.storeUserNamewithUserHandle(request.getUserId(), userId);
        inMemorycredentialRepository.storeUserHandlewithUserName(request.getUserId(), userId);

        AuthenticatorSelectionCriteria authenticatorSelection = AuthenticatorSelectionCriteria.builder()
                .residentKey(ResidentKeyRequirement.REQUIRED) // Ensures a discoverable credential
                .userVerification(UserVerificationRequirement.REQUIRED) // Ensures biometric or similar auth
                .build();

        // Step 6. Start the registration process
        StartRegistrationOptions registrationOptions = StartRegistrationOptions.builder()
                .user(user)
                .authenticatorSelection(authenticatorSelection)
                .build();
        System.out.println("006 : " + registrationOptions);

        // Step 7. Store the challenge temporarily (could be Redis, database, or
        // session)
        options = relyingParty.startRegistration(registrationOptions);
        System.out.println("007 : " + options);

        // Step 8 : Store the challenge and user identity by username
        inMemorycredentialRepository.saveChallenge(request.getUserId(), options.getChallenge());
        inMemorycredentialRepository.saveUserIdentity(request.getUserId(), user);

        // Step 9 : Store the PublicKeyCredentialCreationOptions by username
        inMemorycredentialRepository.saveOptions(request.getUserId(), options);

        return options;

        // 1. Generate a random UUID as the userId and convert it to ByteArray
        // byte[] byteArray =
        // UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
        // ByteArray userId = new ByteArray(byteArray);

        /*
         * String base64UrlUserId = finishRegistration.generateBase64UrlUserId();
         * 
         * byte[] userIdByteArray =
         * finishRegistration.base64UrlToByteArray(base64UrlUserId);
         * 
         * ByteArray userId = new ByteArray(userIdByteArray);
         * 
         * // 2. Create the UserIdentity object
         * UserIdentity user = UserIdentity.builder()
         * .name(request.getUserId())
         * .displayName(request.getDisplayName())
         * .id(userId)
         * .build();
         */

        // System.out.println("Username: " + request.getUserId());

        // ✅ Check if this user already has a userHandle

        // System.out.println("new");
        // ❗ Generate only if not present
        // String base64UrlUserId = finishRegistration.generateBase64UrlUserId();

        // Convert to ByteArray
        // ByteArray newUserId = new
        // ByteArray(base64UrlUserId.getBytes(StandardCharsets.UTF_8));
        // System.out.println("New User ID (hex): " + newUserId.getHex());

        // byte[] userIdByteArray =
        // finishRegistration.base64UrlToByteArray(base64UrlUserId);
        // userId = new ByteArray(userIdByteArray);

        // userId = newUserId;
        // System.out.println("003 : " + userId);

        // System.out.println("new");
        // System.out.println("UserId: " + userId);
        // System.out.println("UserId: " + userId == null);

        // 2. Create the UserIdentity object
        // UserIdentity user = UserIdentity.builder()
        // .name(request.getUserId())
        // .displayName(request.getDisplayName())
        // .id(userId)
        // .build();

        // // ✅ Store this mapping only once
        // inMemorycredentialRepository.storeUserNamewithUserHandle(request.getUserId(),
        // userId);
        // inMemorycredentialRepository.storeUserHandlewithUserName(request.getUserId(),
        // // userId);

        // System.out.println("005 : ");
        // inMemorycredentialRepository.GetMemoryDetails();

        // 3. Start the registration process
        // StartRegistrationOptions registrationOptions =
        // StartRegistrationOptions.builder().user(user).build();

        // System.out.println("006 : " + registrationOptions);

        // 4. Store the challenge temporarily (could be Redis, database, or session)
        // options = relyingParty.startRegistration(registrationOptions);
        // System.out.println("007 : " + options);

        // // 5. Return the PublicKeyCredentialCreationOptions to the frontend
        // inMemorycredentialRepository.saveChallenge(request.getUserId(),
        // options.getChallenge());
        // inMemorycredentialRepository.saveUserIdentity(request.getUserId(), user);

        // System.out.println("008 : ");
        // inMemorycredentialRepository.getChallengeAndUserIdentidyMap();
        // In startRegistration

        // System.out.println("009 : ");
        // inMemorycredentialRepository.getAllCredentialCreationOption();

        // System.out.println("010 : " + options.toString());

    }

    public ByteArray CreateUserHandle() {
        // Generate a random UUID as the userId and convert it to ByteArray
        String base64UrlUserId = finishRegistration.generateBase64UrlUserId();
        byte[] userIdByteArray = finishRegistration.base64UrlToByteArray(base64UrlUserId);
        ByteArray userHandle = new ByteArray(userIdByteArray);
        return userHandle;
    }

    /*
     * public void finishRegistration(String userId,
     * PublicKeyCredential<AuthenticatorAttestationResponse,
     * ClientRegistrationExtensionOutputs> credential,
     * PublicKeyCredentialCreationOptions originalRequest) {
     * 
     * ByteArray storedChallenge = challengeStorage.getChallenge(userId);
     * 
     * if (storedChallenge == null) {
     * throw new IllegalStateException("No challenge found for user.");
     * }
     * 
     * FinishRegistrationOptions options = FinishRegistrationOptions.builder()
     * .request(originalRequest) // You need to reconstruct or retrieve the original
     * //
     * // PublicKeyCredentialCreationOptions
     * .response(credential)
     * .build();
     * 
     * try {
     * RegistrationResult result = relyingParty.finishRegistration(options);
     * if (result == null) {
     * 
     * }
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * 
     * // After successful registration, remove the stored challenge
     * challengeStorage.removeChallenge(userId);
     * 
     * // ... handle the result, such as storing the credential information
     * }
     */

}
