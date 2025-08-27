package com.staj.biletbul.service;

import com.staj.biletbul.entity.*;
import com.staj.biletbul.enums.EventType;
import com.staj.biletbul.exception.*;
import com.staj.biletbul.repository.EventRepository;
import com.staj.biletbul.repository.SeatRepository;
import com.staj.biletbul.repository.TicketRepository;
import com.staj.biletbul.repository.UserRepository;
import com.staj.biletbul.request.AddUserToEventRequest;
import com.staj.biletbul.response.TicketResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public BookService(EventRepository eventRepository,
                       UserRepository userRepository,
                       SeatRepository seatRepository,
                       TicketRepository ticketRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
    }

    //todo: event koltuklu mu ? koltuksuz mu ?
    @Transactional
    public TicketResponse addUserToEvent(AddUserToEventRequest request) {

        Event event = eventRepository.findByTitle(request.eventTitle())
                .orElseThrow(() -> new EventNotFoundException
                        ("Event with title " + request.eventTitle() + " not found"));

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException
                        ("User not found with email " + request.email()));

        Seat seat = seatRepository.findByEventAndSeatNumber(event, request.seatNumber())
                .orElseThrow(()-> new SeatNotFoundException("Seat not found with id : " + request.seatNumber()));

        //genel duzen icin bilet olusturma
        if (event.getEventType() == EventType.GENERAL_ADMISSION) {
            for (TicketType ticketType : event.getTicketTypes()) {
                if (ticketType.getName().equals(request.ticketType().getName())) {
                    if(ticketType.getCapacity() > 0) {
                        Ticket ticket = new Ticket();
                        ticket.setPrice(ticketType.getPrice());
                        ticket.setEvent(event);
                        ticket.setUser(user);

                        ticketRepository.save(ticket);

                        //todo: kapasite 0 olmussa dusurmemek gerek ?
                        ticketType.setCapacity(ticketType.getCapacity() - 1);

                        eventRepository.save(event);
                        return new TicketResponse(
                                user.getFullName(),
                                seat.getSeatPrice(),
                                event.getTitle(),
                                event.getDescription(),
                                event.getStartTime(),
                                event.getEndTime(),
                                event.getVenue().getName(),
                                event.getVenue().getAddress(),
                                event.getOrganizer().getOrganizerName(),
                                event.getArtist().getName(),
                                event.getCity().getName(),
                                seat.getSeatNumber(),
                                seat.getTicket().getTicketType()
                        );
                    } else {
                        throw new AllTicketsReservedException("All tickets reserved in : " + request.ticketType().getName());
                    }
                }
            }
        }

        //koltuklu etkinlikler icin
        if (event.getEventType() == EventType.SEATED) {

        }

        //secilen koltuk musait mi
        if (seat.getUser() != null) {
            throw new SeatAlreadyTakenException("Seat already taken with number : " + seat.getSeatNumber());
        }

        //kullanıcıyı koltuga ekleme
        seat.setUser(user);

        //etkinlige kullaniciyi ekleme
        event.getUsers().add(user);

        eventRepository.save(event);

        //ticket olustur
        Ticket ticket = new Ticket();
        ticket.setPrice(seat.getSeatPrice());
        ticket.setEvent(event);
        ticket.setUser(user);
        ticket.setSeat(seat);

        ticketRepository.save(ticket);

        return new TicketResponse(
                user.getFullName(),
                seat.getSeatPrice(),
                event.getTitle(),
                event.getDescription(),
                event.getStartTime(),
                event.getEndTime(),
                event.getVenue().getName(),
                event.getVenue().getAddress(),
                event.getOrganizer().getOrganizerName(),
                event.getArtist().getName(),
                event.getCity().getName(),
                seat.getSeatNumber(),
                seat.getTicket().getTicketType()
        );
    }

}
