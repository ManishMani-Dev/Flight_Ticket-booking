package com.example.AirLine.Dtos;

import com.example.AirLine.Model.Quality;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPlaneRequest {
    private int companyId;
    private String chassisNo;
    private int totalNoOfSeats;
    private Quality quality;
}
