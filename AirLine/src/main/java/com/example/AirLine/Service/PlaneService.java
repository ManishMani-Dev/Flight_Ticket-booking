package com.example.AirLine.Service;

import com.example.AirLine.Dtos.AddPlaneRequest;
import com.example.AirLine.Dtos.PlaneDto;
import com.example.AirLine.Model.Carrier;
import com.example.AirLine.Model.Plane;
import com.example.AirLine.Repository.CarrierRepository;
import com.example.AirLine.Repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PlaneService {
    @Autowired
    CarrierRepository carrierRepo;
    @Autowired
    PlaneRepository planeRepo;

     public int create(AddPlaneRequest addPlaneRequest,int companyId){
         Optional<Carrier> c= carrierRepo.findById(companyId);
         Carrier carrier = c.get();
         Plane plane = Plane.builder()
                 .chassisNo(addPlaneRequest.getChassisNo())
                 .totalNoOfSeats(addPlaneRequest.getTotalNoOfSeats())
                 .quality(addPlaneRequest.getQuality())
                 .myCarrier(carrier)
                 .myTrips(new ArrayList<>())
                 .build();
         Plane p = planeRepo.save(plane);
         carrier.getMyFlights().add(p);
         int noOfPlanes = carrier.getNoOfPlanes();
         carrierRepo.updatePlaneCount(noOfPlanes + 1,companyId);
         return p.getPlaneId();
     }
     public int planeRemoval(int planeId,int companyId){
         Optional<Plane> p = planeRepo.findById(planeId);
         Plane plane =p.get();
         if(plane.getMyCarrier().getCompanyId() != companyId) return -1;
         planeRepo.delete(plane);
         return plane.getPlaneId();
     }
     public List<PlaneDto> fetchAllPlanes(int companyId){
         List<Plane> planes= planeRepo.findAllByCompanyId(companyId);
         return planes.stream().map(p->PlaneDto.builder()
                                     .planeId(p.getPlaneId())
                                     .chassisNo(p.getChassisNo())
                                     .totalNoOfSeats(p.getTotalNoOfSeats())
                                     .myCarrierId(p.getMyCarrier().getCompanyId())
                                     .build())
                                     .collect(Collectors.toList());
     }
}
