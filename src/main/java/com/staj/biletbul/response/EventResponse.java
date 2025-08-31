package com.staj.biletbul.response;

import com.staj.biletbul.enums.EventStatus;
import com.staj.biletbul.enums.EventType;

import java.util.List;

public class EventResponse {

    private Long id;
    private String title;
    private String description;
    private EventStatus status;
    private EventType eventType;
    private String startTime;
    private String endTime;
    private String venueName;
    private String organizerName;
    private String eventCategoryName;
    private String artistName;
    private String cityName;
    private List<TicketTypeResponse> ticketTypes;

    public EventResponse() {
    }

    public EventResponse(Long id, String title, String description, EventStatus status, EventType eventType, String startTime, String endTime, String venueName, String organizerName, String eventCategoryName, String artistName, String cityName, List<TicketTypeResponse> ticketTypes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.eventType = eventType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venueName = venueName;
        this.organizerName = organizerName;
        this.eventCategoryName = eventCategoryName;
        this.artistName = artistName;
        this.cityName = cityName;
        this.ticketTypes = ticketTypes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getEventCategoryName() {
        return eventCategoryName;
    }

    public void setEventCategoryName(String eventCategoryName) {
        this.eventCategoryName = eventCategoryName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<TicketTypeResponse> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<TicketTypeResponse> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }
}
