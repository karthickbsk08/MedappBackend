
package com.example.medapp.service.managerDash;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.medapp.model.managerdash.ChartDetail;
import com.example.medapp.model.managerdash.ManagerDashRespDetail;
import com.example.medapp.model.managerdash.SalesReportData;
import com.example.medapp.model.managerdash.SalesReportReq;
import com.example.medapp.model.managerdash.SalesReportResponse;
import com.example.medapp.repository.BillDetailsRepository;
import com.example.medapp.repository.BillMasterRepository;
import com.example.medapp.repository.MedappStockRepository;

@Service
@Component
public class ManagerDashService {

    @Autowired
    private BillDetailsRepository billDetailsRepo;

    @Autowired
    private MedappStockRepository medappStockRepo;

    @Autowired
    private BillMasterRepository billMasterRepo;

    public SalesReportResponse getSalesReport(SalesReportReq pInput) {

        LocalDate from_date = getDateFromString(pInput.getFrom_date(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate to_date = getDateFromString(pInput.getTo_date(), DateTimeFormatter.ISO_LOCAL_DATE);
        String lStatus = "S";
        String lMsg = "";
        ;
        String lErrMsg = "";
        SalesReportResponse lSalesReportResponse = new SalesReportResponse();
        List<SalesReportData> data = billDetailsRepo.getSalesReportRawByDateRange(from_date, to_date);
        if (data.size() == 0) {
            lStatus = "E";
            lErrMsg = "No data found";
            lMsg = "No data found";
            lSalesReportResponse.setStatus(lStatus);
            lSalesReportResponse.setErrmsg(lErrMsg);
            lSalesReportResponse.setMsg(lMsg);
            return lSalesReportResponse;
        }

        System.out.println();

        lSalesReportResponse.setStatus(lStatus);
        lSalesReportResponse.setErrmsg(lErrMsg);
        lSalesReportResponse.setMsg(lMsg);
        List<Object> salesReportData = new ArrayList<>(data);
        lSalesReportResponse.setSalesarr(salesReportData);

        return lSalesReportResponse;

    }

    public LocalDate getDateFromString(String string,
            DateTimeFormatter format) {
        // Converting the string to date
        // in the specified format
        LocalDate date = LocalDate.parse(string, format);

        // Returning the converted date
        return date;
    }

    public ManagerDashRespDetail GetFinalDashDetails() {
        ManagerDashRespDetail lFinalRespRec = new ManagerDashRespDetail();

        String lStatus = "S";
        String lErrMsg = "";
        String lmsg = "";

        lFinalRespRec = GetSalesAndInventoryValue(lFinalRespRec);
        lFinalRespRec = GetSalesofDailyForAWeek(lFinalRespRec);
        lFinalRespRec = GetMonthlySalesValue(lFinalRespRec);
        lFinalRespRec = GetTodaySalesPerformance(lFinalRespRec);    
        lFinalRespRec = GetCurrMonthSalesmanPef(lFinalRespRec); 
        lFinalRespRec.setStatus(lStatus);
        lFinalRespRec.setErrMsg(lErrMsg);
        lFinalRespRec.setMsg(lmsg);


        return lFinalRespRec;
    }

    public ManagerDashRespDetail GetSalesAndInventoryValue(ManagerDashRespDetail lFinalRespRec) {

        Float lTodaySales = billMasterRepo.getNetPriceByTodayBillDate();
        lFinalRespRec.setTodaySales(lTodaySales);

        Float lTotalInventoryValue = medappStockRepo.GetTotalInventoryValue();
        lFinalRespRec.setInventoryValue(lTotalInventoryValue);

     

        return lFinalRespRec;
    }

    public ManagerDashRespDetail GetSalesofDailyForAWeek(ManagerDashRespDetail lFinalRespRec) {

        List<ChartDetail> dailySalesArr = billMasterRepo.getDailySales();
        // if (dailySalesArr == null) {

        // }

        System.out.println("Length : " + dailySalesArr.size());

        System.out.println("data : " + dailySalesArr);
        lFinalRespRec.setDailySalesArr(dailySalesArr);

        // Float lTotalInventoryValue = medappStockRepo.GetTotalInventoryValue();
        // lFinalRespRec.setInventoryValue(lTotalInventoryValue);

        return lFinalRespRec;
    }

    public ManagerDashRespDetail GetMonthlySalesValue(ManagerDashRespDetail lFinalRespRec) {

            List<ChartDetail> monthlySalesArr = billMasterRepo.getMonthlySales();
            lFinalRespRec.setMonthlySalesArr(monthlySalesArr);

        
        return lFinalRespRec;

    }

    public ManagerDashRespDetail GetTodaySalesPerformance(ManagerDashRespDetail lFinalRespRec) {

        List<ChartDetail> todaySalesPerf = billMasterRepo.getCurrentDaySalesmanPeformance();
        lFinalRespRec.setTodaySalesArr(todaySalesPerf);

    
    return lFinalRespRec;

}

public ManagerDashRespDetail GetCurrMonthSalesmanPef(ManagerDashRespDetail lFinalRespRec) {

    List<ChartDetail> currentMonSalesmanPefArr = billMasterRepo.getCurrentMonthSalesmanPerformance();
    lFinalRespRec.setCurrentMonthArr(currentMonSalesmanPefArr);


return lFinalRespRec;

}


    
}
