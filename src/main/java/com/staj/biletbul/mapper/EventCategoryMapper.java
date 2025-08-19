package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.EventCategory;
import com.staj.biletbul.response.EventCategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class EventCategoryMapper {

    public EventCategoryResponse mapToResponse(EventCategory eventCategory) {
        EventCategoryResponse response = new EventCategoryResponse(
                eventCategory.getId(),
                eventCategory.getCategoryName(),
                eventCategory.getDescription()
        );
        return response;
    }


}
