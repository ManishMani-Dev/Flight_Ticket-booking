package com.example.AirLine.Service;

import com.example.AirLine.Dtos.AddPlaneRequest;
import com.example.AirLine.Dtos.AddTripRequest;
import com.example.AirLine.Dtos.CarrierDto;
import com.example.AirLine.Dtos.CreateCarrierRequest;
import com.example.AirLine.Model.Carrier;
import com.example.AirLine.Model.Plane;
import com.example.AirLine.Repository.CarrierRepository;
import com.example.AirLine.Repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrierService {
    @Autowired
    CarrierRepository carrierRepo;
    @Autowired
    PlaneRepository planeRepo;
    public List<CarrierDto> fetchAllCarrier(){
        List<Carrier> myCarriers = carrierRepo.findAll();
        return myCarriers.stream().map(carrier -> {
                                        CarrierDto c = CarrierDto.builder().companyId(carrier.getCompanyId()).companyName(carrier.getCompanyName()).build();
                                        return c;
                                    }).collect(Collectors.toList());
    }
    public Integer createCarrier(CreateCarrierRequest createCarrierRequest){
        if(carrierRepo.findIdByCompanyName(createCarrierRequest.getCompanyName())!=null)
            return carrierRepo.findIdByCompanyName(createCarrierRequest.getCompanyName());
        Carrier carrier= createCarrierRequest.to();
        carrier.setNoOfPlanes(createCarrierRequest.getMyPlanes().size());
        Carrier c= carrierRepo.save(carrier);
        for(AddPlaneRequest p: createCarrierRequest.getMyPlanes()){
            Plane plane = Plane.builder()
                    .chassisNo(p.getChassisNo())
                    .totalNoOfSeats(p.getTotalNoOfSeats())
                    .quality(p.getQuality())
                    .myCarrier(c)
                    .myTrips(new ArrayList<>())
                    .build();
            planeRepo.save(plane);
        }
        return c.getCompanyId();
    }
}
