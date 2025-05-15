package com.example.medapp.service.PasskeyAuth;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.medapp.model.commonResponse.CommonResp;
import com.example.medapp.repository.RegistrationStorage.ChallengeStorage;
import com.example.medapp.repository.RegistrationStorage.InMemoryCredentialRepository;
import com.example.medapp.repository.RegistrationStorage.RegistrationStorage;
import com.yubico.webauthn.FinishRegistrationOptions;
import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.RegistrationResult;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.ClientRegistrationExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredential;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;

@Service
@Component
public class FinishRegistration {

    @Autowired
    private ChallengeStorage challengeStorage;

    @Autowired
    private RegistrationStorage creationOptionsStorage;

    @Autowired
    private RelyingParty relyingParty;

    @Autowired
    private InMemoryCredentialRepository inMemorycredentialRepository;

    public CommonResp finishRegistrationService(String userName,
            PublicKeyCredential<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs> credential) {

        CommonResp lRespRec = new CommonResp();
        String lStatus = "S";
        String lErrMsg = "";
        String lMsg = "";

        // Retrieve the stored challenge
        ByteArray storedChallenge = challengeStorage.getChallenge(userName);
        System.err.println("013 : " + storedChallenge);
        if (storedChallenge == null) {
            lStatus = "E";
            lErrMsg = "No challenge found for user.";
            lRespRec.setStatus(lStatus);
            lRespRec.setErrMsg(lErrMsg);
            return lRespRec;
        }

        // Retrieve the original PublicKeyCredentialCreationOptions
        PublicKeyCredentialCreationOptions originalRequest = creationOptionsStorage.getOptions(userName);
        System.err.println("014 : " + originalRequest);
        if (originalRequest == null) {
            lStatus = "E";
            lErrMsg = "No registration options found for user.";
            lRespRec.setStatus(lStatus);
            lRespRec.setErrMsg(lErrMsg);
            return lRespRec;
        }

        // Build FinishRegistrationOptions
        FinishRegistrationOptions options = FinishRegistrationOptions.builder()
                .request(originalRequest)
                .response(credential)
                .build();

        System.err.println("015 : " + options);

        // Finish registration
        RegistrationResult result;
        ByteArray userHandle = null;
        try {
            result = relyingParty.finishRegistration(options);
            System.err.println("016 : " + result);

            // Remove stored challenge
            challengeStorage.removeChallenge(userName);

            // Remove stored registration options
            creationOptionsStorage.removeOptions(userName);

            // challengeStorage.removeUserIdentity(userName);

            System.err.println("017 : " + result);
            challengeStorage.getChallengeAndUserIdentidyMap();
            System.err.println("018 : ");
            creationOptionsStorage.getAllCredentialCreationOption();

            // Extract the userHandle from the UserIdentity
            ByteArray DummyuserHandle = challengeStorage.getUserIdentity(userName).getId();
            System.err.println("019 : " + DummyuserHandle);

            if (inMemorycredentialRepository.getUserHandleForUsername(userName).isPresent()) {
                userHandle = inMemorycredentialRepository.getUserHandleForUsername(userName).get();
                System.err.println("020 : " + userHandle);
            }

            // Store the credential information
            RegisteredCredential registeredCredential = RegisteredCredential.builder()
                    .credentialId(result.getKeyId().getId())
                    .userHandle(userHandle)
                    .publicKeyCose(result.getPublicKeyCose())
                    .signatureCount(result.getSignatureCount())
                    .build();

            System.err.println("021 : " + registeredCredential);

            inMemorycredentialRepository.addCredential(userName, userHandle, registeredCredential);
            System.err.println("022 : ");
            inMemorycredentialRepository.GetMemoryDetails();

        } catch (Exception e) {
            e.printStackTrace();
            lStatus = "E";
            lErrMsg = e.toString();
            lRespRec.setStatus(lStatus);
            lRespRec.setErrMsg(lErrMsg);
            return lRespRec;
        }

        lMsg = "Passkey Registration Successful";
        lRespRec.setMsg(lMsg);
        lRespRec.setStatus(lStatus);
        lRespRec.setErrMsg(lErrMsg);
        System.err.println("023 : " + lRespRec);
        return lRespRec;

    }

    public String generateBase64UrlUserId() {
        SecureRandom random = new SecureRandom();
        byte[] userIdBytes = new byte[32]; // 32 bytes = 256-bit ID
        random.nextBytes(userIdBytes);

        return Base64.getUrlEncoder()
                .withoutPadding() // WebAuthn requires base64url *without* padding
                .encodeToString(userIdBytes);
    }

    public byte[] base64UrlToByteArray(String base64UrlString) {
        // Ensure the string is properly padded to be a valid Base64 string
        String paddedBase64 = base64UrlString + "=".repeat((4 - base64UrlString.length() % 4) % 4);
        // Convert Base64Url to Base64
        String base64 = paddedBase64.replace('-', '+').replace('_', '/');
        // Decode the Base64 string
        return Base64.getDecoder().decode(base64);
    }

    public static String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public static byte[] decode(String base64url) {
        return Base64.getUrlDecoder().decode(base64url);
    }

}

/*
 * public void finishRegistration(String userId,
 * PublicKeyCredential<AuthenticatorAttestationResponse,
 * ClientRegistrationExtensionOutputs> credential) {
 * // Retrieve the stored challenge
 * ByteArray storedChallenge = challengeStorage.getChallenge(userId);
 * if (storedChallenge == null) {
 * throw new IllegalStateException("No challenge found for user.");
 * }
 * 
 * // Retrieve the original PublicKeyCredentialCreationOptions
 * PublicKeyCredentialCreationOptions originalRequest =
 * creationOptionsStorage.get(userId);
 * if (originalRequest == null) {
 * throw new IllegalStateException("No registration options found for user.");
 * }
 * 
 * // Build FinishRegistrationOptions
 * FinishRegistrationOptions options = FinishRegistrationOptions.builder()
 * .request(originalRequest)
 * .response(credential)
 * .build();
 * 
 * // Finish registration
 * RegistrationResult result = relyingParty.finishRegistration(options);
 * 
 * // Remove stored challenge and registration options
 * challengeStorage.removeChallenge(userId);
 * creationOptionsStorage.remove(userId);
 * 
 * // Store the credential information
 * RegisteredCredential registeredCredential = RegisteredCredential.builder()
 * .credentialId(result.getKeyId().getId())
 * .userHandle(result.getKeyId().getUserHandle())
 * .publicKeyCose(result.getPublicKeyCose())
 * .signatureCount(result.getSignatureCount())
 * .build();
 * 
 * credentialRepository.addCredential(userId, result.getKeyId().getUserHandle(),
 * registeredCredential);
 * }
 */
