package com.example.medapp.service.stockentry;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.medapp.model.Login.LoginResponse;
import com.example.medapp.model.medapp_stock.MedappMedicineMaster;
import com.example.medapp.model.medapp_stock.MedappStock;
import com.example.medapp.model.stockentry.MedicineNameAndBrand;
import com.example.medapp.model.stockentry.StockEntryResponse;
import com.example.medapp.repository.MedicineMasterRepository;
import com.example.medapp.repository.MedappStockRepository;

@Service
@Component
public class StockEntryService {

    @Autowired
    private MedicineMasterRepository medStockEntryRepository;

    @Autowired
    private MedappStockRepository medStockRepo;

    public StockEntryResponse getAvailableStocks() {

        String lStatus = "S";
        String lMsg = "";
        String lErrMsg = "";
        List<MedicineNameAndBrand> lStockarr = new ArrayList<MedicineNameAndBrand>();
        StockEntryResponse lEntryResponse = new StockEntryResponse();

        lStockarr = medStockEntryRepository.findAllBy();
        if (lStockarr.size() == 0) {
            lStatus = "E";
            lErrMsg = "No Medicine Available";
            lMsg = "No Medicine Available";

            return lEntryResponse;
        }
        lEntryResponse.setErrmsg(lErrMsg);
        lEntryResponse.setMsg(lMsg);
        lEntryResponse.setStatus(lStatus);
        lEntryResponse.setStockarr(lStockarr);

        // printMedicineNamesAndBrands();

        return lEntryResponse;
    }

    public StockEntryResponse AddNewMedicine(MedappMedicineMaster pNewMedicine, String user) {

        String lStatus = "S";
        String lMsg = "";
        String lErrMsg = "";
        List<MedicineNameAndBrand> lStockarr = new ArrayList<MedicineNameAndBrand>();
        StockEntryResponse lAddMedResponse = new StockEntryResponse();

        if (pNewMedicine.getMedicineName() == "" && pNewMedicine.getBrand() == "") {
            lStatus = "E";
            lMsg = "Invalid Medicine Name";
            lErrMsg = "Invalid Medicine Name";
        }

        Optional<MedappMedicineMaster> existingMed = medStockEntryRepository
                .findByMedicineNameAndBrand(pNewMedicine.getMedicineName(), pNewMedicine.getBrand());

        if (existingMed.isPresent()) {
            lStatus = "E";
            lMsg = "Medicine Already Exists";
            lErrMsg = "Medicine Already Exists";
            lAddMedResponse.setErrmsg(lErrMsg);
            lAddMedResponse.setMsg(lMsg);
            lAddMedResponse.setStatus(lStatus);
            return lAddMedResponse;
        }

        pNewMedicine.setCreatedBy(user);
        pNewMedicine.setCreatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        pNewMedicine.setUpdatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        pNewMedicine.setUpdatedBy(user);
        MedappMedicineMaster lAddedMEd = medStockEntryRepository.save(pNewMedicine);
        if (lAddedMEd.getMedicineMasterId() == 0) {
            lStatus = "E";
            lMsg = "New Medicine Addition Failed";
            lErrMsg = "New Medicine Addition Failed";
        }
        MedicineNameAndBrand LastAddedMed = new MedicineNameAndBrand() {
            @Override
            public String getMedicineName() {
                return lAddedMEd.getMedicineName();
            }

            @Override
            public String getBrand() {
                return lAddedMEd.getBrand();
            }
        };
        lMsg = LastAddedMed.getMedicineName() + " Medicine Added Successfully";

        lAddMedResponse.setErrmsg(lErrMsg);
        lAddMedResponse.setMsg(lMsg);
        lAddMedResponse.setStatus(lStatus);
        lStockarr.add(LastAddedMed);
        lAddMedResponse.setStockarr(lStockarr);

        return lAddMedResponse;
    }

    public LoginResponse UpdateStockEntry(MedappStock pNewMedicine, String user) {

        System.out.println("NEW MEDICINE" + pNewMedicine);

        String lStatus = "S";
        String lMsg = "";
        String lErrMsg = "";
        LoginResponse UpdateStockResponse = new LoginResponse();

        String lMedicineName = pNewMedicine.getMedicineName();
        String lBrand = pNewMedicine.getBrand();
        Float lUnitPrice = pNewMedicine.getUnitPrice();

        // check if the medicine name and brand are not empty
        if ((lMedicineName == "" || lMedicineName == null) && (lBrand == "" || lBrand == null)) {
            lStatus = "E";
            lMsg = "Invalid Medicine Name";
            lErrMsg = "Invalid Medicine Name";
            UpdateStockResponse.setErrMsg(lErrMsg);
            UpdateStockResponse.setMsg(lMsg);
            UpdateStockResponse.setStatus(lStatus);
            return UpdateStockResponse;

        }

        // check in medapp_medicine_master table if exists fetch MedicineId
        Optional<MedappMedicineMaster> existingMedicine = medStockEntryRepository
                .findByMedicineNameAndBrand(lMedicineName, lBrand);

        if (existingMedicine.isPresent()) {

            MedappMedicineMaster existingMed = existingMedicine.get();

            pNewMedicine.setMedicineMaster(existingMed);

            Integer lMedicineMasterId = existingMed.getMedicineMasterId();

            if (lMedicineMasterId == 0) {
                lStatus = "E";
                lMsg = "Invalid Medicine Master Id";
                lErrMsg = "Invalid Medicine Master Id";
                UpdateStockResponse.setErrMsg(lErrMsg);
                UpdateStockResponse.setMsg(lMsg);
                UpdateStockResponse.setStatus(lStatus);
                return UpdateStockResponse;
            }

            // check in medapp_stock table if exists
            Optional<MedappStock> existingStock = medStockRepo.findById(lMedicineMasterId);

            if (existingStock.isPresent()) {

                MedappStock existingMedStock = existingStock.get();
                existingMedStock.setQuantity(existingMedStock.getQuantity() + pNewMedicine.getQuantity());
                existingMedStock.setUpdatedBy(user);
                existingMedStock.setUpdatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                existingMedStock.setUnitPrice(lUnitPrice);

               medStockRepo.save(existingMedStock);

                lStatus = "S";
                lMsg = "Stock Updated successfully";
                UpdateStockResponse.setErrMsg(lErrMsg);
                UpdateStockResponse.setMsg(lMsg);
                UpdateStockResponse.setStatus(lStatus);

               /*  if (existingMedStock.getQuantity() != lUpdateMed.getQuantity()) {
                   
                } else {
                    lStatus = "E";
                    lMsg = "Stock Update Failed";
                    lErrMsg = "Stock Update Failed";
                    UpdateStockResponse.setErrMsg(lErrMsg);
                    UpdateStockResponse.setMsg(lMsg);
                    UpdateStockResponse.setStatus(lStatus);
                } */
            } else {

                MedappStock newMedStock = new MedappStock();
                newMedStock.setMedicineMaster(existingMed);
                newMedStock.setQuantity(pNewMedicine.getQuantity());
                newMedStock.setCreatedBy(user);
                newMedStock.setCreatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                newMedStock.setUpdatedBy(user);
                newMedStock.setUpdatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                newMedStock.setUnitPrice(lUnitPrice);

                MedappStock lNewlyUpdateMedStock = medStockRepo.save(newMedStock);
                if (lNewlyUpdateMedStock.getStockId() > 0) {
                    lStatus = "S";
                    lMsg = "Stock Newly Added successfully";
                    UpdateStockResponse.setErrMsg(lErrMsg);
                    UpdateStockResponse.setMsg(lMsg);
                    UpdateStockResponse.setStatus(lStatus);
                } else {
                    lStatus = "E";
                    lMsg = "Stock Addition Failed";
                    lErrMsg = "Stock Addition Failed";
                    UpdateStockResponse.setErrMsg(lErrMsg);
                    UpdateStockResponse.setMsg(lMsg);
                    UpdateStockResponse.setStatus(lStatus);
                }
            }

            //

        } else {
            lStatus = "E";
            lMsg = "Medicine Not Found";
            lErrMsg = "Medicine Not Found";
            UpdateStockResponse.setErrMsg(lErrMsg);
            UpdateStockResponse.setMsg(lMsg);
            UpdateStockResponse.setStatus(lStatus);
            return UpdateStockResponse;
        }

        return UpdateStockResponse;
    }

    /*
     * public void printMedicineNamesAndBrands() {
     * List<MedicineNameAndBrand> stockList =
     * medStockEntryRepository.findMedicineNamesAndBrands();
     * for (MedicineNameAndBrand stock : stockList) {
     * System.out.println("Medicine Name: " + stock.getMedicineName());
     * System.out.println("Brand: " + stock.getBrand());
     * }
     * }
     */
}
