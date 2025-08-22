package com.staj.biletbul.entity;

import com.staj.biletbul.enums.SeatType;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "seats",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"event_id", "seat_number"}
        )
)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seatNumber;

    private BigDecimal seatPrice;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    public Seat() {
    }

    public Seat(Long id,
                String seatNumber,
                BigDecimal seatPrice,
                SeatType seatType,
                Event event,
                User user) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.seatPrice = seatPrice;
        this.seatType = seatType;
        this.event = event;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(BigDecimal seatPrice) {
        this.seatPrice = seatPrice;
    }
}
