package com.example.FlieBees.Controller;

import com.example.FlieBees.DTOs.CarrierCreateRequest;
import com.example.FlieBees.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @PostMapping("/carrier")
    int  addCarrier(@RequestBody CarrierCreateRequest carrierCreateRequest){
        return adminService.addingCarrier(carrierCreateRequest);
    }
}
