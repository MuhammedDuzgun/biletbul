package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.Organizer;
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

}
