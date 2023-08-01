package com.example.FlieBees.DTOs;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarrierCreateRequest {
    private String companyName;
    private int companyId;
}
