package com.example.FlieBees.Service;

import com.example.FlieBees.DTOs.CarrierCreateRequest;
import com.example.FlieBees.Model.MyCarriers;
import com.example.FlieBees.Repository.MyCarriersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    MyCarriersRepo myCarriersRepo;
    public int addingCarrier(CarrierCreateRequest carrierCreateRequest){
        MyCarriers c =myCarriersRepo.save(MyCarriers.builder()
                                        .companyId(carrierCreateRequest.getCompanyId())
                                        .companyName(carrierCreateRequest.getCompanyName())
                                        .build());
        return c.getId();
    }
}
