package com.example.AirLine.Repository;

import com.example.AirLine.Dtos.PlaneDto;
import com.example.AirLine.Model.Carrier;
import com.example.AirLine.Model.Plane;
import com.example.AirLine.Model.TripDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CarrierRepository extends JpaRepository<Carrier,Integer> {

//    @Query("select c.companyId from Carrier c where c.companyName = :companyName")
    @Query
    Integer findIdByCompanyName(String companyName);

    @Modifying
    @Transactional
//    @Query("UPDATE Carrier c set c.no_of_planes = ?1 where c.company_id = ?2",nativeQuery = true)---native query
    @Query("UPDATE Carrier c set c.noOfPlanes = :planeCount where c.companyId = :companyId") /**----JPQL query---*/
    void updatePlaneCount(int planeCount,int companyId);

}
