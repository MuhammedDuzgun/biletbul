package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.EventCategory;
import com.staj.biletbul.request.CreateEventCategoryRequest;
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

    public EventCategory mapToEntity(CreateEventCategoryRequest request) {
        EventCategory eventCategory = new EventCategory();
        eventCategory.setCategoryName(request.categoryName());
        eventCategory.setDescription(request.description());
        return eventCategory;
    }

}
