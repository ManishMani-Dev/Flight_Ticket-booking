package com.example.FlieBees.Controller;

import com.example.FlieBees.DTOs.TripDetailsDto;
import com.example.FlieBees.DTOs.TripFetchRequest;
import com.example.FlieBees.Service.FlightFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightFetchService flightFetchService;
    //TODO change return type
    @GetMapping("/search")
    List<TripDetailsDto> searchFlights(@RequestParam("src") String source,
                                       @RequestParam("dst") String destination,
                                       @RequestParam(name="stime",required=false) Timestamp startTime,
                                       @RequestParam(name="etime",required=false)Timestamp endTime){
        TripFetchRequest tripFetch= TripFetchRequest.builder()
                .source(source)
                .destination(destination)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        List<TripDetailsDto> res = flightFetchService.findTrips(tripFetch);
//        List<TripDetailsDto> res = flightFetchService.findTripsAsync(tripFetch); /**Async**/
        res.add(TripDetailsDto.builder()
                .tripId(5555).build());
        return res;
    }

}
