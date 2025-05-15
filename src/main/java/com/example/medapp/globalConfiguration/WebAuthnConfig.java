package com.example.medapp.globalConfiguration;

import com.example.medapp.repository.RegistrationStorage.InMemoryCredentialRepository;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.data.RelyingPartyIdentity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class WebAuthnConfig {

    @Bean
    public RelyingParty relyingParty() {
        RelyingPartyIdentity rpIdentity = RelyingPartyIdentity.builder()
                .id("localhost") // Your site's domain (RP ID)
                .name("MedApp") // Display name shown to users
                .build();

        return RelyingParty.builder()
                .identity(rpIdentity)
                .credentialRepository(new InMemoryCredentialRepository()) // You must implement this interface
                .origins(Set.of("http://localhost:8080")) // The allowed frontend origin               
                .build();
    }
}
