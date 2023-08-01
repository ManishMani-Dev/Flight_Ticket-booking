package com.example.AirLine.Model;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planeId;
    private String chassisNo;
    private int totalNoOfSeats;
    private Quality quality;
    @ManyToOne(targetEntity = Carrier.class)
    @JoinColumn
    private Carrier myCarrier;
    @OneToMany(mappedBy = "myPlane")
    private List<TripDetails> myTrips;

}
