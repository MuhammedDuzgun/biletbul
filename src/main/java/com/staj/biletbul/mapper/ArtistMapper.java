package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.Artist;
import com.staj.biletbul.request.CreateArtistRequest;
import com.staj.biletbul.response.ArtistResponse;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {

    public ArtistResponse mapToArtistResponse(Artist artist) {
        ArtistResponse artistResponse = new ArtistResponse(
                artist.getId(),
                artist.getName()
        );
        return artistResponse;
    }

    public Artist mapToArtist(CreateArtistRequest request) {
        Artist artist = new Artist();
        artist.setName(request.name());
        return artist;
    }

}
