package com.staj.biletbul.entity;

import com.staj.biletbul.enums.SeatType;
import jakarta.persistence.*;

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
                SeatType seatType,
                Event event,
                User user) {
        this.id = id;
        this.seatNumber = seatNumber;
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
}
