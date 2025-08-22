package com.staj.biletbul.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "organizer")
public class Organizer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String organizerName;

     @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @OneToMany(mappedBy = "organizer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Event> eventList = new ArrayList<Event>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "organizer_roles",
            joinColumns = @JoinColumn(name = "organizer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Organizer() {
    }

    public Organizer(Long id,
                     String fullName,
                     String email,
                     String password,
                     List<Event> eventList,
                     Set<Role> roles) {
        this.id = id;
        this.organizerName = fullName;
        this.email = email;
        this.password = password;
        this.eventList = eventList;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String fullName) {
        this.organizerName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
