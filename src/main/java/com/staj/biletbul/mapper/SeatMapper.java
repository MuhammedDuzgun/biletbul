package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.Seat;
import com.staj.biletbul.response.SeatResponse;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public SeatResponse mapToSeatResponse(Seat seat) {

        boolean isSeatAvailable;
        if (seat.getUser() == null) {
            isSeatAvailable = true;
        } else {
            isSeatAvailable = false;
        }

        SeatResponse response = new SeatResponse(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getSeatPrice(),
                seat.getSeatType(),
                seat.getEvent().getTitle(),
                isSeatAvailable
        );

        return response;
    }

}
