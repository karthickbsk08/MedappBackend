package com.example.medapp.controller.passkeyAuth.StartRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.medapp.model.passkeyauth.StartRegistrationRequest;
import com.example.medapp.repository.RegistrationStorage.ChallengeStorage;
import com.example.medapp.repository.RegistrationStorage.InMemoryCredentialRepository;
import com.example.medapp.repository.RegistrationStorage.RegistrationStorage;
import com.example.medapp.service.PasskeyAuth.FinishRegistration;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartRegistrationOptions;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.UserIdentity;

@RestController
public class PasskeyRegistrationAndAuthenticate {

    @Autowired
    private RelyingParty relyingParty;

    @Autowired
    private ChallengeStorage challengeStorage;

    @Autowired
    private FinishRegistration finishRegistration;
    @Autowired
    private RegistrationStorage creationOptionsStorage;

    @Autowired
    private InMemoryCredentialRepository inMemorycredentialRepository;

    @PostMapping("/webauthn/register-options")
    public PublicKeyCredentialCreationOptions startRegistration(@RequestBody StartRegistrationRequest request) {

        // 1. Generate a random UUID as the userId and convert it to ByteArray
        // byte[] byteArray =
        // UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
        // ByteArray userId = new ByteArray(byteArray);

        System.out.println("001 : " + request);

        ByteArray userId = null;
        PublicKeyCredentialCreationOptions options = null;

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
        if (inMemorycredentialRepository.getUserHandleForUsername(request.getUserId()).isPresent()) {
            userId = inMemorycredentialRepository.getUserHandleForUsername(request.getUserId()).get();
            System.out.println("002 : " + userId);
        } else {
            // System.out.println("new");
            // ❗ Generate only if not present
            String base64UrlUserId = finishRegistration.generateBase64UrlUserId();
            byte[] userIdByteArray = finishRegistration.base64UrlToByteArray(base64UrlUserId);
            userId = new ByteArray(userIdByteArray);

            System.out.println("003 : " + userId);

            // System.out.println("new");
            // System.out.println("UserId: " + userId);
            // System.out.println("UserId: " + userId == null);
        }

        // 2. Create the UserIdentity object
        UserIdentity user = UserIdentity.builder()
                .name(request.getUserId())
                .displayName(request.getDisplayName())
                .id(userId)
                .build();

        System.out.println("004 : " + user);

        // ✅ Store this mapping only once
        inMemorycredentialRepository.storeUserNamewithUserHandle(request.getUserId(), userId);
        inMemorycredentialRepository.storeUserHandlewithUserName(request.getUserId(), userId);

        System.out.println("005 : ");
        inMemorycredentialRepository.GetMemoryDetails();

        // 3. Start the registration process
        StartRegistrationOptions registrationOptions = StartRegistrationOptions.builder().user(user).build();

        System.out.println("006 : " + registrationOptions);

        // 4. Store the challenge temporarily (could be Redis, database, or session)
        options = relyingParty.startRegistration(registrationOptions);
        System.out.println("007 : " + options);

        // 5. Return the PublicKeyCredentialCreationOptions to the frontend
        challengeStorage.saveChallenge(request.getUserId(), options.getChallenge());
        challengeStorage.saveUserIdentity(request.getUserId(), user);

        System.out.println("008 : ");
        challengeStorage.getChallengeAndUserIdentidyMap();
        // In startRegistration
        creationOptionsStorage.saveOptions(request.getUserId(), options);

        System.err.println("009 : ");
        creationOptionsStorage.getAllCredentialCreationOption();

        System.err.println("010 : " + options.toString());
        return options;
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
