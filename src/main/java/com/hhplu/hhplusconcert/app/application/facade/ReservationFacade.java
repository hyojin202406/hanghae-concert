package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.service.reservation.dto.ReserveSeatsDto;
import com.hhplu.hhplusconcert.app.application.service.reservation.dto.ReserveSeatsResponseDto;
import com.hhplu.hhplusconcert.app.application.service.concert.service.ConcertService;
import com.hhplu.hhplusconcert.app.application.service.payment.service.PaymentService;
import com.hhplu.hhplusconcert.app.application.service.reservation.service.ReservationService;
import com.hhplu.hhplusconcert.app.application.service.concert.service.SeatService;
import com.hhplu.hhplusconcert.app.domain.concert.ConcertManagement;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Concert;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationFacade {
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final SeatService seatService;
    private final ConcertService concertService;

    @Transactional
    public ReserveSeatsResponseDto reserveSeats(ReserveSeatsDto command) {
        Concert concert = concertService.validateConcertExists(command.getConcertId());
        Reservation reservation = reservationService.createReservation(command.getUserId());
        List<Seat> seats = seatService.updateSeatStatus(reservation.getId(), command.getScheduleId(), command.getSeatIds());
        long sumPoint = ConcertManagement.calculateTotalPrice(seats);
        paymentService.createPendingPayment(reservation, sumPoint);
        return new ReserveSeatsResponseDto(reservation, concert, seats, sumPoint);
    }
}
