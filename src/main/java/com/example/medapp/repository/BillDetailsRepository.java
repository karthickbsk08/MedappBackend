package com.example.medapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.medapp.model.billentry.Medapp_bill_details;
import com.example.medapp.model.managerdash.SalesReportData;

@Repository
@Component
public interface BillDetailsRepository extends JpaRepository<Medapp_bill_details, Integer> {

        @Query(value = "select mbd.bill_no, mbm.bill_date, mmm.medicine_name, mbd.quantity, mbd.amount " +
                        "from medapp_bill_details mbd " +
                        "join medapp_bill_master mbm ON mbd.bill_no = mbm.bill_no " +
                        "join medapp_medicine_master mmm on mbd.medicine_master_id = mmm.medicine_master_id " +
                        "where mbm.bill_date between (:from_date) and (:to_date);" + "", nativeQuery = true)
        List<SalesReportData> getSalesReportRawByDateRange(@Param("from_date") LocalDate from_date,
                        @Param("to_date") LocalDate to_date);

}
