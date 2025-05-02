package com.example.medapp.service.login;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.medapp.model.Login.LoginResponse;
import com.example.medapp.model.Login.Medapp_login_history;
import com.example.medapp.model.Login.Medapp_Login;
import com.example.medapp.model.Login.UserDetails;
import com.example.medapp.repository.LoginHistoryRepository;
import com.example.medapp.repository.LoginRepository;

@Service
@Component
public class LoginService {

    @Autowired
    private LoginRepository loginRepo;
    @Autowired
    private LoginHistoryRepository loginHistoryRepo;

    public LoginResponse LoginValidate(Medapp_Login login) {

        String UserId = login.getUserId();
        String password = login.getPassword();
        Medapp_login_history loginHistory = new Medapp_login_history();
        UserDetails userDetails = new UserDetails();
        String lStatus = "S";
        String lErrMsg = "";
        String lMsg = "";

        Medapp_Login existingLogin = loginRepo.findByUserIdAndPassword(UserId, password);
        if (existingLogin == null) {
            lStatus = "E";
            lErrMsg = "Invalid User ID or Password";
            lMsg = "Invalid User ID or Password";
            return new LoginResponse(userDetails, lErrMsg, lStatus, lMsg);
        }

        loginHistory.setLoginId(existingLogin.getLoginId());
        loginHistory.setLoginDate(LocalDate.now());
        loginHistory.setLoginTime(LocalTime.now());
        loginHistory.setCreatedBy(existingLogin.getUserId());
        loginHistory.setCreatedDate(LocalDateTime.now());
        loginHistory.setUpdatedBy(existingLogin.getUserId());
        loginHistory.setUpdatedDate(LocalDateTime.now());

        Integer loginHistoryId = InsertInLoginHistory(loginHistory);
        if (loginHistoryId == null) {
            lStatus = "E";
            lErrMsg = "Login History Insertion Failed";
            lMsg = "Login History Insertion Failed";
            return new LoginResponse(userDetails, lErrMsg, lStatus, lMsg);
        }

        userDetails.setLoginId(existingLogin.getLoginId());
        userDetails.setUserId(existingLogin.getUserId());
        userDetails.setLoginHistoryId(loginHistoryId);
        userDetails.setRole(existingLogin.getRole());
        lMsg = "Login Successful";
        return new LoginResponse(userDetails, lErrMsg, lStatus, lMsg);
    }

    public Integer InsertInLoginHistory(Medapp_login_history loginHistory) {

        System.out.println("InsertInLoginHistory");
        System.out.println(loginHistory);
        return loginHistoryRepo.save(loginHistory).getLoginHistoryId();
    }

    public LoginResponse AddUser(Medapp_Login login, String pUser) {

        String lStatus = "S";
        String lErrMsg = "";
        String lMsg = "";
        String lCreatedBy = pUser;
        String lUpdatedBy = pUser;
        LocalDateTime lCreatedDate = LocalDateTime.now();
        LocalDateTime lUpdatedDate = LocalDateTime.now();
        LoginResponse lLoginRespRec = new LoginResponse();

        login.setUpdatedDate(lUpdatedDate);
        login.setCreatedDate(lCreatedDate);
        login.setCreatedBy(lCreatedBy);
        login.setUpdatedBy(lUpdatedBy);

        Medapp_Login lUserExists = loginRepo.findByUserIdAndPassword(login.getUserId(), login.getPassword());
        if (lUserExists != null) {
            lStatus = "E";
            lErrMsg = "User with Same Password Already Exists";
            lMsg = "User with Same Password Already Exists";
            lLoginRespRec.setMsg(lMsg);
            lLoginRespRec.setStatus(lStatus);
            lLoginRespRec.setErrMsg(lErrMsg);
            return lLoginRespRec;
        }

        boolean lexists = loginRepo.existsByUserIdAndPasswordAndRole(login.getUserId(), login.getPassword(),
                login.getRole());
        if (lexists) {
            lStatus = "E";
            lErrMsg = "User with same Password and Role Already Exists";
            lMsg = "User with same Password and Role Already Exists";
            lLoginRespRec.setMsg(lMsg);
            lLoginRespRec.setStatus(lStatus);
            lLoginRespRec.setErrMsg(lErrMsg);

        } else {
            System.out.println("AddUser");
            Medapp_Login existingLogin = loginRepo.save(login);
            if (existingLogin != null) {
                lMsg = "User Added Successfully";
                lLoginRespRec.setMsg(lMsg);
                lLoginRespRec.setStatus(lStatus);
                lLoginRespRec.setErrMsg(lErrMsg);
            }
        }

        return lLoginRespRec;
    }

    public LoginResponse Logout(Integer pLoginHistoryId, String pUser) {

        String lStatus = "S";
        String lErrMsg = "";
        String lMsg = "";
        UserDetails userDetails = new UserDetails();
        LoginResponse loginResponse = new LoginResponse();

        Optional<Medapp_login_history> existingLoginHistoryEntry = loginHistoryRepo.findById(pLoginHistoryId);

        if (existingLoginHistoryEntry.isPresent()) {
            Medapp_login_history loginHistory = existingLoginHistoryEntry.get();
            System.out.println("Logout History Entry: " + loginHistory.toString());
            loginHistory.setLogoutDate(LocalDate.now());
            loginHistory.setLogoutTime(LocalTime.now());
            loginHistory.setUpdatedDate(LocalDateTime.now());
            loginHistory.setUpdatedBy(pUser);
            loginHistory.setLoginHistoryId(pLoginHistoryId);

            loginHistoryRepo.save(loginHistory);
            lMsg = "Logout Successful";

        } else {
            lStatus = "E";
            lErrMsg = "Invalid Login ID";
            lMsg = "Invalid Login ID";
        }
        loginResponse.setMsg(lMsg);
        loginResponse.setStatus(lStatus);
        loginResponse.setErrMsg(lErrMsg);
        loginResponse.setUserDetails(userDetails);

        return loginResponse;

    }

}
