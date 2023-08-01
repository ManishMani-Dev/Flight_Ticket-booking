package com.example.FlieBees.Service;

import com.example.FlieBees.DTOs.TripDetailsDto;
import com.example.FlieBees.DTOs.TripFetchRequest;
import com.example.FlieBees.Model.MyCarriers;
import com.example.FlieBees.Repository.MyCarriersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class FlightFetchService {
    @Autowired
    private MyCarriersRepo myCarriersRepo;
    @Autowired  /*********Synchronized  execution ***/
    private RestTemplate restTemplate;
    @Autowired
    private WebClient.Builder webClientBuilder;
    private String airLinesURL = "http://localhost:8081/air-lines/";
    public List<TripDetailsDto> findTrips(TripFetchRequest fetchRequest){
        List<TripDetailsDto> trips = new ArrayList<TripDetailsDto>();
        List<MyCarriers> carriers = myCarriersRepo.findAll();
        Map<String, String> params = new HashMap<String, String>();
        params.put("src", fetchRequest.getSource());
        params.put("dst", fetchRequest.getDestination());
        params.put("stime", fetchRequest.getStartTime().toString());
        params.put("etime", fetchRequest.getEndTime().toString());
        /**
         * make api calls for every air lines (http://localhost:8081/air-lines/{cId}/Search)
         * add it to a list of flights
         *
         //                                        HttpHeaders headers = new HttpHeaders();
         //                                        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
         //                                        HttpEntity<TripDetailsDto[]> entity = new HttpEntity<TripDetailsDto[]>(headers);
         //                                        ResponseEntity<TripDetailsDto[]> response = restTemplate.exchange(
         //                                                airLinesURL + c.getCompanyId() + "/search", HttpMethod.GET, entity, TripDetailsDto[].class,params);
         //                                        List<TripDetailsDto> flights = Arrays.asList(response.getBody());
         */

        carriers.stream().forEach(c->{
                                        String url = airLinesURL+c.getCompanyId()+"/search";
                                        List<TripDetailsDto> flights =  restTemplate.getForObject(url,List.class,params);
//                                            TripDetailsDto[] flights =webClientBuilder.build()
//                                                        .get().uri(url,params).retrieve().bodyToMono(TripDetailsDto[].class).block();
                                        System.out.println(flights);
                                        trips.addAll(flights);
                                    });
        return  trips;
    }
    public List<TripDetailsDto> findTripsAsync(TripFetchRequest fetchRequest){
        Map<String, String> params = new HashMap<String, String>();
        params.put("src", fetchRequest.getSource());
        params.put("dst", fetchRequest.getDestination());
        params.put("stime", fetchRequest.getStartTime().toString());
        params.put("etime", fetchRequest.getEndTime().toString());

        Executor executor = Executors.newFixedThreadPool(3);
        List<TripDetailsDto> trips= (List<TripDetailsDto>) CompletableFuture.supplyAsync(()->myCarriersRepo.findAll(),executor)
                .thenApplyAsync(myCarriers -> {
                    return myCarriers.stream().map((c) -> airLinesURL + c.getCompanyId() + "/search")
                            .map((url) -> restTemplate.getForObject(url, List.class, params))
//                            .flatMap(List::stream)
//                            .collect(Collectors.toList());
                            .reduce(new ArrayList<>(), (x, y) -> {
                                x.addAll(y);
                                return x;
                            });
                },executor);
        return trips;
    }

    }