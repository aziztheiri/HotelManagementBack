package com.aziz.booksocialnetwork.services.customer;

import com.aziz.booksocialnetwork.dto.ReservationDto;
import com.aziz.booksocialnetwork.dto.ReservationResponseDto;
import com.aziz.booksocialnetwork.entities.Reservation;
import com.aziz.booksocialnetwork.entities.Room;
import com.aziz.booksocialnetwork.entities.User;
import com.aziz.booksocialnetwork.enums.ReservationStatus;
import com.aziz.booksocialnetwork.repositories.ReservationRepository;
import com.aziz.booksocialnetwork.repositories.RoomRepository;
import com.aziz.booksocialnetwork.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    public static final  int SEARCH_RESULT_PER_PAGE = 4 ;
    public boolean postReservation(ReservationDto reservationDto ){
        Optional<User> optionalUser = userRepository.findById(reservationDto.getUserId());
        Optional<Room> optionalRoom = roomRepository.findById(reservationDto.getRoomId());
        if(optionalRoom.isPresent() && optionalUser.isPresent()){
            Reservation reservation = new Reservation();
            reservation.setRoom(optionalRoom.get());
            reservation.setUser(optionalUser.get());
            reservation.setCheckInDate(reservationDto.getCheckInDate());
            reservation.setCheckOutDate(reservationDto.getCheckOutDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);
            Long days = ChronoUnit.DAYS.between(reservationDto.getCheckInDate(),reservationDto.getCheckOutDate());
            reservation.setPrice(optionalRoom.get().getPrice()*days);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }
    public ReservationResponseDto getAllReservations(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,SEARCH_RESULT_PER_PAGE);
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);
        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(
                 Reservation::getReservationDto
        ).collect(Collectors.toList()));
        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());
        return reservationResponseDto;
    }
    public boolean changeReservationStatus(Long id,String status){
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if(optionalReservation.isPresent()){
            Reservation existingReservation = optionalReservation.get();
            Room existingroom = existingReservation.getRoom();
            if (Objects.equals(status, "Approve")) {
                existingReservation.setReservationStatus(ReservationStatus.APPROVED);
                existingroom.setAvailable(false);
            }else{
                existingReservation.setReservationStatus(ReservationStatus.REJECTED);
                existingroom.setAvailable(true);
            }
            reservationRepository.save(existingReservation);


            roomRepository.save(existingroom);
            return true;
        }
        return false;
    }
    public  ReservationResponseDto getAllReservationsByUserId(Long userId,int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,SEARCH_RESULT_PER_PAGE);
        Page<Reservation> reservationPage = reservationRepository.findAllByUser_Id(pageable,userId);
        reservationPage.get().forEach(System.out::println);
        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(
                Reservation::getReservationDto
        ).collect(Collectors.toList()));
        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());
        return reservationResponseDto;
    }
}
