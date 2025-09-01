package com.staj.biletbul.response;

import java.io.Serializable;

public record ArtistResponse (Long id,
                             String name) implements Serializable {
}
