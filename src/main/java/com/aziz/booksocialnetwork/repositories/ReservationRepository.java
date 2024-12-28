package com.aziz.booksocialnetwork.repositories;

import com.aziz.booksocialnetwork.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Page<Reservation> findAllByUser_Id(Pageable pageable,Long userId);
}
