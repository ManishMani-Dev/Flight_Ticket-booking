package com.example.AirLine.Dtos;

import com.example.AirLine.Model.TripDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddTripRequest {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS",timezone="IST")
    private Timestamp startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS",timezone="IST")
    private Timestamp endTime;
    private String source;
    private String destination;
    private int planeId;
//    private int companyId;

}
