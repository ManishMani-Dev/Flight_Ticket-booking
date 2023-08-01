package com.example.AirLine.Repository;

import com.example.AirLine.Dtos.PlaneDto;
import com.example.AirLine.Model.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaneRepository extends JpaRepository<Plane,Integer> {
    /**
     * here foreign key referencing is done like referencing attribute of object
     */
    @Query("select p from Plane p where p.myCarrier.companyId= :companyId")
    List<Plane> findAllByCompanyId(int companyId);
}
