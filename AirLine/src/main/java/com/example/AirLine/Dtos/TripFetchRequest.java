package com.example.AirLine.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripFetchRequest {
    private Integer carrierId;
    private String source;
    private String destination;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS",timezone="IST")
    private Timestamp startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS",timezone="IST")
    private Timestamp endTime;
}
