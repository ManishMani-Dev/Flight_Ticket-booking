package com.example.FlieBees.Service;

import com.example.FlieBees.DTOs.TripDetailsDto;
import com.example.FlieBees.DTOs.TripFetchRequest;
import com.example.FlieBees.Model.MyCarriers;
import com.example.FlieBees.Repository.MyCarriersRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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

    private Logger logger = LoggerFactory.getLogger(FlightFetchService.class);
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
                                        List<TripDetailsDto> flights =  restTemplate.getForObject(url+"?src="+params.get("src")+"&dst="+params.get("dst")+"&stime="+params.get("stime")+"&etime="+fetchRequest.getEndTime() , List.class);
//                                            TripDetailsDto[] flights =webClientBuilder.build()
//                                                        .get().uri(url,params).retrieve().bodyToMono(TripDetailsDto[].class).block();
//                                        System.out.println(flights);
                                        trips.addAll(flights);
                                    });
        return  trips;
    }
    public List<TripDetailsDto> findTripsExServ(TripFetchRequest fetchRequest) {
        List<TripDetailsDto> trips = new ArrayList<TripDetailsDto>();
        List<MyCarriers> carriers = myCarriersRepo.findAll();

        Executor executor = Executors.newFixedThreadPool(3);

        carriers.stream().forEach(c->executor.execute(()->task(c.getCompanyId(),fetchRequest,trips)));
//        try {
//            Thread.sleep(5);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return trips;
    }

    private void task(int companyId,TripFetchRequest request,List<TripDetailsDto> trips){
        String url = airLinesURL+companyId+"/search?src="+request.getSource()+"&dst="+request.getDestination()+"&stime="+request.getStartTime()+"&etime="+request.getEndTime() ;
        logger.info("Inside task Thread ->{}, for company id ->{}",Thread.currentThread(),companyId);
        List<TripDetailsDto> flights =  restTemplate.getForObject(url, List.class);
        trips.addAll(flights);
    }
    public List<TripDetailsDto> findTripsAsync(TripFetchRequest fetchRequest) throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(3);
        List<TripDetailsDto> trips= (List<TripDetailsDto>) CompletableFuture.supplyAsync(()->myCarriersRepo.findAll(),executor)
                .thenApplyAsync(myCarriers -> {
                    return myCarriers.stream().map((c) -> airLinesURL + c.getCompanyId() + "/search?src="+fetchRequest.getSource()+"&dst="+fetchRequest.getDestination()+"&stime="+fetchRequest.getStartTime()+"&etime="+fetchRequest.getEndTime())
                            .map((url) -> {
                                logger.info("in api call-{}, thread{}",url,Thread.currentThread());
                                return restTemplate.getForObject(url, List.class);
                            })
//                            .flatMap(List::stream)
//                            .collect(Collectors.toList());
                            .reduce(new ArrayList<>(), (x, y) -> {
                                x.addAll(y);
                                return x;
                            });
                },executor).get();
//        CompletableFuture.supplyAsync(()->myCarriersRepo.findAll().stream().map(c->airLinesURL+c.getCompanyId()+"/search?src="+fetchRequest.getSource()+"&dst="+fetchRequest.getDestination()
//                +"&stime="+fetchRequest.getStartTime()+"&etime="+fetchRequest.getEndTime()))
//                .thenApplyAsync((url)->restTemplate.getForObject(url, List.class)).
        return trips;
    }

    }
