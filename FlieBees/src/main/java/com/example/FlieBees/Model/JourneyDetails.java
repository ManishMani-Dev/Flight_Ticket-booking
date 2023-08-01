package com.example.FlieBees.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class JourneyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
