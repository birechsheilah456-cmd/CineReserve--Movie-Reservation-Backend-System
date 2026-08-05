package com.movie.cinereservemoviereservationbackendsystem.auditorium.model;

import com.movie.cinereservemoviereservationbackendsystem.cinema.model.Cinema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "auditoriums")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auditorium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // e.g., "Screen 1", "IMAX Hall"

    @Column(nullable = false)
    private Integer capacity; // total number of seats

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;
}