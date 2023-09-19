package com.example.FlieBees.Controller;

import com.example.FlieBees.DTOs.TripDetailsDto;
import com.example.FlieBees.DTOs.TripFetchRequest;
import com.example.FlieBees.Service.FlightFetchService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightFetchService flightFetchService;
    private Logger logger = LoggerFactory.getLogger(FlightFetchService.class);
    @GetMapping("/search")
    List<TripDetailsDto> searchFlights(@RequestParam("src") String source,
                                       @RequestParam("dst") String destination,
                                       @RequestParam(name="stime",required=false) @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS",timezone="IST") Timestamp startTime,
                                       @RequestParam(name="etime",required=false) @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS",timezone="IST")Timestamp endTime) throws ExecutionException, InterruptedException {
        TripFetchRequest tripFetch= TripFetchRequest.builder()
                .source(source)
                .destination(destination)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        long s = System.currentTimeMillis();
        List<TripDetailsDto> res = flightFetchService.findTrips(tripFetch);
        logger.info("normal call time taken {}",(System.currentTimeMillis()-s));
//        s=System.currentTimeMillis();
//        res = flightFetchService.findTripsExServ(tripFetch); /**ExecutorService**/
//        logger.info("Executor Service call time taken {}",(System.currentTimeMillis()-s));
        s=System.currentTimeMillis();
        res.addAll( flightFetchService.findTripsAsync(tripFetch)); /**Async**/
        logger.info("Completable future Service call time taken {}",(System.currentTimeMillis()-s));
        return res;
    }

}
