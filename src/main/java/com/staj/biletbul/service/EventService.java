package com.staj.biletbul.service;

import com.staj.biletbul.entity.Event;
import com.staj.biletbul.entity.Organizer;
import com.staj.biletbul.entity.User;
import com.staj.biletbul.enums.SeatType;
import com.staj.biletbul.repository.EventRepository;
import com.staj.biletbul.repository.OrganizerRepository;
import com.staj.biletbul.repository.UserRepository;
import com.staj.biletbul.request.AddUserToEventRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository,
                        OrganizerRepository organizerRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.userRepository = userRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).get();
    }

    @Transactional
    public Event createEvent(Event event) {
        Long organizerId = event.getOrganizer().getId();
        Organizer organizer = organizerRepository.findById(organizerId).get();
        event.setOrganizer(organizer);
        return eventRepository.save(event);
    }

    @Transactional
    public void deleteEvent(Long id) {
        Event eventToDelete = getEventById(id);
        eventRepository.delete(eventToDelete);
    }

    @Transactional
    public Event addUserToEvent(Long eventId, AddUserToEventRequest request) {
        Event event = getEventById(eventId);
        User user = userRepository.findByEmail(request.email())
                        .orElseThrow(() -> new RuntimeException("User not found with email " + request.email()));

        //kullanıcı daha once event'e kaydolmus mu
        if(event.getUsers().contains(user)) {
            throw new RuntimeException("Bu etkinliğe daha önce kaydolmuşsunuz");
        }

        //musait koltuk var mı
        if (request.seatType().equals(SeatType.STANDARD)) {
            if(!(event.getStandardSeats() > 0)) {
                throw new RuntimeException("Standard koltuklar tükendi");
            }
        } else if (request.seatType().equals(SeatType.VIP)) {
            if(!(event.getVipSeats() > 0)) {
                throw new RuntimeException("Vip koltuklar tükendi");
            }
        }

        //etkinlige kullaniciyi ekleme
        event.getUsers().add(user);

        //koltuk azaltma
        if (request.seatType() == SeatType.STANDARD) {
            event.setStandardSeats(event.getStandardSeats() - 1);
        } else if (request.seatType() == SeatType.VIP) {
            event.setVipSeats(event.getVipSeats() - 1);
        }

        return eventRepository.save(event);
    }

}
