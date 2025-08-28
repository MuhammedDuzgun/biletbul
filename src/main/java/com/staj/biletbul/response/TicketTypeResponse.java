package com.staj.biletbul.response;

import java.math.BigDecimal;

public record TicketTypeResponse(Long id,
                                 String name,
                                 BigDecimal price) {
}
