package com.example.medapp.controller.passkeyAuth.StartRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.medapp.model.passkeyauth.StartRegistrationRequest;
import com.example.medapp.service.PasskeyAuth.ChallengeStorage;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartRegistrationOptions;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.UserIdentity;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
public class PasskeyRegistrationAndAuthenticate {

    @Autowired
    private RelyingParty relyingParty;

    @Autowired
    private ChallengeStorage challengeStorage;

    @PostMapping("/webauthn/register-options")
    public PublicKeyCredentialCreationOptions startRegistration(@RequestBody StartRegistrationRequest request) {

        // 1. Generate a random UUID as the userId and convert it to ByteArray
        byte[] byteArray = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
        ByteArray userId = new ByteArray(byteArray);

        // 2. Create the UserIdentity object
        UserIdentity user = UserIdentity.builder()
                .name(request.getUserId())
                .displayName(request.getDisplayName())
                .id(userId)
                .build();

        // 3. Start the registration process
        StartRegistrationOptions registrationOptions = StartRegistrationOptions.builder().user(user).build();

        // 4. Store the challenge temporarily (could be Redis, database, or session)
        PublicKeyCredentialCreationOptions options = relyingParty.startRegistration(registrationOptions);

        // 5. Return the PublicKeyCredentialCreationOptions to the frontend
        challengeStorage.saveChallenge(request.getUserId(), options.getChallenge());

        return options;
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