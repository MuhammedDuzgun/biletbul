package com.staj.biletbul.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Integer standardSeats;

    private Integer vipSeats;

    private boolean isAllStandardSeatsReserved = false;

    private boolean isAllVipSeatsReserved = false;

    @ManyToMany
    @JoinTable(
            name = "event_users",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer_id", nullable = false)
    private Organizer organizer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_category_id", nullable = false)
    private EventCategory eventCategory;

    public Event() {
    }

    public Event(Long id,
                 String description,
                 Integer standartSeats,
                 Integer vipSeats,
                 List<User> users,
                 Organizer organizer,
                 EventCategory eventCategory) {
        this.id = id;
        this.description = description;
        this.vipSeats = vipSeats;
        this.standardSeats = standartSeats;
        this.users = users;
        this.organizer = organizer;
        this.eventCategory = eventCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStandardSeats() {
        return standardSeats;
    }

    public void setStandardSeats(Integer standartSeats) {
        this.standardSeats = standartSeats;
    }

    public Integer getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(Integer vipSeats) {
        this.vipSeats = vipSeats;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public boolean isAllStandardSeatsReserved() {
        return isAllStandardSeatsReserved;
    }

    public void setAllStandardSeatsReserved(boolean allStandardSeatsReserved) {
        isAllStandardSeatsReserved = allStandardSeatsReserved;
    }

    public boolean isAllVipSeatsReserved() {
        return isAllVipSeatsReserved;
    }

    public void setAllVipSeatsReserved(boolean allVipSeatsReserved) {
        isAllVipSeatsReserved = allVipSeatsReserved;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }
}
