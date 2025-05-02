package com.example.medapp.service.stockview;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.medapp.model.medapp_stock.MedAppStockResponse;
import com.example.medapp.model.medapp_stock.MedappStockView;
import com.example.medapp.repository.MedappStockRepository;

@Service
@Component
public class MedappStockService {

    @Autowired
    MedappStockRepository medStockRepo;

    public MedAppStockResponse getMedAppStock() {

        String lStatus = "S";
        String lErrMsg = "";
        String lMsg = "";
        MedAppStockResponse lMedAppStockResponse = new MedAppStockResponse();

        List<MedappStockView> stockList = medStockRepo.fetchMedicineStockView();
        if (stockList.size() == 0) {
            lStatus = "E";
            lErrMsg = "No Stock Available";
            lMsg = "No Stock Available";
            lMedAppStockResponse.setErrMsg(lErrMsg);
            lMedAppStockResponse.setStatus(lStatus);
            lMedAppStockResponse.setMsg(lMsg);
            return lMedAppStockResponse;
        }
        lMsg="Stocks's Fetched Successfully!";
        lMedAppStockResponse.setMsg(lMsg);
        lMedAppStockResponse.setErrMsg(lErrMsg);
        lMedAppStockResponse.setStatus(lStatus);
        lMedAppStockResponse.setStockviewarr(stockList);
        return lMedAppStockResponse;
    }

}
