package com.example.AirLine.Controller;

import com.example.AirLine.Dtos.*;
import com.example.AirLine.Service.CarrierService;
import com.example.AirLine.Service.PlaneService;
import com.example.AirLine.Service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrier")
public class AdminController {
    @Autowired
    CarrierService carrierService;
    @Autowired
    TripService tripService;
    @Autowired
    PlaneService planeService;

    /**--------------------------------------GET-MAPPINGS----------------------------------------*/
    @GetMapping()
    List<CarrierDto> getCarriers(){
        return carrierService.fetchAllCarrier();
    }
    @GetMapping("/{cId}")
     List<PlaneDto> getPlanes(@PathVariable("cId") int companyId){
        return planeService.fetchAllPlanes(companyId);
    }
    @GetMapping("/{cId}/trips")
    List<TripDetailsDTO> getAllTrips(@PathVariable("cId") int companyId){
        return tripService.findTrips(companyId);
    }
    /**--------------------------------------POST-MAPPINGS----------------------------------------*/

    @PostMapping("/create")
    int createCarrier(@RequestBody CreateCarrierRequest createCarrierRequest){
        System.out.println("hello!");
        try {
            return carrierService.createCarrier(createCarrierRequest);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    @PostMapping("/{cId}/trip")
    int addTrip(@RequestBody AddTripRequest addTripRequest,
                   @PathVariable("cId") int companyId){
//        if(companyId != addTripRequest.getCompanyId()) return -1;
        int id = tripService.create(addTripRequest,companyId);
        return id;
    }

    @PostMapping("/{cId}/trips")
    List<Integer> addTrips(@RequestBody List<AddTripRequest> tripAddList,
                           @PathVariable("cId") int companyId){
        return tripAddList.stream()
                            .map(trip -> tripService.create(trip,companyId))
                            .collect(Collectors.toList());
    }
    @PostMapping("/{cId}/plane")
    int addPlane(@RequestBody AddPlaneRequest addPlaneRequest,
                  @PathVariable("cId") int companyId){
        if(companyId != addPlaneRequest.getCompanyId()) return -1;
        int id = planeService.create(addPlaneRequest,companyId);
        return id;
    }
    /**--------------------------------------DELETE-MAPPINGS----------------------------------------*/
    @DeleteMapping("/{cId}/trip")
    String addTrip(@RequestParam("tId") int tripId,
                   @PathVariable("cId") int companyId){
        return tripService.tripRemoval(tripId,companyId);
    }
    @DeleteMapping("/{cId}/plane")
    void removePlane(@RequestParam("pId") int planeId,
                     @PathVariable("cId") int companyId){
        planeService.planeRemoval(planeId,companyId);
    }
}
