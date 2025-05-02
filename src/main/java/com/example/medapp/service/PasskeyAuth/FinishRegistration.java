package com.example.medapp.service.PasskeyAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.medapp.controller.passkeyAuth.FinishRegistration.RegistrationStorage;
import com.example.medapp.model.commonResponse.CommonResp;
import com.example.medapp.repository.RegistrationStorage.InMemoryCredentialRepository;
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

    public CommonResp finishRegistrationService(String userId,
            PublicKeyCredential<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs> credential) {

        CommonResp lRespRec = new CommonResp();
        String lStatus = "S";
        String lErrMsg = "";
        String lMsg = "";

        // Retrieve the stored challenge
        ByteArray storedChallenge = challengeStorage.getChallenge(userId);
        if (storedChallenge == null) {
            lStatus = "E";
            lErrMsg = "No challenge found for user.";
            lRespRec.setStatus(lStatus);
            lRespRec.setErrMsg(lErrMsg);
            return lRespRec;
        }

        // Retrieve the original PublicKeyCredentialCreationOptions
        PublicKeyCredentialCreationOptions originalRequest = creationOptionsStorage.getOptions(userId);
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

        // Finish registration
        RegistrationResult result;
        try {
            result = relyingParty.finishRegistration(options);

            // Remove stored challenge
            challengeStorage.removeChallenge(userId);

            // Remove stored registration options
            creationOptionsStorage.removeOptions(userId);

            // Extract the userHandle from the UserIdentity
            ByteArray userHandle = challengeStorage.getUserIdentity(userId).getId();

            // Store the credential information
            RegisteredCredential registeredCredential = RegisteredCredential.builder()
                    .credentialId(result.getKeyId().getId())
                    .userHandle(userHandle)
                    .publicKeyCose(result.getPublicKeyCose())
                    .signatureCount(result.getSignatureCount())
                    .build();

            inMemorycredentialRepository.addCredential(userId, userHandle, registeredCredential);

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

        return lRespRec;

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
