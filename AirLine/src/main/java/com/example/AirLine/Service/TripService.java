package com.example.AirLine.Service;

import com.example.AirLine.Dtos.AddTripRequest;
import com.example.AirLine.Dtos.TripDetailsDTO;
import com.example.AirLine.Dtos.TripFetchRequest;
import com.example.AirLine.Model.Carrier;
import com.example.AirLine.Model.Plane;
import com.example.AirLine.Model.TripDetails;
import com.example.AirLine.Repository.CarrierRepository;
import com.example.AirLine.Repository.PlaneRepository;
import com.example.AirLine.Repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    @Autowired
     TripRepository tripRepo;
    @Autowired
    CarrierRepository carrierRepo;
    @Autowired
    PlaneRepository planeRepo;
    public  List<TripDetailsDTO> findTrips(TripFetchRequest tripFetch){
        List<TripDetails> trips= tripRepo.findTripFetch(tripFetch.getSource(),tripFetch.getDestination(), tripFetch.getStartTime(),tripFetch.getEndTime(),tripFetch.getCarrierId());
        /**
         * using JPA implemented Query Methods
         *         List<TripDetails> trips= tripRepo.findAllBySourceAndDestinationAndStartTimeGreaterThanEqualAndEndTimeLessThanEqualAndMyCarrierCompanyId
         *                                                  (tripFetch.getSource(),tripFetch.getDestination(), tripFetch.getStartTime(),tripFetch.getEndTime(),tripFetch.getCarrierId());
         */
        List<TripDetailsDTO> res= new ArrayList<TripDetailsDTO>();
        for(TripDetails t:trips){
            TripDetailsDTO temp = TripDetailsDTO.builder()
                                                .tripId(t.getTripId())
                                                .source(t.getSource())
                                                .destination(t.getDestination())
                                                .startTime(t.getStartTime())
                                                .endTime(t.getEndTime())
                                                .availableSeats(t.getAvailableSeats())
                                                .build();
            res.add(temp);
        }
        return res;
    }
    public  List<TripDetailsDTO> findTrips(int companyId){
        List<TripDetails> trips= tripRepo.findAllByMyCarrierCompanyId(companyId);
        List<TripDetailsDTO> res= new ArrayList<TripDetailsDTO>();
        for(TripDetails t:trips){
            TripDetailsDTO temp = TripDetailsDTO.builder()
                    .tripId(t.getTripId())
                    .source(t.getSource())
                    .destination(t.getDestination())
                    .startTime(t.getStartTime())
                    .endTime(t.getEndTime())
                    .availableSeats(t.getAvailableSeats())
                    .build();
            res.add(temp);
        }
        return res;
    }
    public int create(AddTripRequest addTripRequest, int companyId){
        Optional<Carrier> c= carrierRepo.findById(companyId);
        Carrier carrier = c.get();
        Optional<Plane> p = planeRepo.findById(addTripRequest.getPlaneId());
        Plane plane = p.get();
        // checking if the plane id given in addTripRequest matches with the companyId or not
        if(companyId!=plane.getMyCarrier().getCompanyId()) return -1;
        TripDetails tripDetails =  TripDetails.builder()
                                                .source(addTripRequest.getSource())
                                                .destination(addTripRequest.getDestination())
                                                .startTime(addTripRequest.getStartTime())
                                                .endTime(addTripRequest.getEndTime())
                                                .myCarrier(carrier)
                                                .myPlane(plane)
                                                .build();
        TripDetails t= tripRepo.save(tripDetails);
        return t.getTripId();
    }
    public String tripRemoval(int tripId,int companyId){
        Optional<TripDetails> trip =tripRepo.findById(tripId);
        TripDetails tripDetails = trip.get();
        if(tripDetails.getMyCarrier().getCompanyId()!=companyId)
            return "You are not authorized to delete the trip which belongs to "
                    + tripDetails.getMyCarrier().getCompanyName();
        else{
            tripRepo.delete(tripDetails);
            return "removed the trip "+tripDetails;
        }
    }
}
