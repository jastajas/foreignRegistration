package com.example.foreign_registration.repository.assessment;

import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.assessment.PackageSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageSizeRepository extends JpaRepository<PackageSize, Long> {

    @Query("SELECT ps FROM PackageSize ps WHERE ps.id IN(:packIds)")
    List<PackageSize> getAllByIds(@Param("packIds") List<Long> packIds);

    List<PackageSize> getAllByAssessment(Assessment assessment);

}
