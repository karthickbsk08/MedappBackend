package com.example.medapp.controller.passkeyAuth.RegisterAuthentication;

import org.springframework.web.bind.annotation.RestController;

import com.example.medapp.model.commonResponse.CommonResp;
import com.example.medapp.repository.RegistrationStorage.InMemoryCredentialRepository;
import com.yubico.webauthn.AssertionRequest;
import com.yubico.webauthn.AssertionResult;
import com.yubico.webauthn.FinishAssertionOptions;
import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartAssertionOptions;
import com.yubico.webauthn.data.AuthenticatorAssertionResponse;
import com.yubico.webauthn.data.AuthenticatorTransport;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.ClientAssertionExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredential;
import com.yubico.webauthn.data.PublicKeyCredentialDescriptor;
import com.yubico.webauthn.data.PublicKeyCredentialRequestOptions;
import com.yubico.webauthn.data.PublicKeyCredentialType;
import com.yubico.webauthn.data.UserVerificationRequirement;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

    // @PostMapping("/webauthn/authenticate-options1")
    // public Map<String, Object> AuthenticateOptions1(@RequestBody Map<String,
    // String> body) {

    // System.out.println("024 : " + body);
    // // 1. Read the Username from the request body
    // String username = body.get("username");
    // System.out.println("025 : " + username);

    // /*
    // * Optional<ByteArray> userHandle = null;
    // * if
    // *
    // (inMemorycredentialRepository.getUserHandleForUsername(username).isPresent())
    // * {
    // * userHandle =
    // inMemorycredentialRepository.getUserHandleForUsername(username);
    // * }
    // */

    // Optional<ByteArray> userHandleOpt =
    // inMemorycredentialRepository.getUserHandleForUsername(username);

    // if (userHandleOpt.isEmpty()) {
    // // Return an error message if userHandle is not found
    // Map<String, Object> errorResponse = new HashMap<>();
    // errorResponse.put("status", "E");
    // errorResponse.put("errMsg", "User handle not found");
    // return errorResponse;
    // }
    // // 2. Get the userHandle
    // ByteArray userHandle = userHandleOpt.get();
    // System.out.println("026 : " + userHandle);

    // // System.out.println("userHandle == AssertionRequest ==> " + userHandle);
    // // System.out.println("username == AssertionRequest ==> " + username);

    // // 3. Create the AssertionRequest
    // // AssertionRequest request = relyingParty.startAssertion(
    // // StartAssertionOptions.builder()
    // // .username(Optional.of(username))
    // // .userHandle(userHandle)
    // // .userVerification(UserVerificationRequirement.PREFERRED)
    // // .build());

    // AssertionRequest request = relyingParty.startAssertion(
    // StartAssertionOptions.builder()
    // .username(Optional.of(username)) // ✅ this is correct
    // // .userHandle(userHandle) // ❌ here is the problem
    // .userVerification(UserVerificationRequirement.PREFERRED)
    // .build());

    // System.out.println("027 : " + request);

    // // inMemorycredentialRepository.getPendingRequests().put(userHandle,
    // request);

    // // System.out.println("028 : ");
    // // inMemorycredentialRepository.GetMemoryDetails();
    // // PublicKeyCredentialRequestOptions options =
    // // request.getPublicKeyCredentialRequestOptions();

    // // System.out.println("029 : " + options);

    // // Map<String, Object> response = new HashMap<>();

    // // // Encode challenge
    // // String challenge = Base64.getUrlEncoder().withoutPadding().encodeToString(
    // // options.getChallenge().getBytes());
    // // System.out.println("030 : " + challenge);
    // // response.put("challenge", challenge);

    // // System.out.println("031 : " + options.getTimeout().orElse(60000L));
    // // // Timeout (optional)
    // // response.put("timeout", options.getTimeout().orElse(60000L));

    // // System.out.println("031 : " + options.getRpId());
    // // // RP ID
    // // response.put("rpId", options.getRpId());

    // // AllowCredentials
    // /*
    // * Optional<List<PublicKeyCredentialDescriptor>> allowedCredentials =
    // * options.getAllowCredentials();
    // * if (allowedCredentials.isPresent()) {
    // * return null; // or handle gracefully
    // * } else {
    // * List<PublicKeyCredentialDescriptor> lallowedCredentials =
    // * allowedCredentials.get();
    // * for (PublicKeyCredentialDescriptor cred : lallowedCredentials) {
    // * Map<String, Object> credMap = new HashMap<>();
    // * credMap.put("type", cred.getType().name().toLowerCase());
    // * credMap.put("id",
    // *
    // Base64.getUrlEncoder().withoutPadding().encodeToString(cred.getId().getBytes(
    // * )));
    // * credMap.put("transports",
    // * cred.getTransports().stream()
    // * .map(transport -> ((PostMapping) transport).name().toLowerCase())
    // * .collect(Collectors.toList()));
    // * lallowedCredentials.add(cred); // <- This line was missing
    // * }
    // * response.put("allowCredentials", lallowedCredentials);
    // * }
    // */

    // // Optional<List<PublicKeyCredentialDescriptor>> optionalCreds =
    // // options.getAllowCredentials();

    // // List<Map<String, Object>> allowCredentials = new ArrayList<>();

    // /*
    // * if (optionalCreds.isPresent() && !optionalCreds.get().isEmpty()) {
    // * for (PublicKeyCredentialDescriptor cred : optionalCreds.get()) {
    // * Map<String, Object> credMap = new HashMap<>();
    // * credMap.put("type", cred.getType().name().toLowerCase());
    // * credMap.put("id", Base64.getUrlEncoder().withoutPadding()
    // * .encodeToString(cred.getId().getBytes()));
    // * credMap.put("transports", cred.getTransports().stream()
    // * .map((PublicKeyCredentialDescriptor.transport t) -> t.name().toLowerCase())
    // * .collect(Collectors.toList()));
    // * allowCredentials.add(credMap);
    // * }
    // * }
    // */

    // // if (optionalCreds.isPresent() && !optionalCreds.get().isEmpty()) {
    // // for (PublicKeyCredentialDescriptor cred : optionalCreds.get()) {
    // // Map<String, Object> credMap = new HashMap<>();
    // // credMap.put("type", cred.getType().name().toLowerCase());
    // // credMap.put("id", Base64.getUrlEncoder().withoutPadding()
    // // .encodeToString(cred.getId().getBytes()));
    // // credMap.put("transports", cred.getTransports().stream()
    // // .map(t -> t.toString().toLowerCase()) // Fix here: using toString()
    // instead
    // // of name()
    // // .collect(Collectors.toList()));
    // // allowCredentials.add(credMap);
    // // }
    // // }

    // // if (!allowCredentials.isEmpty()) {
    // // response.put("allowCredentials", allowCredentials);
    // // }
    // // // this will be an empty list if not present

    // // response.put(
    // // "userVerification",
    // // options.getUserVerification()
    // // .map(uv -> uv.name().toLowerCase()) // or uv.getValue() if your enum
    // defines
    // // it
    // // .orElse("required"));

    // // // extensions — omit `appid` if null
    // // Map<String, Object> extensions = new HashMap<>();
    // // options.getExtensions().getAppid().ifPresent(appid -> {
    // // String appidStr = appid.toString();
    // // if (appidStr.startsWith("http://localhost")) {
    // // extensions.put("appid", appidStr);
    // // }
    // // });
    // // response.put("extensions", extensions);

    // // System.out.println("032 : " + response);

    // // return response;
    // return request.getPublicKeyCredentialRequestOptions();
    // }

    @PostMapping("/webauthn/authenticate")
    public ResponseEntity<CommonResp> Authenticate(
            @RequestBody PublicKeyCredential<AuthenticatorAssertionResponse, ClientAssertionExtensionOutputs> credential) {
        // String username = extractUsernameFrmomCredential(credential);

        System.out.println("033 : " + credential);

        // ByteArray lCredentialId = credential.getId();

        CommonResp lRespRec = new CommonResp();
        String lStatus = "S";
        String lErrMsg = "";
        String lMsg = "";

        ByteArray userHandle = null;

        System.out.println("034 : ");
        inMemorycredentialRepository.GetMemoryDetails();

        // System.out.println("034.1 : " + credential.getId());
        System.out.println("034.1 : " + credential.getResponse().getUserHandle());

        userHandle
        =inMemorycredentialRepository.getUserHandleByCredId(credential.getId());
        System.out.println("034.2 : " + userHandle);

        ByteArray credentialId = credential.getId();
        ByteArray userHandleFromAuthenticator = credential.getResponse().getUserHandle().get(); // unwrap Optional

        userHandle = userHandleFromAuthenticator;

        System.out.println("userHandleFromAuthenticator : " + userHandleFromAuthenticator);

        Optional<RegisteredCredential> maybeCred = inMemorycredentialRepository.lookup(credentialId,
                userHandleFromAuthenticator);
        if (maybeCred.isEmpty()) {
            // Handle the case where the credential is not found
            lStatus = "E";
            lErrMsg = "Credential not found";
            lRespRec.setStatus(lStatus);
            lRespRec.setErrMsg(lErrMsg);
            return ResponseEntity.ok(lRespRec);
        }

        System.out.println("maybeCred : " + maybeCred);

        // Optional<ByteArray> lAuthUserHandle =
        // credential.getResponse().getUserHandle();
        // System.out.println("035 : " + lAuthUserHandle.get());

        // ByteArray lTempUserHandle = lAuthUserHandle.get();

        // // Step 1: Extract hex string from ByteArray(...)
        // String hexInput =
        // "705932544d41504d783539425a6152655463785a6d322d7655485a31445a576a544a796c5738366b4c6773";

        // Base64url-encoded
        // String userHandleHex = lAuthUserHandle.get().getHex(); // Hex-encoded

        // ByteArray userHandle =
        // ByteArray.fromHex("776251365658765a7735394d6f634976334379424d70666c444c344646317568662d53304d384a5041456b");

        // Step 2: Convert hex to bytes
        // byte[] base64UrlBytes = lAuthUserHandle.get().getBytes();

        // // Step 3: Convert bytes to Base64URL string
        // String base64Url = new String(base64UrlBytes);

        // System.out.println("035.1 : " + base64Url);

        // // Step 4: Decode Base64URL string to original userHandle bytes
        // byte[] originalUserHandleBytes = Base64.getUrlDecoder().decode(base64Url);

        // String lDecodedUserHandle = bytesToHex(originalUserHandleBytes);

        // // Step 5: Convert final user handle bytes to hex for logging or comparison
        // // System.out.println("Decoded userHandle (hex): " + lDecodedUserHandle);

        // try {
        // userHandle = ByteArray.fromHex(lDecodedUserHandle);
        // } catch (HexException e) {
        // e.printStackTrace();
        // }
        // System.out.println("userHandle (hex): " + userHandle);

        // String userHandleBase64 = lAuthUserHandle.get().getBase64Url(); //

        // try {
        // userHandle = ByteArray.fromHex(userHandleHex);
        // System.out.println("userHandleHex: " + userHandleHex);
        // System.out.println("userHandle: " + userHandle);
        // } catch (HexException e) {
        // e.printStackTrace();
        // }

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
        System.out.println("036 : " + lUsername);
        if (lUsername == null) {
            // Handle the case where the username is not found
            lStatus = "E";
            lErrMsg = "User handle not found";
            lRespRec.setStatus(lStatus);
            lRespRec.setErrMsg(lErrMsg);
            return ResponseEntity.ok(lRespRec);
        }

        /*
         * System.out.println("============BEFORE===================");
         * inMemorycredentialRepository.GetMemoryDetails();
         * inMemorycredentialRepository.storeUserHandlewithUserName(lUsername,
         * lTempUserHandle);
         * AssertionRequest RequestTimeUserHandle =
         * inMemorycredentialRepository.getPendingRequests().get(userHandle);
         * inMemorycredentialRepository.getPendingRequests().put(lTempUserHandle,
         * RequestTimeUserHandle);
         * inMemorycredentialRepository.getPendingRequests().remove(userHandle);
         * System.out.println("============AFTER===================");
         * inMemorycredentialRepository.GetMemoryDetails();
         */
        // inMemorycredentialRepository.storeUserNamewithUserHandle(lUsername,
        // lTempUserHandle);

        // inMemorycredentialRepository.setpendingRequests(lTempUserHandle, credential);

        // 3. Retrieve the stored AssertionRequest
        AssertionRequest request = inMemorycredentialRepository.getPendingRequests().get(userHandle);
        System.out.println("037 : " + request);
        if (request == null) {
            // Handle the case where the request is not found
            lStatus = "E";
            lErrMsg = "No matching assertion request";
            lRespRec.setStatus(lStatus);
            lRespRec.setErrMsg(lErrMsg);
            return ResponseEntity.ok(lRespRec);
        }

        System.out.println("userHandle : " + userHandle);
        // System.out.println("lTempUserHandle : " + lTempUserHandle);

        /*
         * RegisteredCredential credId =
         * inMemorycredentialRepository.getCredentialIdByUserHandle(userHandle,
         * credential.getId());
         * if (credId != null) {
         * System.out.println("Credential ID found: " + credId);
         * } else {
         * System.out.println("Credential ID not found for userHandle: " + userHandle);
         * }
         * 
         * Boolean flag = inMemorycredentialRepository.removeCredential(userHandle,
         * credential.getId());
         * if (flag) {
         * System.out.println("Credential removed successfully for userHandle: " +
         * userHandle);
         * } else {
         * System.out.println("Credential removal failed for userHandle: " +
         * userHandle);
         * }
         * 
         * inMemorycredentialRepository.addCredential("", lTempUserHandle, credId);
         * 
         * inMemorycredentialRepository.GetMemoryDetails();
         */

        // AssertionRequest newRequest =
        // inMemorycredentialRepository.ModifyAssertionRequest(userHandle,
        // lTempUserHandle);
        System.out.println("request : " + request);
        // System.out.println("newRequest : " + newRequest);
        FinishAssertionOptions finishAssertionOptions = FinishAssertionOptions.builder()
                .request(request)
                .response(credential)
                .build();

        // System.out.println(finishAssertionOptions.getUserHandle());
        // System.out.println(finishAssertionOptions.getUsername());
        // System.out.println(finishAssertionOptions.getUserVerification());
        System.out.println("x001 : " + finishAssertionOptions.getRequest());
        System.out.println("x002 : " + finishAssertionOptions.getResponse());
        System.out.println("x003 : " +
                finishAssertionOptions.getCallerTokenBindingId());
        System.out.println("x004 : " + finishAssertionOptions);

        try {
            AssertionResult result = relyingParty.finishAssertion(
                    finishAssertionOptions);

            System.out.println("038 : " + result);

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
                System.out.println("039 : " + lRespRec);
                return ResponseEntity.ok(lRespRec);
            } else {
                // lMsg = " Authentication successful for user: " + lUsername;
                lErrMsg = "Authentication failed";
                lStatus = "E";
                lRespRec.setStatus(lStatus);
                lRespRec.setErrMsg(lErrMsg);
                lRespRec.setMsg(lMsg);
                System.out.println("040 : " + lRespRec);
                return ResponseEntity.ok(lRespRec);
            }
        } catch (Exception e) {
            lErrMsg = "Verification failed: " + e.getMessage();
            lStatus = "E";
            lRespRec.setStatus(lStatus);
            lRespRec.setErrMsg(lErrMsg);
            lRespRec.setMsg(lMsg);
            System.out.println("041 : " + lRespRec);
            return ResponseEntity.ok(lRespRec);

        }

    }

    /*
     * private static byte[] hexStringToByteArray(String s) {
     * int len = s.length();
     * byte[] data = new byte[len / 2];
     * for (int i = 0; i < len; i += 2) {
     * data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
     * + Character.digit(s.charAt(i + 1), 16));
     * }
     * return data;
     * }
     */

    // Helper: Byte array to hex
   /*  private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    } */

    @PostMapping("/webauthn/authenticate-options")
    public AssertionRequest AuthenticateOptions(
            @RequestBody Map<String, String> body) {

        // Step 1: Extract the username
        String username = body.get("username");
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is required.");
        }
        System.out.println("025 : " + username);

        // Optional<ByteArray> credentialIdOpt = inMemorycredentialRepository.getCredentialIdForUsername(username);
        // if (credentialIdOpt.isEmpty()) {
        //     throw new IllegalArgumentException("Credential not found for user: " + username);
        // }

        // ByteArray credentialId = credentialIdOpt.get();

        // PublicKeyCredentialDescriptor descriptor =
        // PublicKeyCredentialDescriptor.builder()
        // .id(credentialId)
        // .type(PublicKeyCredentialType.PUBLIC_KEY)
        // .build();

        // Build options

        // 1. Build the descriptor for the credential you want to allow
        // PublicKeyCredentialDescriptor descriptor = PublicKeyCredentialDescriptor.builder()
        //         .id(credentialId) // ByteArray (credentialId from registration)
        //         .type(PublicKeyCredentialType.PUBLIC_KEY)
        //         .transports(Optional.of(Set.of(
        //                 AuthenticatorTransport.USB,
        //                 AuthenticatorTransport.NFC,
        //                 AuthenticatorTransport.BLE,
        //                 AuthenticatorTransport.INTERNAL))) // optional but improves UX
        //         .build();


        // 3. Start assertion using these options, let the rest be filled automatically
        // AssertionRequest assertionRequest = relyingParty.startAssertion(
        //         StartAssertionOptions.builder()
        //                 .username(username)
        //                 .userVerification(UserVerificationRequirement.PREFERRED)
        //                 .build());

        // System.out.println("===========================================");
        // inMemorycredentialRepository.GetMemoryDetails();
        // System.out.println("===========================================");

        // System.out.println("AssertionRequest Builder: " +
        // StartAssertionOptions.builder());
        // System.out.println("AssertionRequest username : " +
        // StartAssertionOptions.builder().username(username));
        // System.out.println("AssertionRequest userVerification : " +
        // StartAssertionOptions.builder().username(username)
        // .userVerification(UserVerificationRequirement.PREFERRED));
        // System.out.println("AssertionRequest build : " +
        // StartAssertionOptions.builder()
        // .username(username)
        // .userVerification(UserVerificationRequirement.PREFERRED)
        // .build());
        // System.out.println("userHandle from repo: " +
        // inMemorycredentialRepository.getUserHandleForUsername(username));
        // System.out.println(
        // "userHandle getUserIdentity " +
        // inMemorycredentialRepository.getUserIdentity(username).getId());

        ByteArray lTempUserHandle = null;
        // get userhandle using username
        Optional<ByteArray> UserHandle =
        inMemorycredentialRepository.getUserHandleForUsername(username);
        // if (UserHandle.isPresent()) {
        lTempUserHandle = UserHandle.get();
        System.out.println("lTempUserHandle"+lTempUserHandle);
        // }
        // Step 2: Start the assertion
        AssertionRequest request = relyingParty.startAssertion(
                StartAssertionOptions.builder()
                        .username(username)
                        // .allowCredentials(List.of(descriptor)) // <-- pa
                        // .userHandle(lTempUserHandle)
                        .userVerification(UserVerificationRequirement.REQUIRED)
                        .build());

        System.out.println("027 : " + request);

        // Step 3: Log allowed credentials (optional)
        // request.getPublicKeyCredentialRequestOptions()
        //         .getAllowCredentials()
        //         .ifPresentOrElse(
        //                 creds -> System.out.println("allowedCredentials: " + creds),
        //                 () -> System.out.println("allowedCredentials: none"));

        // Step 4 : Extract the userHandle from the UserIdentity
        // ByteArray userHandle = inMemorycredentialRepository.getUserIdentity(username).getId();

        // Step 7 : Check if the userHandle is already present in the repository by
        // username if exists again reasign the userhandle
        // if (!inMemorycredentialRepository.getUserHandleForUsername(username).isPresent()) {
        //     inMemorycredentialRepository.storeUserNamewithUserHandle(username, userHandle);
        //     inMemorycredentialRepository.storeUserHandlewithUserName(username, userHandle);
        // }

        // Step 4: (Optional) Store full assertion request
        inMemorycredentialRepository.getPendingRequests().put(lTempUserHandle,
                request);

        System.out.println("request : " + request);

        // Step 5: Return the request options
        return request;

        /*
         * // Step 1 : ready the body
         * System.out.println("024 : " + body);
         * 
         * // Step 2 : Extract the Username
         * String username = body.get("username");
         * System.out.println("025 : " + username);
         * 
         * // Step 3 : Get the userHandle by username
         * Optional<ByteArray> userHandleOpt =
         * inMemorycredentialRepository.getUserHandleForUsername(username);
         * ByteArray luserHandle = userHandleOpt.orElse(null);
         * System.out.println("026 : " + luserHandle);
         * 
         * // Step 4 : Prepare AssertionRequest
         * AssertionRequest request = relyingParty.startAssertion(
         * StartAssertionOptions.builder()
         * .username(username)
         * // .userHandle(userHandle)
         * .userVerification(UserVerificationRequirement.PREFERRED)
         * .build());
         * 
         * System.out.println("027 : " + request);
         * 
         * // Step 5 : Get the allowded credentials from the created assertion request
         * Optional<List<PublicKeyCredentialDescriptor>> allowedCredentials = request
         * .getPublicKeyCredentialRequestOptions().getAllowCredentials();
         * System.out.println("allowedCredentials: " + allowedCredentials);
         * 
         * // Step 6 :Share the Created Assertionrequest.
         * return request.getPublicKeyCredentialRequestOptions();
         */

        /*
         * Optional<ByteArray> userHandle = null;
         * if
         * (inMemorycredentialRepository.getUserHandleForUsername(username).isPresent())
         * {
         * userHandle = inMemorycredentialRepository.getUserHandleForUsername(username);
         * }
         */

        // Removed invalid lambda; not needed here.

        // if (userHandleOpt.isEmpty()) {
        // // Return an error message if userHandle is not found
        // Map<String, Object> errorResponse = new HashMap<>();
        // errorResponse.put("status", "E");
        // errorResponse.put("errMsg", "User handle not found");
        // return errorResponse;
        // }
        // 2. Get the userHandle

        // System.out.println("userHandle == AssertionRequest ==> " + userHandle);
        // System.out.println("username == AssertionRequest ==> " + username);

        // 3. Create the AssertionRequest
        // AssertionRequest request = relyingParty.startAssertion(
        // StartAssertionOptions.builder()
        // .username(Optional.of(username))
        // .userHandle(userHandle)
        // .userVerification(UserVerificationRequirement.PREFERRED)
        // .build());

        // inMemorycredentialRepository.getPendingRequests().put(userHandle, request);

        // System.out.println("028 : ");
        // inMemorycredentialRepository.GetMemoryDetails();
        // PublicKeyCredentialRequestOptions options =
        // request.getPublicKeyCredentialRequestOptions();

        // System.out.println("029 : " + options);

        // Map<String, Object> response = new HashMap<>();

        // // Encode challenge
        // String challenge = Base64.getUrlEncoder().withoutPadding().encodeToString(
        // options.getChallenge().getBytes());
        // System.out.println("030 : " + challenge);
        // response.put("challenge", challenge);

        // System.out.println("031 : " + options.getTimeout().orElse(60000L));
        // // Timeout (optional)
        // response.put("timeout", options.getTimeout().orElse(60000L));

        // System.out.println("031 : " + options.getRpId());
        // // RP ID
        // response.put("rpId", options.getRpId());

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

        // Optional<List<PublicKeyCredentialDescriptor>> optionalCreds =
        // options.getAllowCredentials();

        // List<Map<String, Object>> allowCredentials = new ArrayList<>();

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

        // if (optionalCreds.isPresent() && !optionalCreds.get().isEmpty()) {
        // for (PublicKeyCredentialDescriptor cred : optionalCreds.get()) {
        // Map<String, Object> credMap = new HashMap<>();
        // credMap.put("type", cred.getType().name().toLowerCase());
        // credMap.put("id", Base64.getUrlEncoder().withoutPadding()
        // .encodeToString(cred.getId().getBytes()));
        // credMap.put("transports", cred.getTransports().stream()
        // .map(t -> t.toString().toLowerCase()) // Fix here: using toString() instead
        // of name()
        // .collect(Collectors.toList()));
        // allowCredentials.add(credMap);
        // }
        // }

        // if (!allowCredentials.isEmpty()) {
        // response.put("allowCredentials", allowCredentials);
        // }
        // // this will be an empty list if not present

        // response.put(
        // "userVerification",
        // options.getUserVerification()
        // .map(uv -> uv.name().toLowerCase()) // or uv.getValue() if your enum defines
        // it
        // .orElse("required"));

        // // extensions — omit `appid` if null
        // Map<String, Object> extensions = new HashMap<>();
        // options.getExtensions().getAppid().ifPresent(appid -> {
        // String appidStr = appid.toString();
        // if (appidStr.startsWith("http://localhost")) {
        // extensions.put("appid", appidStr);
        // }
        // });
        // response.put("extensions", extensions);

        // System.out.println("032 : " + response);

        // return response;

    }
}