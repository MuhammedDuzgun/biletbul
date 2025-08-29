package com.staj.biletbul.security;

import com.staj.biletbul.exception.UserNotFoundException;
import com.staj.biletbul.repository.AdminRepository;
import com.staj.biletbul.repository.OrganizerRepository;
import com.staj.biletbul.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final OrganizerRepository organizerRepository;
    private final AdminRepository adminRepository;

    public CustomUserDetailsService(UserRepository userRepository,
                                    OrganizerRepository organizerRepository,
                                    AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.organizerRepository = organizerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(user -> {
                    Set<SimpleGrantedAuthority> authorities = user
                            .getRoles()
                            .stream()
                            .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                            .collect(Collectors.toSet());
                    return new CustomUserDetails(user.getEmail(), user.getPassword(), authorities);
                })
                .orElseGet(()-> organizerRepository.findByEmail(username)
                        .map(organizer -> {
                            Set<SimpleGrantedAuthority> authorities = organizer
                                    .getRoles()
                                    .stream()
                                    .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                                    .collect(Collectors.toSet());
                            return new CustomUserDetails(organizer.getEmail(), organizer.getPassword(), authorities);
                        })
                        .orElseGet(()-> adminRepository.findByEmail(username)
                                .map(admin -> {
                                  Set<SimpleGrantedAuthority> authorities = admin
                                          .getRoles()
                                          .stream()
                                          .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                                          .collect(Collectors.toSet());
                                  return new CustomUserDetails(admin.getEmail(), admin.getPassword(), authorities);
                                })
                                .orElseThrow(()-> new UserNotFoundException("user with email " + username + " not found"))
                        )
                );
    }
}
