package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.event.reservation.ReservationSuccessEvent;
import com.hhplu.hhplusconcert.app.application.service.concert.service.ConcertService;
import com.hhplu.hhplusconcert.app.application.service.concert.service.SeatService;
import com.hhplu.hhplusconcert.app.application.service.payment.PaymentService;
import com.hhplu.hhplusconcert.app.application.service.reservation.dto.ReserveSeatsDto;
import com.hhplu.hhplusconcert.app.application.service.reservation.dto.ReserveSeatsResponseDto;
import com.hhplu.hhplusconcert.app.application.service.reservation.ReservationService;
import com.hhplu.hhplusconcert.app.application.event.reservation.ReservationEventPublisher;
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
    private final ReservationEventPublisher reservationEventPublisher;

    @Transactional
    public ReserveSeatsResponseDto reserveSeats(ReserveSeatsDto command) {
        Concert concert = concertService.validateConcertExists(command.getConcertId());
        Reservation reservation = reservationService.createReservation(command.getUserId());

        List<Seat> seats = seatService.updateSeatStatus(reservation.getId(), command.getScheduleId(), command.getSeatIds());
        long totalPoint = seatService.calculateTotalPoint(seats);

        paymentService.createPendingPayment(reservation, totalPoint);

        reservationEventPublisher.success(new ReservationSuccessEvent(reservation.getId(), reservation.getPaymentId()));

        return new ReserveSeatsResponseDto(reservation, concert, seats, totalPoint);
    }
}
