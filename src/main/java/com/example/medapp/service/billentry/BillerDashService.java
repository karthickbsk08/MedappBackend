package com.example.medapp.service.billentry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.medapp.model.billentry.BillerDashRespStruct;
import com.example.medapp.repository.BillMasterRepository;

@Service
@Component
public class BillerDashService {

    @Autowired
    private BillMasterRepository billmasterRepo;

    public BillerDashRespStruct GetBillerDashDetails(Integer loginid) {

        String lStatus ="S";
        String lErrMsg ="";
        String lMsg ="";
        BillerDashRespStruct billdashrespRec = new BillerDashRespStruct();

        billdashrespRec.setTodaysales(billmasterRepo.getTodayNetPriceByLoginId(loginid));
        billdashrespRec.setYesterdaysales(billmasterRepo.getYesterdayNetPriceByLoginId(loginid));
        lMsg="Biller Dash Details fetched Successfully";
        billdashrespRec.setErrmsg(lErrMsg);
        billdashrespRec.setMsg(lMsg);
        billdashrespRec.setStatus(lStatus);
        


        return billdashrespRec;

      
    }



}
