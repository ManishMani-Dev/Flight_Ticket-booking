package com.example.AirLine.Model;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyId;
    @NotBlank
    private String companyName;
    private int noOfPlanes;
    private int costPerTrip;
    @OneToMany(mappedBy = "myCarrier")
    private List<Plane> myFlights;
    @OneToMany(mappedBy = "myCarrier")
    private List<TripDetails> myTripDetails;

}
