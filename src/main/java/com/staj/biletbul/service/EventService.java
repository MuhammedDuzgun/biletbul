package com.staj.biletbul.service;

import com.staj.biletbul.entity.Event;
import com.staj.biletbul.entity.EventCategory;
import com.staj.biletbul.entity.Organizer;
import com.staj.biletbul.entity.User;
import com.staj.biletbul.enums.SeatType;
import com.staj.biletbul.exception.*;
import com.staj.biletbul.mapper.EventMapper;
import com.staj.biletbul.repository.EventCategoryRepository;
import com.staj.biletbul.repository.EventRepository;
import com.staj.biletbul.repository.OrganizerRepository;
import com.staj.biletbul.repository.UserRepository;
import com.staj.biletbul.request.AddUserToEventRequest;
import com.staj.biletbul.request.CreateEventRequest;
import com.staj.biletbul.response.EventResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final UserRepository userRepository;
    private final EventCategoryRepository eventCategoryRepository;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository,
                        OrganizerRepository organizerRepository,
                        UserRepository userRepository,
                        EventCategoryRepository eventCategoryRepository,
                        EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.userRepository = userRepository;
        this.eventCategoryRepository = eventCategoryRepository;
        this.eventMapper = eventMapper;

    }

    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventResponse> eventResponses = events.stream().map(
                eventMapper::mapToResponse
        ).toList();
        return eventResponses;
    }

    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(()-> new EventNotFoundException("No event found with id: " + id));
        return eventMapper.mapToResponse(event);
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

        Event event = eventMapper.mapToEntity(request);

        event.setOrganizer(organizer);
        event.setEventCategory(eventCategory);

        Event savedEvent = eventRepository.save(event);

        return eventMapper.mapToResponse(savedEvent);
    }

    @Transactional
    public void deleteEvent(Long id) {
        Event eventToDelete = eventRepository.findById(id)
                        .orElseThrow(()-> new EventNotFoundException("No event found with id: " + id));
        eventRepository.delete(eventToDelete);
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
