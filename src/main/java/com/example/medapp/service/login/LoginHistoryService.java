package com.example.medapp.service.login;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.medapp.model.Login.Medapp_login_history;
import com.example.medapp.model.Loginhistory.LoginHistoryResponse;
import com.example.medapp.repository.LoginHistoryRepository;

@Service
public class LoginHistoryService {

    @Autowired
    private LoginHistoryRepository lLoginHistoryRepo;

    public LoginHistoryResponse GetAllLoginHistory() {

        String lStatus = "S";
        String lErrMsg = "";
        String lMsg = "";
        LoginHistoryResponse lLoginHistoryResponse = new LoginHistoryResponse();
        DateTimeFormatter dTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Medapp_login_history> loginHistory = lLoginHistoryRepo
                .findAll(Sort.by(Sort.Direction.DESC, "loginHistoryId"));
        if (loginHistory.isEmpty()) {
            lStatus = "E";
            lErrMsg = "No Login History Found";
            lMsg = "No Login History Found";
            lLoginHistoryResponse.setMsg(lMsg);
            lLoginHistoryResponse.setErrMsg(lErrMsg);
            lLoginHistoryResponse.setStatus(lStatus);
            return lLoginHistoryResponse;
        }
        lMsg = "Login History Found";
        loginHistory = loginHistory.stream().map((entity) -> {
            if (entity.getLoginTime() != null) {
                // String lLogoutTime = entity.getLoginTime().format(dTimeFormatter);
                // entity.setLoginTime( );
                entity.setLoginTime(entity.getLoginTime().truncatedTo(ChronoUnit.SECONDS));
            }
            if (entity.getLogoutTime() != null) {
                // entity.getLogoutTime().format(dTimeFormatter);
                entity.setLogoutTime(entity.getLogoutTime().truncatedTo(ChronoUnit.SECONDS));
            }
            entity.setCreatedDate(
                    LocalDateTime.parse(entity.getCreatedDate().format(dTimeFormatter), dTimeFormatter));
            entity.setUpdatedDate(
                    LocalDateTime.parse(entity.getUpdatedDate().format(dTimeFormatter), dTimeFormatter));

            return entity;
        }).toList();
        lLoginHistoryResponse.setLogin_historyarr(loginHistory);
        lLoginHistoryResponse.setMsg(lMsg);
        lLoginHistoryResponse.setErrMsg(lErrMsg);
        lLoginHistoryResponse.setStatus(lStatus);
        return lLoginHistoryResponse;
    }

}
