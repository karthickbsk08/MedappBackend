package com.example.medapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.medapp.model.medapp_stock.MedappStockView;

import jakarta.transaction.Transactional;

import com.example.medapp.model.billentry.MedicineNameAndQuantity;
import com.example.medapp.model.medapp_stock.MedappStock;

@Repository
@Component
public interface MedappStockRepository extends JpaRepository<MedappStock, Integer> {

    @Query("SELECT new com.example.medapp.model.medapp_stock.MedappStockView(" +
            "m.medicineName, m.brand, s.unitPrice, s.quantity) " +
            "FROM MedappStock s " +
            "JOIN s.medicineMaster m")
    List<MedappStockView> fetchMedicineStockView();

    @Query("SELECT m.medicineName AS medicineName, s.quantity AS quantity " +
            "FROM MedappStock s JOIN s.medicineMaster m")
    List<MedicineNameAndQuantity> findAllMedicineNamesAndQuantities();

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE medapp_stock
            SET quantity = quantity - :quantity,
                updated_by = :updatedBy,
                updated_date = CURRENT_TIMESTAMP
            WHERE medicine_master_id = :medicine_master_id
            """, nativeQuery = true)
    int updateStockQuantity(
            @Param("quantity") int quantity,
            @Param("updatedBy") String updatedBy,
            @Param("medicineName") String medicineName,
            @Param("brand") String brand,
            @Param("medicine_master_id") Integer medicine_master_id);

    @Query(value = "select coalesce (round(sum(ms.quantity *ms.unit_price),2),0) totalInventoryValue " +
            "from medapp_stock ms;", nativeQuery = true)
    Float GetTotalInventoryValue();






}
