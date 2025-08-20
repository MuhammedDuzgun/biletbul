package com.staj.biletbul.service;

import com.staj.biletbul.entity.*;
import com.staj.biletbul.enums.SeatType;
import com.staj.biletbul.exception.*;
import com.staj.biletbul.mapper.EventMapper;
import com.staj.biletbul.mapper.UserMapper;
import com.staj.biletbul.repository.*;
import com.staj.biletbul.request.AddUserToEventRequest;
import com.staj.biletbul.request.CreateEventRequest;
import com.staj.biletbul.response.AllUsersOfEventResponse;
import com.staj.biletbul.response.EventResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public EventService(EventRepository eventRepository,
                        OrganizerRepository organizerRepository,
                        UserRepository userRepository,
                        EventCategoryRepository eventCategoryRepository,
                        ArtistRepository artistRepository,
                        EventMapper eventMapper,
                        UserMapper userMapper,
                        CityRepository cityRepository) {
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.userRepository = userRepository;
        this.eventCategoryRepository = eventCategoryRepository;
        this.artistRepository = artistRepository;
        this.eventMapper = eventMapper;
        this.userMapper = userMapper;
        this.cityRepository = cityRepository;
    }

    public List<EventResponse> getAllEvents() {
        List<EventResponse> eventResponses = eventRepository.findAll()
                .stream()
                .map(eventMapper::mapToResponse)
                .toList();
        return eventResponses;
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

    @Transactional
    public EventResponse createEvent(CreateEventRequest request) {

        Organizer organizer = organizerRepository.findByEmail(request.organizerMail())
                        .orElseThrow(()-> new OrganizerNotFoundException
                                ("No organizer found with email: " + request.organizerMail()));

        EventCategory eventCategory = eventCategoryRepository.findByCategoryName
                (request.eventCategoryName())
                        .orElseThrow(()-> new EventCategoryNotFoundException
                                        ("Event category not found with name: " +
                                                request.eventCategoryName()));

        Artist artist = artistRepository.findByName(request.artistName())
                .orElseThrow(()-> new ArtistNotFoundException
                        ("Artist not found with name : " + request.artistName()));

        City city = cityRepository.findByName(request.cityName())
                .orElseThrow(()-> new CityNotFoundException("city with name : " + request.cityName() + "not found"));

        if (eventRepository.findByTitle(request.title()).isPresent()) {
            throw new EventAlreadyExistsException("Event already exists with title: " + request.title());
        }

        Event event = eventMapper.mapToEntity(request);

        event.setOrganizer(organizer);
        event.setEventCategory(eventCategory);
        event.setArtist(artist);
        event.setCity(city);

        Event savedEvent = eventRepository.save(event);

        return eventMapper.mapToResponse(savedEvent);
    }

    @Transactional
    public ResourceDeletedResponse deleteEvent(Long id) {
        Event eventToDelete = eventRepository.findById(id)
                        .orElseThrow(()-> new EventNotFoundException("No event found with id: " + id));
        eventRepository.delete(eventToDelete);
        return new ResourceDeletedResponse("Event with id: " + id + " deleted successfully.");
    }

    @Transactional
    public EventResponse addUserToEvent(Long eventId, AddUserToEventRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()-> new EventNotFoundException("No event found with id: " + eventId));

        User user = userRepository.findByEmail(request.email())
                        .orElseThrow(() -> new UserNotFoundException
                                ("User not found with email " + request.email()));

        //kullanıcı daha once event'e kaydolmus mu
        if(event.getUsers().contains(user)) {
            throw new AlreadyRegisteredEventException("Bu etkinliğe daha önce kaydolmuşsunuz");
        }

        //musait koltuk var mı
        if (request.seatType().equals(SeatType.STANDARD)) {
            if(!(event.getStandardSeats() > 0)) {
                throw new AllStandardSeatsReservedException("Standard koltuklar tükendi");
            }
        } else if (request.seatType().equals(SeatType.VIP)) {
            if(!(event.getVipSeats() > 0)) {
                throw new AllVipSeatsReservedException("Vip koltuklar tükendi");
            }
        }

        //etkinlige kullaniciyi ekleme
        event.getUsers().add(user);

        //koltuk azaltma
        if (request.seatType() == SeatType.STANDARD) {
            event.setStandardSeats(event.getStandardSeats() - 1);
            if(event.getStandardSeats() == 0) {
                event.setAllStandardSeatsReserved(true);
            }
        } else if (request.seatType() == SeatType.VIP) {
            event.setVipSeats(event.getVipSeats() - 1);
            if(event.getVipSeats() == 0) {
                event.setAllVipSeatsReserved(true);
            }
        }

        eventRepository.save(event);

        return eventMapper.mapToResponse(event);
    }

}
