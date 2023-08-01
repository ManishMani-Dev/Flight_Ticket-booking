package com.example.AirLine.Repository;

import com.example.AirLine.Model.TripDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

public interface TripRepository extends JpaRepository<TripDetails,Integer> {
    @Query(value = "select t from TripDetails t where t.source =:source and t.destination=:destination and t.startTime >= :startTime and t.endTime <= :endTime and t.myCarrier.companyId = :companyId")
    List<TripDetails> findTripFetch(String source, String destination, Timestamp startTime, Timestamp endTime, Integer companyId);

//    List<TripDetails> findAllBySourceAndDestinationAndStartTimeGreaterThanEqualAndEndTimeLessThanEqualAndMyCarrierCompanyId(String source, String destination, Timestamp startTime, Timestamp endTime, Integer companyId);

    @Query //TODO find out how to fetch details using foreign key-->solved <3
    List<TripDetails> findAllByMyCarrierCompanyId(int companyId);
}
