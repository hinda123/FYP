package com.example.fyp.model.event;

import java.util.UUID;

public class Event {

    private  UUID id;
    private  String title;
    private  String description;
    private  String media;
    private  MediaType mediaType;
    private  String postedAt;

    public Event() {
    }

    public Event(UUID id, String title, String description, String media, MediaType mediaType, String postedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.media = media;
        this.mediaType = mediaType;
        this.postedAt = postedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMedia() {
        return media;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public String getPostedAt() {
        return postedAt;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", media='" + media + '\'' +
                ", mediaType=" + mediaType +
                ", postedAt='" + postedAt + '\'' +
                '}';
    }
}
