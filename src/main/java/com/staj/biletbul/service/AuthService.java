package com.staj.biletbul.service;

import com.staj.biletbul.entity.Organizer;
import com.staj.biletbul.entity.Role;
import com.staj.biletbul.entity.User;
import com.staj.biletbul.enums.RoleName;
import com.staj.biletbul.exception.OrganizerAlreadyExistsException;
import com.staj.biletbul.exception.UserAlreadyExistsException;
import com.staj.biletbul.repository.OrganizerRepository;
import com.staj.biletbul.repository.RoleRepository;
import com.staj.biletbul.repository.UserRepository;
import com.staj.biletbul.request.LoginRequest;
import com.staj.biletbul.request.SignupAsOrganizerRequest;
import com.staj.biletbul.request.SignupAsUserRequest;
import com.staj.biletbul.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final OrganizerRepository organizerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider,
                       UserRepository userRepository,
                       OrganizerRepository organizerRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.organizerRepository = organizerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.email(),
                loginRequest.password()
        ));

        //token olustur
        String token = jwtTokenProvider.generateToken(authentication);

        //set auth
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return token;
    }

    //signup as user
    public String signupAsUser(SignupAsUserRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.email())) {
            throw new UserAlreadyExistsException(signupRequest.email() + "  already exists");
        }
        User user = new User();
        user.setFullName(signupRequest.fullName());
        user.setEmail(signupRequest.email());
        user.setPassword(passwordEncoder.encode(signupRequest.password()));

        Set<Role> roles = new HashSet<>();
        Role roleUser = roleRepository.findByRoleName(RoleName.ROLE_USER);
        roles.add(roleUser);

        user.setRoles(roles);
        userRepository.save(user);
        return "success";
    }

    //signup as organizer
    public String signupAsOrganizer(SignupAsOrganizerRequest signupRequest) {
        if (organizerRepository.existsByEmail(signupRequest.email())) {
            throw new OrganizerAlreadyExistsException
                    ("Organizer with email: " + signupRequest.email() + " already exists");
        }
        if(organizerRepository.findByOrganizerName(signupRequest.organizerName()).isPresent()) {
            throw new OrganizerAlreadyExistsException
                    ("Organizer with name: " + signupRequest.organizerName() + " already exists");
        }
        Organizer organizer = new Organizer();
        organizer.setOrganizerName(signupRequest.organizerName());
        organizer.setPhoneNumber(signupRequest.phoneNumber());
        organizer.setEmail(signupRequest.email());
        organizer.setPassword(passwordEncoder.encode(signupRequest.password()));

        Set<Role> rolesOfOrganizer = new HashSet<>();
        Role roleOrganizer = roleRepository.findByRoleName(RoleName.ROLE_ORGANIZER);
        rolesOfOrganizer.add(roleOrganizer);
        organizer.setRoles(rolesOfOrganizer);

        organizerRepository.save(organizer);
        return "success";
    }

}
