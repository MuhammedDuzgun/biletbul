package com.staj.biletbul.service;

import com.staj.biletbul.entity.*;
import com.staj.biletbul.enums.EventStatus;
import com.staj.biletbul.enums.EventType;
import com.staj.biletbul.exception.*;
import com.staj.biletbul.mapper.EventMapper;
import com.staj.biletbul.mapper.SeatMapper;
import com.staj.biletbul.mapper.UserMapper;
import com.staj.biletbul.repository.*;
import com.staj.biletbul.request.CreateEventRequest;
import com.staj.biletbul.request.UpdateEventStatusRequest;
import com.staj.biletbul.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final UserRepository userRepository;
    private final EventCategoryRepository eventCategoryRepository;
    private final ArtistRepository artistRepository;
    private final TicketTypeRepository ticketTypeRepository;
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
                        TicketTypeRepository ticketTypeRepository,
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
        this.ticketTypeRepository = ticketTypeRepository;
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

    //get all active events
    public Page<EventResponse> getAllEventsByEventStatus(Pageable pageable,
                                                         EventStatus eventStatus) {
        return eventRepository.findAllByEndTimeAfterAndEventStatus(pageable,LocalDateTime.now(),eventStatus)
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

        City city = cityRepository.findByName(request.cityName())
                .orElseThrow(()-> new CityNotFoundException("city with venueName : " + request.cityName() + " not found"));

        Venue venue = venueRepository.findByName(request.venueName())
                .orElseThrow(()-> new VenueNotFoundException("venue with venueName : " + request.venueName() + " not found"));

        Artist artist = null;
        if (request.artistName() != null && !request.artistName().isEmpty()) {
            artist = artistRepository.findByName(request.artistName())
                    .orElseThrow(()-> new ArtistNotFoundException
                            ("Artist not found with venueName : " + request.artistName()));
        }

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

            //todo: aynı saatte aynı mekanda event post edilebiliyor ?
            if (request.startTime().isBefore(eventEndTime) && request.endTime().isAfter(eventStartTime)) {
                throw new EventTimeConflictException("another event already exists with" +
                        " start time: " + request.startTime() + " and end time: " + request.endTime()
                        + " at venue : " + request.venueName()
                );
            }
        }

        Event event = eventMapper.mapToEntity(request);

        //ticket type'ları evente ekle
        List<TicketType> ticketTypes = request.ticketTypes();
        for (TicketType ticketType : ticketTypes) {
            ticketType.setEvent(event);
        }

        event.setTicketTypes(ticketTypes);
        event.setEventStatus(EventStatus.PENDING);
        event.setOrganizer(organizer);
        event.setEventCategory(eventCategory);
        event.setArtist(artist);
        event.setCity(city);
        event.setVenue(venue);

        Event savedEvent = eventRepository.save(event);

        return eventMapper.mapToResponse(savedEvent);
    }

    @Transactional
    public EventResponse updateEventStatus(Long id,
                                           UpdateEventStatusRequest request) {

        Event event = eventRepository.findById(id)
                .orElseThrow(()-> new EventNotFoundException("No event found with id: " + id));

        //event'in kabul edilmesi durumu
        if (request.eventStatus().equals(EventStatus.CONFIRMED)) {
            //koltuklu düzen
            if(event.getEventType().equals(EventType.SEATED)) {
                List<TicketType> ticketTypes = event.getTicketTypes();
                for (TicketType ticketType : ticketTypes) {
                    for (int i=1; i<ticketType.getCapacity() + 1; i++) {
                        Seat seat = new Seat();
                        seat.setSeatNumber(ticketType.getName() + "-" + i);
                        seat.setSeatPrice(ticketType.getPrice());
                        seat.setTicketType(ticketType);
                        seat.setEvent(event);
                        seatRepository.save(seat);
                    }
                }
            }
            event.setEventStatus(request.eventStatus());
        }

        //event'in iptal edilmesi durumu
        if (request.eventStatus().equals(EventStatus.CANCELLED)) {
            event.setEventStatus(request.eventStatus());
        }

        //todo: bu asamada organizer'a mail gidebilir

        return eventMapper.mapToResponse(event);
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

        //ticket_type'ları sil
        eventToDelete.getTicketTypes().clear();

        //event'i sil
        eventRepository.deleteEventById(id);

        return new ResourceDeletedResponse("Event with id: " + id + " deleted successfully.");
    }

}
