package com.example.AirLine.Dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaneDto {
    private int planeId;
    private String chassisNo;
    private int totalNoOfSeats;
    private int myCarrierId;
}
