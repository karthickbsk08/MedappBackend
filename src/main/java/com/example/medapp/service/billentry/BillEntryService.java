package com.example.medapp.service.billentry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.medapp.model.Login.Medapp_Login;
import com.example.medapp.model.billentry.AddMedBillResponse;
import com.example.medapp.model.billentry.AddMedicine;
import com.example.medapp.model.billentry.BillDetailsRequest;
import com.example.medapp.model.billentry.BillEntryResponse;
import com.example.medapp.model.billentry.BillStockResponse;
import com.example.medapp.model.billentry.Medapp_bill_details;
import com.example.medapp.model.billentry.Medapp_bill_master;
import com.example.medapp.model.billentry.MedicineNameAndQuantity;
import com.example.medapp.model.billentry.saveBillRequest;
import com.example.medapp.model.medapp_stock.MedappMedicineMaster;
import com.example.medapp.model.medapp_stock.MedappStock;
import com.example.medapp.repository.BillDetailsRepository;
import com.example.medapp.repository.BillMasterRepository;
import com.example.medapp.repository.LoginRepository;
import com.example.medapp.repository.MedicineMasterRepository;
import com.example.medapp.repository.MedappStockRepository;

@Service
@Component
public class BillEntryService {

    @Autowired
    private MedappStockRepository medappStockRepo;

    @Autowired
    private BillMasterRepository billMasterRepo;

    @Autowired
    private BillDetailsRepository billDetailsrepo;

    @Autowired
    private MedicineMasterRepository medicinemasterRepo;

    @Autowired
    private LoginRepository loginRepo;

    public BillStockResponse getBillStock() {

        String lStatus = "S";
        String lMsg = "";
        String lErrMsg = "";

        BillStockResponse billStockResponse = new BillStockResponse();

        List<MedicineNameAndQuantity> lAvailableStock = medappStockRepo.findAllMedicineNamesAndQuantities();

        if (lAvailableStock.size() == 0 || lAvailableStock == null) {
            lStatus = "E";
            lMsg = "No stock available";
            lErrMsg = "No stock available";
            billStockResponse.setStatus(lStatus);
            billStockResponse.setMsg(lMsg);
            billStockResponse.setErrMsg(lErrMsg);
            return billStockResponse;
        }
        billStockResponse.setBillstockarr(lAvailableStock);

        Integer lNxtBillNo = billMasterRepo.findMaxBillNo();
        if (lNxtBillNo == null || lNxtBillNo == 0) {
            // lStatus = "E";
            // lMsg = "Bill No not generated";
            // lErrMsg = "Bill No not generated";
            lNxtBillNo = 1001;
        }
        billStockResponse.setBill_no(lNxtBillNo + 1);
        lMsg = "Available Stock Fetched!";
        billStockResponse.setStatus(lStatus);
        billStockResponse.setMsg(lMsg);
        billStockResponse.setErrMsg(lErrMsg);

        return billStockResponse;
    }

    public AddMedBillResponse AddBill(MedappStock pNewMed) {

        String lStatus = "S";
        String lMsg = "";
        String lErrMsg = "";
        AddMedBillResponse addMedBillResponse = new AddMedBillResponse();

        String lMedicineName = pNewMed.getMedicineName();
        Integer lQuantity = pNewMed.getQuantity();

        AddMedicine existingMed = billMasterRepo.findAddMedicineByMedicineNameAndQuantity(lMedicineName, lQuantity);
        if (existingMed == null || existingMed.getMedicineName() == "") {
            lStatus = "E";
            lMsg = "Medicine not found";
            lErrMsg = "Medicine not found";
        } else {
            addMedBillResponse.setMedicinearr(existingMed);
            lMsg = "Medicine found";
        }
        // Set response values
        addMedBillResponse.setStatus(lStatus);
        addMedBillResponse.setMsg(lMsg);
        addMedBillResponse.setErrMsg(lErrMsg);

        return addMedBillResponse;
    }

    public BillEntryResponse saveBill(saveBillRequest newBill) {

        // Declaration for final response
        String lStatus = "S";
        String lMsg = "";
        String lErrMsg = "";
        BillEntryResponse lBillEntryRespRec = new BillEntryResponse();

        // Preparation for medapp_bill_details insert
        List<Medapp_bill_details> bill_detailsArr = new ArrayList<Medapp_bill_details>();

        // Preparation for medapp_bill_master insert
        Float totalPrice = 0f;
        Float gst = 0f;
        Float netPrice = 0f;
        Medapp_bill_master billMaster = new Medapp_bill_master();

        for (BillDetailsRequest bill : newBill.getBillsarr()) {

            Medapp_bill_details billdetails = new Medapp_bill_details();
            billdetails.setBillNo(newBill.getBill_no());

            Optional<MedappMedicineMaster> med = medicinemasterRepo.findById(bill.getMedicineMasterId());
            if (med.isPresent()) {
                billdetails.setMedicineMaster(med.get());
            }

            billdetails.setQuantity(bill.getQuantity());
            billdetails.setUnitPrice(bill.getUnitPrice());
            billdetails.setAmount(bill.getTotalPrice());
            billdetails.setCreatedBy(lErrMsg);

            // Set created_by and created_date
            billdetails.setCreatedBy(newBill.getUser_id());
            billdetails.setCreatedDate(LocalDateTime.now());

            // Set updated_by and updated_date
            billdetails.setUpdatedBy(newBill.getUser_id());
            billdetails.setUpdatedDate(LocalDateTime.now());

            bill_detailsArr.add(billdetails);

            totalPrice += bill.getTotalPrice();
        }

        // insert into medapp_bill_details
        bill_detailsArr.forEach(billDetails -> {
            billDetailsrepo.save(billDetails);
        });

        // prepare instance to insert in medapp_bill_master
        gst = (totalPrice * 18) / 100;
        netPrice = totalPrice + gst;

        billMaster.setBillNo(newBill.getBill_no());
        billMaster.setBillDate(newBill.getBill_date());
        billMaster.setBillAmount(totalPrice);
        billMaster.setBillGst(gst);
        billMaster.setNetPrice(netPrice);

        Optional<Medapp_Login> loginDetail = loginRepo.findById(newBill.getLogin_id());
        if (loginDetail.isPresent()) {
            billMaster.setMedappLogin(loginDetail.get());
        }

        billMaster.setCreatedBy(newBill.getUser_id());
        billMaster.setCreatedDate(LocalDateTime.now());
        billMaster.setUpdatedBy(newBill.getUser_id());
        billMaster.setUpdatedDate(LocalDateTime.now());

        Medapp_bill_master lInsertedBill = billMasterRepo.save(billMaster);

        // update the stock in medapp_Stock

        for (Medapp_bill_details billDetails : bill_detailsArr) {

            Integer lUpdateQty = billDetails.getQuantity();
            String lUpdatedBy = newBill.getUser_id();
            String MedicineName = billDetails.getMedicineMaster().getMedicineName();
            String lUpdateBrand = billDetails.getMedicineMaster().getBrand();
            Integer lUpdateMedicineMasterId = billDetails.getMedicineMaster().getMedicineMasterId();

            int updatedQuantity = medappStockRepo.updateStockQuantity(lUpdateQty, lUpdatedBy, MedicineName,
                    lUpdateBrand, lUpdateMedicineMasterId);
            if (updatedQuantity == 0) {
                lStatus = "E";
                lMsg = "Failed to update stock for medicine: " + billDetails.getMedicineMaster().getMedicineName();
                lErrMsg = "Failed to update stock for medicine: " + billDetails.getMedicineMaster().getMedicineName();
            }
        }

        // get maximum bill no
        lBillEntryRespRec.setBill_no(billMasterRepo.findMaxBillNo() + 1);

        lMsg = "Bill No " + lInsertedBill.getBillNo() + " Saved Successfully ";
        lBillEntryRespRec.setErrMsg(lErrMsg);
        lBillEntryRespRec.setMsg(lMsg);
        lBillEntryRespRec.setStatus(lStatus);

        // bill_details_id, bill_no, medicine_master_id, quantity, unit_price, amount,
        // created_by, created_date, updated_by, updated_date

        // billDetailsrepo.save(null)

        // System.out.println("NEW BILL : " + newBill);

        return lBillEntryRespRec;

    }

    /*
     * public void InsertIntoMedappBillDetails(Medapp_bill_details[] billDetails) {
     * billDetailsrepo.save(billDetails);
     * // Additional logic if needed
     * }
     */
}

/*
 * CREATE TABLE medapp_bill_master (
 * bill_master_id SERIAL PRIMARY KEY,
 * bill_no INT UNIQUE NOT NULL,
 * bill_date DATE,
 * bill_amount VARCHAR(20),
 * bill_gst VARCHAR(20) NOT NULL,
 * net_price VARCHAR(20) NOT NULL,
 * login_id INT REFERENCES medapp_login(login_id),
 * created_by VARCHAR(30),
 * created_date TIMESTAMP DEFAULT NOW(),
 * updated_by VARCHAR(30),
 * updated_date TIMESTAMP DEFAULT NOW()
 * );
 */