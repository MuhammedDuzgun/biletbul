package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.Organizer;
import com.staj.biletbul.request.CreateOrganizerRequest;
import com.staj.biletbul.response.OrganizerResponse;
import org.springframework.stereotype.Component;

@Component
public class OrganizerMapper {

    public OrganizerResponse mapToOrganizerResponse(Organizer organizer) {
        OrganizerResponse response = new OrganizerResponse(
                organizer.getId(),
                organizer.getOrganizerName(),
                organizer.getEmail()
        );
        return response;
    }

    public Organizer mapToEntity(CreateOrganizerRequest request) {
        Organizer organizer = new Organizer();
        organizer.setEmail(request.email());
        organizer.setOrganizerName(request.organizerName());
        organizer.setPassword(request.password());
        return organizer;
    }

}
