package com.staj.biletbul.response;

import java.io.Serializable;

public record EventCategoryResponse(Long id,
                                    String categoryName,
                                    String description) implements Serializable {
}
