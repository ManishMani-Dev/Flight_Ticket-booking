package com.example.AirLine.Dtos;

import com.example.AirLine.Model.Carrier;
import com.example.AirLine.Model.Plane;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCarrierRequest {
    private String companyName;
    private int noOfPlanes;
    private List<AddPlaneRequest> myPlanes;
    public Carrier to(){
        return Carrier.builder()
                .companyName(this.companyName)
                .noOfPlanes(this.noOfPlanes)
                .build();
    }
}
