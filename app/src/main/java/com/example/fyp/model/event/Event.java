package com.example.fyp.model.event;

import java.time.LocalDateTime;

public class Event {

    private final Long id ;
    private final String title;
    private final String description;
    private final String url;
    private final MediaType mediaType;
    private final LocalDateTime dateTime;

    public Event(Long id, String title, String description, String url, MediaType mediaType, LocalDateTime dateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.mediaType = mediaType;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
