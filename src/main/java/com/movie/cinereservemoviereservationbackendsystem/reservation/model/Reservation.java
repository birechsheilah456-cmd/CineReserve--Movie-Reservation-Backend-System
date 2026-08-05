package com.movie.cinereservemoviereservationbackendsystem.reservation.model;

import com.movie.cinereservemoviereservationbackendsystem.auth.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // Tied to the logged-in user

    @Column(nullable = false)
    private Long showtimeId; // Placeholder until showtime module is built

    @Column(nullable = false, unique = true)
    private String idempotencyKey; // Prevents double-booking on retries

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private LocalDateTime reservationTime;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationSeat> reservationSeats;

    @PrePersist
    public void prePersist() {
        if (reservationTime == null) {
            reservationTime = LocalDateTime.now();
        }
        if (status == null) {
            status = ReservationStatus.PENDING;
        }
    }
}