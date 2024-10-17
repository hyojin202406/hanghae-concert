package com.hhplu.hhplusconcert.app.domain.concert;

import com.hhplu.hhplusconcert.app.domain.concert.entity.Seat;

import java.util.List;

public class ConcertManagement {

    // 좌석 목록의 총 가격을 계산하는 메서드
    public static long calculateTotalPrice(List<Seat> seats) {
        return seats.stream()
                .mapToLong(Seat::getSeatPrice)
                .sum();
    }

}
