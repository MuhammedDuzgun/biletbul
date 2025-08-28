package com.staj.biletbul.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tickets",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"seat_id", "event_id"}
        )
)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_type_id", nullable = false)
    private TicketType ticketType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @OneToOne(
            optional = false,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "seat_id",
            nullable = true
    )
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Ticket() {
    }

    public Ticket(Long id,
                  BigDecimal price,
                  TicketType ticketType,
                  Event event,
                  Seat seat,
                  User user) {
        this.id = id;
        this.price = price;
        this.ticketType = ticketType;
        this.event = event;
        this.seat = seat;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}
