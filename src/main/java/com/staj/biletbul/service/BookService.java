package com.staj.biletbul.service;

import com.staj.biletbul.entity.*;
import com.staj.biletbul.enums.EventStatus;
import com.staj.biletbul.enums.EventType;
import com.staj.biletbul.exception.*;
import com.staj.biletbul.repository.*;
import com.staj.biletbul.request.AddUserToEventRequest;
import com.staj.biletbul.response.TicketResponse;
import com.staj.biletbul.response.TicketTypeResponse;
import com.staj.biletbul.security.CustomUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;

    public BookService(EventRepository eventRepository,
                       UserRepository userRepository,
                       SeatRepository seatRepository,
                       TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.ticketTypeRepository = ticketTypeRepository;
    }

    @Transactional
    public TicketResponse addUserToEvent(CustomUserDetails userDetails,
                                         AddUserToEventRequest request) {

        Event event = eventRepository.findByTitle(request.eventTitle())
                .orElseThrow(() -> new EventNotFoundException
                        ("Event with title " + request.eventTitle() + " not found"));

        if (event.getEventStatus().equals(EventStatus.PENDING)
                || event.getEventStatus().equals(EventStatus.CANCELLED)) {
            throw new EventNotActiveException(
                    "Event with title " + request.eventTitle() + " is not active"
            );
        }

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException
                        ("User not found with email " + userDetails.getUsername()));

        //genel duzen icin bilet olusturma
        if (event.getEventType() == EventType.GENERAL_ADMISSION) {

            TicketType ticketType = ticketTypeRepository.findTicketTypeByEventAndName(event, request.ticketTypeName())
                    .orElseThrow(() -> new TicketTypeNotFoundException("Ticket type not found"));

            if (ticketType.getCapacity() > 0) {
                Ticket ticket = new Ticket();
                ticket.setTicketType(ticketType);
                ticket.setPrice(ticketType.getPrice());
                ticket.setEvent(event);
                ticket.setUser(user);

                ticketRepository.save(ticket);

                event.getUsers().add(user);
                ticketType.setCapacity(ticketType.getCapacity() - 1);
                eventRepository.save(event);

                TicketTypeResponse typeResponse = new TicketTypeResponse(
                        ticket.getId(),
                        ticket.getTicketType().getName(),
                        ticket.getPrice()
                );

                TicketResponse response = new TicketResponse();
                response.setFullName(user.getFullName());
                response.setPrice(ticketType.getPrice());
                response.setEventTitle(event.getTitle());
                response.setEventDescription(event.getDescription());
                response.setStartTime(event.getStartTime());
                response.setEndTime(event.getEndTime());
                response.setVenueName(event.getVenue().getName());
                response.setVenueAddress(event.getVenue().getAddress());
                response.setOrganizerName(event.getOrganizer().getOrganizerName());
                if (event.getArtist() != null) {
                    response.setArtistName(event.getArtist().getName());
                }
                response.setCityName(event.getCity().getName());
                response.setTicketType(typeResponse);

                return response;
            } else {
                throw new AllTicketsReservedException("All tickets reserved in : " + request.ticketTypeName());
            }
        } else {
            Seat seat = seatRepository.findByEventAndSeatNumber(event, request.seatNumber())
                    .orElseThrow(() -> new SeatNotFoundException("Seat not found with id : " + request.seatNumber()));

            TicketType ticketType = ticketTypeRepository.
                    findTicketTypeByEventAndName(event, request.ticketTypeName())
                    .orElseThrow(() -> new TicketTypeNotFoundException
                            ("Ticket type with name : " + request.ticketTypeName() + " not found"));

            //ticket type'da öyle bir koltuk var mı
            if (!seat.getTicketType().getName().equals(ticketType.getName())) {
                throw new SeatNotBelongsToTicketTypeException
                        ("Seat with number : " + request.seatNumber()
                                + " not belongs to ticket type : " + ticketType.getName());
            }

            //secilen koltuk musait mi
            if (seat.getUser() != null) {
                throw new SeatAlreadyTakenException("Seat already taken with number : " + seat.getSeatNumber());
            }

            //kullanıcıyı koltuga ekleme
            seat.setUser(user);

            //etkinlige kullaniciyi ekleme
            event.getUsers().add(user);

            //kapasiteyi azalt
            ticketType.setCapacity(ticketType.getCapacity() - 1);

            eventRepository.save(event);

            //ticket olustur
            Ticket ticket = new Ticket();
            ticket.setTicketType(ticketType);
            ticket.setPrice(seat.getSeatPrice());
            ticket.setEvent(event);
            ticket.setUser(user);
            ticket.setSeat(seat);

            ticketRepository.save(ticket);

            TicketTypeResponse typeResponse = new TicketTypeResponse(
                    ticket.getId(),
                    ticket.getTicketType().getName(),
                    ticket.getPrice()
            );

            TicketResponse response = new TicketResponse();
            response.setFullName(user.getFullName());
            response.setPrice(ticketType.getPrice());
            response.setEventTitle(event.getTitle());
            response.setEventDescription(event.getDescription());
            response.setStartTime(event.getStartTime());
            response.setEndTime(event.getEndTime());
            response.setVenueName(event.getVenue().getName());
            response.setVenueAddress(event.getVenue().getAddress());
            response.setOrganizerName(event.getOrganizer().getOrganizerName());
            response.setArtistName(event.getArtist().getName());
            response.setCityName(event.getCity().getName());
            response.setTicketType(typeResponse);
            response.setSeatNumber(seat.getSeatNumber());

            return response;
        }
    }

}
