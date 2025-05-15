package com.example.medapp.controller.passkeyAuth.RegisterAuthentication;

import org.springframework.web.bind.annotation.RestController;

import com.example.medapp.model.commonResponse.CommonResp;
import com.example.medapp.repository.RegistrationStorage.InMemoryCredentialRepository;
import com.yubico.webauthn.AssertionRequest;
import com.yubico.webauthn.AssertionResult;
import com.yubico.webauthn.FinishAssertionOptions;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartAssertionOptions;
import com.yubico.webauthn.data.AuthenticatorAssertionResponse;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.ClientAssertionExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredential;
import com.yubico.webauthn.data.PublicKeyCredentialDescriptor;
import com.yubico.webauthn.data.PublicKeyCredentialRequestOptions;
import com.yubico.webauthn.data.UserVerificationRequirement;
import com.yubico.webauthn.data.exception.HexException;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class RegisterAuthentication {

    @Autowired
    private RelyingParty relyingParty;

    @Autowired
    private InMemoryCredentialRepository inMemorycredentialRepository;

    // @Autowired
    // private JSONConverter jsonConverter;

    @PostMapping("/webauthn/authenticate-options")
    public Map<String, Object> AuthenticateOptions(@RequestBody Map<String, String> body) {

        System.err.println("024 : " + body);
        // 1. Read the Username from the request body
        String username = body.get("username");
        System.err.println("025 : " + username);

        /*
         * Optional<ByteArray> userHandle = null;
         * if
         * (inMemorycredentialRepository.getUserHandleForUsername(username).isPresent())
         * {
         * userHandle = inMemorycredentialRepository.getUserHandleForUsername(username);
         * }
         */

        Optional<ByteArray> userHandleOpt = inMemorycredentialRepository.getUserHandleForUsername(username);

        if (userHandleOpt.isEmpty()) {
            // Return an error message if userHandle is not found
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "E");
            errorResponse.put("errMsg", "User handle not found");
            return errorResponse;
        }
        // 2. Get the userHandle
        ByteArray userHandle = userHandleOpt.get();
        System.err.println("026 : " + userHandle);

        // System.out.println("userHandle == AssertionRequest ==> " + userHandle);
        // System.out.println("username == AssertionRequest ==> " + username);

        // 3. Create the AssertionRequest
        AssertionRequest request = relyingParty.startAssertion(
                StartAssertionOptions.builder()
                        .username(Optional.of(username))
                        .userHandle(userHandle)
                        .userVerification(UserVerificationRequirement.PREFERRED)
                        .build());

        System.err.println("027 : " + request);

        inMemorycredentialRepository.getPendingRequests().put(userHandle, request);

        System.err.println("028 : ");
        inMemorycredentialRepository.GetMemoryDetails();

        PublicKeyCredentialRequestOptions options = request.getPublicKeyCredentialRequestOptions();

        System.err.println("029 : " + options);

        Map<String, Object> response = new HashMap<>();

        // Encode challenge
        String challenge = Base64.getUrlEncoder().withoutPadding().encodeToString(
                options.getChallenge().getBytes());
        System.err.println("030 : " + challenge);
        response.put("challenge", challenge);

        System.err.println("031 : " + options.getTimeout().orElse(60000L));
        // Timeout (optional)
        response.put("timeout", options.getTimeout().orElse(60000L));

        System.err.println("031 : " + options.getRpId());
        // RP ID
        response.put("rpId", options.getRpId());

        // AllowCredentials
        /*
         * Optional<List<PublicKeyCredentialDescriptor>> allowedCredentials =
         * options.getAllowCredentials();
         * if (allowedCredentials.isPresent()) {
         * return null; // or handle gracefully
         * } else {
         * List<PublicKeyCredentialDescriptor> lallowedCredentials =
         * allowedCredentials.get();
         * for (PublicKeyCredentialDescriptor cred : lallowedCredentials) {
         * Map<String, Object> credMap = new HashMap<>();
         * credMap.put("type", cred.getType().name().toLowerCase());
         * credMap.put("id",
         * Base64.getUrlEncoder().withoutPadding().encodeToString(cred.getId().getBytes(
         * )));
         * credMap.put("transports",
         * cred.getTransports().stream()
         * .map(transport -> ((PostMapping) transport).name().toLowerCase())
         * .collect(Collectors.toList()));
         * lallowedCredentials.add(cred); // <- This line was missing
         * }
         * response.put("allowCredentials", lallowedCredentials);
         * }
         */

        Optional<List<PublicKeyCredentialDescriptor>> optionalCreds = options.getAllowCredentials();

        List<Map<String, Object>> allowCredentials = new ArrayList<>();

        /*
         * if (optionalCreds.isPresent() && !optionalCreds.get().isEmpty()) {
         * for (PublicKeyCredentialDescriptor cred : optionalCreds.get()) {
         * Map<String, Object> credMap = new HashMap<>();
         * credMap.put("type", cred.getType().name().toLowerCase());
         * credMap.put("id", Base64.getUrlEncoder().withoutPadding()
         * .encodeToString(cred.getId().getBytes()));
         * credMap.put("transports", cred.getTransports().stream()
         * .map((PublicKeyCredentialDescriptor.transport t) -> t.name().toLowerCase())
         * .collect(Collectors.toList()));
         * allowCredentials.add(credMap);
         * }
         * }
         */

        if (optionalCreds.isPresent() && !optionalCreds.get().isEmpty()) {
            for (PublicKeyCredentialDescriptor cred : optionalCreds.get()) {
                Map<String, Object> credMap = new HashMap<>();
                credMap.put("type", cred.getType().name().toLowerCase());
                credMap.put("id", Base64.getUrlEncoder().withoutPadding()
                        .encodeToString(cred.getId().getBytes()));
                credMap.put("transports", cred.getTransports().stream()
                        .map(t -> t.toString().toLowerCase()) // Fix here: using toString() instead of name()
                        .collect(Collectors.toList()));
                allowCredentials.add(credMap);
            }
        }

        if (!allowCredentials.isEmpty()) {
            response.put("allowCredentials", allowCredentials);
        }
        // this will be an empty list if not present

        response.put(
                "userVerification",
                options.getUserVerification()
                        .map(uv -> uv.name().toLowerCase()) // or uv.getValue() if your enum defines it
                        .orElse("required"));

        // extensions — omit `appid` if null
        Map<String, Object> extensions = new HashMap<>();
        options.getExtensions().getAppid().ifPresent(appid -> {
            String appidStr = appid.toString();
            if (appidStr.startsWith("http://localhost")) {
                extensions.put("appid", appidStr);
            }
        });
        response.put("extensions", extensions);

        System.err.println("032 : " + response);

        return response;
    }

    @PostMapping("/webauthn/authenticate")
    public ResponseEntity<CommonResp> Authenticate(
            @RequestBody PublicKeyCredential<AuthenticatorAssertionResponse, ClientAssertionExtensionOutputs> credential) {
        // String username = extractUsernameFromCredential(credential);

        System.err.println("033 : " + credential);

        // ByteArray lCredentialId = credential.getId();

        CommonResp lRespRec = new CommonResp();
        String lStatus = "S";
        String lErrMsg = "";
        String lMsg = "";

        ByteArray userHandle = null;

        System.err.println("034 : ");
        inMemorycredentialRepository.GetMemoryDetails();

        Optional<ByteArray> lAuthUserHandle = credential.getResponse().getUserHandle();
        System.err.println("035 : " + lAuthUserHandle.get());

        // String userHandleBase64 = lAuthUserHandle.get().getBase64Url(); //
        // Base64url-encoded
        String userHandleHex = lAuthUserHandle.get().getHex(); // Hex-encoded
        try {
            userHandle = ByteArray.fromHex(userHandleHex);
            System.out.println("userHandleHex: " + userHandleHex);
            System.out.println("userHandle: " + userHandle);
        } catch (HexException e) {
            e.printStackTrace();
        }

        /*
         * Optional<ByteArray> lCheckUsername =
         * inMemorycredentialRepository.getUserHandleForUsername();
         * if (lCheckUsername.isPresent()) {
         * System.out.println("lCheckUsername +++ > " + lCheckUsername.get());
         * } else {
         * System.out.println("lCheckUsername +++ > " + lCheckUsername);
         * }
         * // System.out.println("lCheckUsername +++ > "+lCheckUsername;);
         */
        String lUsername = inMemorycredentialRepository.getUsernameForUserHandle(userHandle)
                .map(username -> {
                    // Handle the case where the username is found
                    System.out.println("Username found: " + username);
                    return username;
                })
                .orElse(null);
        System.err.println("036 : " + lUsername);
        if (lUsername == null) {
            // Handle the case where the username is not found
            lStatus = "E";
            lErrMsg = "User handle not found";
            lRespRec.setStatus(lStatus);
            lRespRec.setErrMsg(lErrMsg);
            return ResponseEntity.ok(lRespRec);
        }

        // 3. Retrieve the stored AssertionRequest
        AssertionRequest request = inMemorycredentialRepository.getPendingRequests().get(lAuthUserHandle.get());
        System.err.println("037 : " + request);
        if (request == null) {
            // Handle the case where the request is not found
            lStatus = "E";
            lErrMsg = "No matching assertion request";
            lRespRec.setStatus(lStatus);
            lRespRec.setErrMsg(lErrMsg);
            return ResponseEntity.ok(lRespRec);
        }

        try {
            AssertionResult result = relyingParty.finishAssertion(
                    FinishAssertionOptions.builder()
                            .request(request)
                            .response(credential)
                            .build());

            System.err.println("038 : " + result);

            if (result.isSuccess()) {
                // Success — you can log the user in here
                // System.out.println("Authentication successful for user: " + lUsername);
                // lMsg = "Authentication successful for user: " + lUsername;
                lMsg = " Authentication successful for user: " + lUsername;
                lErrMsg = "";
                lStatus = "S";
                lRespRec.setStatus(lStatus);
                lRespRec.setErrMsg(lErrMsg);
                lRespRec.setMsg(lMsg);
                System.err.println("039 : " + lRespRec);
                return ResponseEntity.ok(lRespRec);
            } else {
                // lMsg = " Authentication successful for user: " + lUsername;
                lErrMsg = "Authentication failed";
                lStatus = "E";
                lRespRec.setStatus(lStatus);
                lRespRec.setErrMsg(lErrMsg);
                lRespRec.setMsg(lMsg);
                System.err.println("040 : " + lRespRec);
                return ResponseEntity.ok(lRespRec);
            }
        } catch (Exception e) {
            lErrMsg = "Verification failed: " + e.getMessage();
            lStatus = "E";
            lRespRec.setStatus(lStatus);
            lRespRec.setErrMsg(lErrMsg);
            lRespRec.setMsg(lMsg);
            System.err.println("041 : " + lRespRec);
            return ResponseEntity.ok(lRespRec);

        }

    }

}
