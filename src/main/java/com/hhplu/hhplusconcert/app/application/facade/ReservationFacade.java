package com.hhplu.hhplusconcert.app.application.facade;

import com.hhplu.hhplusconcert.app.application.reservation.command.ReserveSeatsCommand;
import com.hhplu.hhplusconcert.app.application.reservation.command.ReserveSeatsResponseCommand;
import com.hhplu.hhplusconcert.app.application.concert.service.ConcertService;
import com.hhplu.hhplusconcert.app.application.payment.service.PaymentService;
import com.hhplu.hhplusconcert.app.application.reservation.service.ReservationService;
import com.hhplu.hhplusconcert.app.application.concert.service.SeatService;
import com.hhplu.hhplusconcert.app.domain.concert.ConcertManagement;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Concert;
import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;
import com.hhplu.hhplusconcert.app.domain.reservation.entity.Reservation;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationFacade {
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final SeatService seatService;
    private final ConcertService concertService;

    /**
     * 좌석 예약 파사드 메서드
     * @param command
     * @return
     */
    @Transactional
    public ReserveSeatsResponseCommand reserveSeats(ReserveSeatsCommand command) {
        try {
            Concert concert = concertService.validateConcertExists(command.getConcertId());
            Reservation reservation = reservationService.createReservation(command.getUserId());
            List<Seat> seats = seatService.updateSeatStatus(reservation.getId(), command.getScheduleId(), command.getSeatIds());
            long sumPoint = ConcertManagement.calculateTotalPrice(seats);
            paymentService.createPendingPayment(reservation, sumPoint);
            return new ReserveSeatsResponseCommand(reservation, concert, seats, sumPoint);
        } catch (OptimisticLockException e) {
            throw new IllegalStateException("좌석 예약에 실패했습니다. 다시 시도해 주세요.");
        }
    }
}
