package com.example.FlieBees.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripFetchRequest {
    @JsonProperty(value="src")
    private String source;
    private String destination;
    private Timestamp startTime;
    private Timestamp endTime;
}
