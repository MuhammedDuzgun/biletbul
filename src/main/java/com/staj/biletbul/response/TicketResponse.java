package com.staj.biletbul.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TicketResponse {

    private String fullName;
    private BigDecimal price;
    private String eventTitle;
    private String eventDescription;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String venueName;
    private String venueAddress;
    private String organizerName;
    private String artistName;
    private String cityName;
    private String seatNumber;
    private TicketTypeResponse ticketType;

    public TicketResponse() {
    }

    public TicketResponse(String fullName,
                          BigDecimal price,
                          String eventTitle,
                          String eventDescription,
                          LocalDateTime startTime,
                          LocalDateTime endTime,
                          String venueName,
                          String venueAddress,
                          String organizerName,
                          String artistName,
                          String cityName,
                          String seatNumber,
                          TicketTypeResponse ticketType) {
        this.fullName = fullName;
        this.price = price;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venueName = venueName;
        this.venueAddress = venueAddress;
        this.organizerName = organizerName;
        this.artistName = artistName;
        this.cityName = cityName;
        this.seatNumber = seatNumber;
        this.ticketType = ticketType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
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

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public TicketTypeResponse getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketTypeResponse ticketType) {
        this.ticketType = ticketType;
    }
}

