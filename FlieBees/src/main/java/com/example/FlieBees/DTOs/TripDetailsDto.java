package com.example.FlieBees.DTOs;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripDetailsDto {
    private int tripId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String source;
    private String destination;
    private int availableSeats;
}
