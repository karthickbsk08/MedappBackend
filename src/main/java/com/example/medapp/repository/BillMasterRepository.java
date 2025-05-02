package com.example.medapp.repository;

import com.example.medapp.model.billentry.AddMedicine;
import com.example.medapp.model.billentry.Medapp_bill_master;
import com.example.medapp.model.managerdash.ChartDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface BillMasterRepository extends JpaRepository<Medapp_bill_master, Integer> {

        @Query("SELECT MAX(m.billNo) FROM Medapp_bill_master m")
        Integer findMaxBillNo();

        /*
         * @Query("SELECT m.medicineMasterId AS medicineMasterId, " +
         * "m.medicineName AS medicineName, " +
         * "m.brand AS brand, " +
         * ":quantity AS quantity, " +
         * "(:quantity * cast(ms1_0.unit_price as float4) ) AS totalPrice " +
         * "FROM MedappStock s JOIN s.medicineMaster m " +
         * "WHERE m.medicineName = :medicineName")
         * AddMedicine findAddMedicineByMedicineNameAndQuantity(@Param("medicineName")
         * String medicineName,
         * 
         * @Param("quantity") Integer quantity);
         */

        @Query(value = "SELECT " +
                        "mm.medicine_master_id AS medicineMasterId, " +
                        "mm.medicine_name AS medicineName, " +
                        "mm.brand AS brand, " +
                        ":quantity AS quantity, " +
                        "CAST(ms.unit_price AS float4) AS unitPrice, " +
                        "(CAST(:quantity AS float4) * CAST(ms.unit_price AS float4)) AS totalPrice " +
                        "FROM medapp_stock ms " +
                        "JOIN medapp_medicine_master mm ON mm.medicine_master_id = ms.medicine_master_id " +
                        "WHERE mm.medicine_name = :medicineName", nativeQuery = true)
        AddMedicine findAddMedicineByMedicineNameAndQuantity(
                        @Param("medicineName") String medicineName,
                        @Param("quantity") Integer quantity);

        @Query(value = "SELECT COALESCE(ROUND(SUM(mbm.net_price), 2), 0) " +
                        "FROM medapp_bill_master mbm " +
                        "WHERE login_id = :loginId AND bill_date = CURRENT_DATE", nativeQuery = true)
        Double getTodayNetPriceByLoginId(@Param("loginId") Integer loginId);

        @Query(value = "SELECT COALESCE(ROUND(SUM(mbm.net_price), 2), 0) " +
                        "FROM medapp_bill_master mbm " +
                        "WHERE login_id = :loginId AND bill_date = CURRENT_DATE - 1", nativeQuery = true)
        Double getYesterdayNetPriceByLoginId(@Param("loginId") Integer loginId);

        @Query(value = "select coalesce (round(sum(mbm.net_price),2),0) " +
                        "from medapp_bill_master mbm where bill_date =CURRENT_DATE;", nativeQuery = true)
        Float getNetPriceByTodayBillDate();

        /*
         * @Query(value =
         * "SELECT COALESCE(ROUND(SUM(mbm.net_price), 2), 0) AS totalAmount, " +
         * "TO_CHAR(SalesTable.SalesDate, 'FMDay') AS dynamicParam, " +
         * "'day' AS dynamicKeyName " +
         * "FROM ( " +
         * "SELECT DATE(CURRENT_DATE - INTERVAL '6 day') AS SalesDate UNION ALL " +
         * "SELECT DATE(CURRENT_DATE - INTERVAL '5 day') AS SalesDate UNION ALL " +
         * "SELECT DATE(CURRENT_DATE - INTERVAL '4 day') AS SalesDate UNION ALL " +
         * "SELECT DATE(CURRENT_DATE - INTERVAL '3 day') AS SalesDate UNION ALL " +
         * "SELECT DATE(CURRENT_DATE - INTERVAL '2 day') AS SalesDate UNION ALL " +
         * "SELECT DATE(CURRENT_DATE - INTERVAL '1 day') AS SalesDate UNION ALL " +
         * "SELECT DATE(CURRENT_DATE) " +
         * ") SalesTable " +
         * "LEFT JOIN medapp_bill_master mbm ON mbm.bill_date = SalesTable.SalesDate " +
         * "GROUP BY SalesTable.SalesDate " +
         * "ORDER BY SalesTable.SalesDate", nativeQuery = true)
         * 
         * @org.springframework.data.jpa.repository.query.QueryHints({
         * javax.persistence.QueryHint(name = "javax.persistence.query.result-mapping",
         * value = "ChartDetailMapping")
         * })
         * List<ChartDetail> getDailySales();
         */

        @Query(name = "ChartDetail.getDailySales", nativeQuery = true)
        List<ChartDetail> getDailySales();


        @Query(name= "ChartDetail.getMonthlySales", nativeQuery = true)
        List<ChartDetail> getMonthlySales();

        @Query(name = "ChartDetail.GetSalesmanCurrentDayPef",nativeQuery = true)
        List<ChartDetail>  getCurrentDaySalesmanPeformance();

        @Query(name = "ChartDetail.getSalesmanCurrentMonthlyPef",nativeQuery = true)
        List<ChartDetail>  getCurrentMonthSalesmanPerformance();

}
