package com.movie.cinereservemoviereservationbackendsystem.auth.enums;

/**
 * The three roles required by the brief.
 *
 * Endpoint access matrix (documented here so it lives next to the source
 * of truth, and duplicated in README.md for reviewers):
 *
 *   ADMINISTRATOR:
 *     - Full access: user management (create/update roles), system reports,
 *       system information management, and overall system configuration.
 *
 *   CINEMA_MANAGER:
 *     - Movies: full access (create, update, delete, view).
 *     - Showtimes: full access (schedule, update, manage showtimes).
 *     - Cinema Information: manage theater layouts, halls, and cinema details.
 *     - Reservation Information: view all customer reservations and booking analytics.
 *     - Cannot manage users.
 *
 *   CUSTOMER:
 *     - Account: register and login.
 *     - Movies & Showtimes: browse active movies and view available showtimes (read-only).
 *     - Reservations: reserve seats, view own reservations, and cancel eligible reservations.
 *     - Administrative endpoints: no access.
 */
public enum Role {
    ADMINISTRATOR,
    CINEMA_MANAGER,
    CUSTOMER
}