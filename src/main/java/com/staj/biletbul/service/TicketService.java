package com.staj.biletbul.service;

import com.staj.biletbul.entity.User;
import com.staj.biletbul.exception.UserNotFoundException;
import com.staj.biletbul.mapper.TicketMapper;
import com.staj.biletbul.repository.TicketRepository;
import com.staj.biletbul.repository.UserRepository;
import com.staj.biletbul.response.TicketResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository,
                         TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.ticketMapper = ticketMapper;
    }

    //kullanıcının tum biletleri
    public List<TicketResponse> getAllTicketsOfUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id : " + id));

        return user.getTickets()
                .stream()
                .map(ticketMapper::mapToTicketResponse)
                .toList();
    }

}
