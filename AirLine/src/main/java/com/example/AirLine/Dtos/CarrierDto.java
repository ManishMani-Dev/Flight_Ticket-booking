package com.example.AirLine.Dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarrierDto {
    private int companyId;
    private String companyName;
}
