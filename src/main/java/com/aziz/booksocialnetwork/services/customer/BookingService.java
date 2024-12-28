package com.aziz.booksocialnetwork.services.customer;

import com.aziz.booksocialnetwork.dto.ReservationDto;
import com.aziz.booksocialnetwork.dto.ReservationResponseDto;

public interface BookingService {
    public boolean postReservation(ReservationDto reservationDto );
    public ReservationResponseDto getAllReservations(int pageNumber);
    public boolean changeReservationStatus(Long id,String status);
    public  ReservationResponseDto getAllReservationsByUserId(Long userId,int pageNumber);
}
