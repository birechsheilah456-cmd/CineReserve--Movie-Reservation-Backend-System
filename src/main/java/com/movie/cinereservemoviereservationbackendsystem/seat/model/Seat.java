package com.movie.cinereservemoviereservationbackendsystem.seat.model;

import com.movie.cinereservemoviereservationbackendsystem.auditorium.model.Auditorium;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private String rowLabel;

    @Column(nullable = false)
    private Integer seatPosition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditorium_id", nullable = false)
    private Auditorium auditorium;
}