package com.staj.biletbul.service;

import com.staj.biletbul.entity.*;
import com.staj.biletbul.enums.SeatType;
import com.staj.biletbul.exception.*;
import com.staj.biletbul.mapper.EventMapper;
import com.staj.biletbul.mapper.SeatMapper;
import com.staj.biletbul.mapper.UserMapper;
import com.staj.biletbul.repository.*;
import com.staj.biletbul.request.AddUserToEventRequest;
import com.staj.biletbul.request.CreateEventRequest;
import com.staj.biletbul.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final UserRepository userRepository;
    private final EventCategoryRepository eventCategoryRepository;
    private final ArtistRepository artistRepository;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final CityRepository cityRepository;
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    private final VenueRepository venueRepository;
    private final TicketRepository ticketRepository;

    public EventService(EventRepository eventRepository,
                        OrganizerRepository organizerRepository,
                        UserRepository userRepository,
                        EventCategoryRepository eventCategoryRepository,
                        ArtistRepository artistRepository,
                        EventMapper eventMapper,
                        UserMapper userMapper,
                        CityRepository cityRepository,
                        SeatRepository seatRepository,
                        SeatMapper seatMapper,
                        VenueRepository venueRepository,
                        TicketRepository ticketRepository) {
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.userRepository = userRepository;
        this.eventCategoryRepository = eventCategoryRepository;
        this.artistRepository = artistRepository;
        this.eventMapper = eventMapper;
        this.userMapper = userMapper;
        this.cityRepository = cityRepository;
        this.seatRepository = seatRepository;
        this.seatMapper = seatMapper;
        this.venueRepository = venueRepository;
        this.ticketRepository = ticketRepository;
    }

    public Page<EventResponse> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(eventMapper::mapToResponse);
    }

    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(()-> new EventNotFoundException("No event found with id: " + id));
        return eventMapper.mapToResponse(event);
    }

    public AllUsersOfEventResponse getAllUserOfEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(()-> new EventNotFoundException("No event found with id: " + id));

        List<UserResponse> userResponses = event.getUsers()
                .stream()
                .map(userMapper::mapToUserResponse)
                .toList();

        AllUsersOfEventResponse response = new AllUsersOfEventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStandardSeats(),
                event.getVipSeats(),
                event.isAllStandardSeatsReserved(),
                event.isAllVipSeatsReserved(),
                event.getStandardSeatPrice(),
                event.getVipSeatPrice(),
                event.getStartTime(),
                event.getEndTime(),
                event.getOrganizer().getOrganizerName(),
                event.getEventCategory().getCategoryName(),
                userResponses
        );
        return response;
    }

    //all seats of event
    public List<SeatResponse> getAllSeatsOfEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(()-> new EventNotFoundException("No event found with id: " + id));

        List<SeatResponse> seats = event.getSeats()
                .stream()
                .map(seatMapper::mapToSeatResponse)
                .toList();

        return seats;
    }

    //all available seats of event
    public List<SeatResponse> getAllAvailableSeatsOfEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(()-> new EventNotFoundException("No event found with id: " + id));

        List<SeatResponse> seats = event.getSeats()
                .stream()
                .map(seatMapper::mapToSeatResponse)
                .filter(SeatResponse::isAvailable)
                .toList();

        return seats;
    }

    @Transactional
    public EventResponse createEvent(CreateEventRequest request) {

        Organizer organizer = organizerRepository.findByEmail(request.organizerMail())
                        .orElseThrow(()-> new OrganizerNotFoundException
                                ("No organizer found with email: " + request.organizerMail()));

        EventCategory eventCategory = eventCategoryRepository.findByCategoryName
                (request.eventCategoryName())
                        .orElseThrow(()-> new EventCategoryNotFoundException
                                        ("Event category not found with venueName: " +
                                                request.eventCategoryName()));

        Artist artist = artistRepository.findByName(request.artistName())
                .orElseThrow(()-> new ArtistNotFoundException
                        ("Artist not found with venueName : " + request.artistName()));

        City city = cityRepository.findByName(request.cityName())
                .orElseThrow(()-> new CityNotFoundException("city with venueName : " + request.cityName() + " not found"));

        Venue venue = venueRepository.findByName(request.venueName())
                .orElseThrow(()-> new VenueNotFoundException("venue with venueName : " + request.venueName() + " not found"));

        //venue, city'e ait mi
        if (!venue.getCity().equals(city)) {
            throw new VenueNotBelongsToCityException("venueName : " + request.venueName() + " is not belong to city");
        }

        //event title kontrolü
        if (eventRepository.findByTitle(request.title()).isPresent()) {
            throw new EventAlreadyExistsException("Event already exists with title: " + request.title());
        }

        //saat uygun mu
        for (Event event : venue.getEventList()) {
            LocalDateTime eventStartTime = event.getStartTime();
            LocalDateTime eventEndTime = event.getEndTime();

            if (request.startTime().isBefore(eventEndTime) && request.endTime().isAfter(eventStartTime)) {
                throw new EventTimeConflictException("another event already exists with" +
                        " start time: " + request.startTime() + " and end time: " + request.endTime()
                        + " at venue : " + request.venueName()
                );
            }
        }


        Event event = eventMapper.mapToEntity(request);

        event.setOrganizer(organizer);
        event.setEventCategory(eventCategory);
        event.setArtist(artist);
        event.setCity(city);
        event.setVenue(venue);

        Event savedEvent = eventRepository.save(event);

        //koltuklar
        List<Seat> seats = new ArrayList<>();

        //standart koltuklar
        for (int i=1; i<request.standardSeats() + 1; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber("S" + i);
            seat.setSeatPrice(savedEvent.getStandardSeatPrice());
            seat.setSeatType(SeatType.STANDARD);
            seat.setEvent(savedEvent);
            seats.add(seat);
        }

        //vip koltuklar
        for (int i=1; i<request.vipSeats() + 1; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber("V" + i);
            seat.setSeatPrice(savedEvent.getVipSeatPrice());
            seat.setSeatType(SeatType.VIP);
            seat.setEvent(savedEvent);
            seats.add(seat);
        }

        //koltukları ekle
        seatRepository.saveAll(seats);

        return eventMapper.mapToResponse(savedEvent);
    }

    @Transactional
    public ResourceDeletedResponse deleteEvent(Long id) {
        Event eventToDelete = eventRepository.findById(id)
                        .orElseThrow(()-> new EventNotFoundException("No event found with id: " + id));

        //event_users tablosundan sil
        eventRepository.deleteEventUsers(id);

        //event'in biletlerini sil
        ticketRepository.deleteTicketsByEventId(id);

        //koltukları sil
        seatRepository.deleteSeatsByEventId(id);

        //event'i sil
        eventRepository.deleteEventById(id);

        return new ResourceDeletedResponse("Event with id: " + id + " deleted successfully.");
    }

    @Transactional
    public TicketResponse addUserToEvent(Long eventId,
                                         AddUserToEventRequest request) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(()-> new EventNotFoundException("No event found with id: " + eventId));

        User user = userRepository.findByEmail(request.email())
                        .orElseThrow(() -> new UserNotFoundException
                                ("User not found with email " + request.email()));

        Seat seat = seatRepository.findByEventAndSeatNumber(event, request.seatNumber())
                .orElseThrow(()-> new SeatNotFoundException("Seat not found with id : " + request.seatNumber()));

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
                seat.getSeatType()
        );
    }

}
