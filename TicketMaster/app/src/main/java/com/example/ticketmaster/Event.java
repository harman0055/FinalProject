package com.example.ticketmaster;

public class Event {
    private String name;
    private String startDate;
    private String priceRange;
    private String ticketmasterUrl;
    private String imageUrl;

    public Event(String name, String startDate, String priceRange, String ticketmasterUrl, String imageUrl) {
        this.name = name;
        this.startDate = startDate;
        this.priceRange = priceRange;
        this.ticketmasterUrl = ticketmasterUrl;
        this.imageUrl = imageUrl;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getTicketmasterUrl() {
        return ticketmasterUrl;
    }

    public void setTicketmasterUrl(String ticketmasterUrl) {
        this.ticketmasterUrl = ticketmasterUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
