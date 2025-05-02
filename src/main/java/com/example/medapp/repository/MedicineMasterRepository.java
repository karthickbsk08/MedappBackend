package com.example.medapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.medapp.model.medapp_stock.MedappMedicineMaster;
import com.example.medapp.model.stockentry.MedicineNameAndBrand;

@Repository
@Component
public interface MedicineMasterRepository extends JpaRepository<MedappMedicineMaster, Integer> {
    // Custom query methods can be defined here if needed
    // For example, to find a medicine by its name:
    // List<MedappMedicineMaster> findByMedicineName(String medicineName);

    List<MedicineNameAndBrand> findAllBy();

    Optional<MedappMedicineMaster> findByMedicineNameAndBrand(String medicineName, String brand);

    // List<MedicineNameAndBrand> findByMedicineNameAndBrand();

    

    

}
