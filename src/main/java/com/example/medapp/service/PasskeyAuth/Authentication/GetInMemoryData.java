package com.example.medapp.service.PasskeyAuth.Authentication;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.medapp.repository.RegistrationStorage.ChallengeStorage;
import com.example.medapp.repository.RegistrationStorage.InMemoryCredentialRepository;
import com.example.medapp.repository.RegistrationStorage.RegistrationStorage;

public class GetInMemoryData {

    @Autowired
    private static RegistrationStorage registrationStorage;

    @Autowired
    private ChallengeStorage challengeStorage;

    @Autowired
    private InMemoryCredentialRepository inMemorycredentialRepository;

    public static void main(String[] args) {

        System.out.println("Registration Credentials :");
        registrationStorage.getAllCredentialCreationOption();
    }

}
