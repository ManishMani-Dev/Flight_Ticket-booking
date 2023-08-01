package com.example.AirLine.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tripId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS",timezone="IST")
    private Timestamp startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS",timezone="IST")
    private Timestamp endTime;
    private String source;
    private String destination;
    private int availableSeats;
    @ManyToOne(targetEntity = Carrier.class)
    @JoinColumn
    private Carrier myCarrier;
    @ManyToOne(targetEntity = Plane.class)
    @JoinColumn
    private Plane myPlane;
}
