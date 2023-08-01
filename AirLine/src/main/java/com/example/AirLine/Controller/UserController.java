package com.example.AirLine.Controller;

import com.example.AirLine.Dtos.TripDetailsDTO;
import com.example.AirLine.Dtos.TripFetchRequest;
import com.example.AirLine.Service.TripService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/air-lines")
public class UserController {
    //TODO use swagger to do beautiful api documentation YT link "https://youtu.be/A-lqrhhMEYY"
    @Autowired
    TripService tripService;
    @GetMapping("/hello")
    String hi(){
        return "Hi, You are in airlines API";
    }
    @GetMapping("/{cId}/search")
    List<TripDetailsDTO> searchFlights(@PathVariable("cId") Integer carrierId,
                                       @RequestParam("src") String source,
                                       @RequestParam("dst") String destination,
                                       @RequestParam(name="stime",required=false) @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS",timezone="IST") Timestamp startTime,
                                       @RequestParam(name="etime",required=false) @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS",timezone="IST")Timestamp endTime){
        TripFetchRequest tripFetch = TripFetchRequest.builder()
                                                    .carrierId(carrierId)
                                                    .source(source)
                                                    .destination(destination)
                                                    .startTime(startTime)
                                                    .endTime(endTime)
                                                    .build();
        List<TripDetailsDTO> res = tripService.findTrips(tripFetch);
        return res;
    }


}
