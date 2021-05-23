package com.example.conductor_app.model;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class Ticket {
    private String ticketId;
    private String from;
    private String to;
    private String ticketOwner;
    private boolean isVerified;
    private String qrCodeUrl;
    private String verifiedBy;
    private Timestamp createdAt;
    private double ticketFare;

    public Ticket() {
    }

    public Ticket(String ticketId, String from, String to, String ticketOwner, boolean isVerified, String qrCodeUrl, String verifiedBy, Timestamp createdAt, double ticketFare) {
        this.ticketId = ticketId;
        this.from = from;
        this.to = to;
        this.ticketOwner = ticketOwner;
        this.isVerified = isVerified;
        this.qrCodeUrl = qrCodeUrl;
        this.verifiedBy = verifiedBy;
        this.createdAt = createdAt;
        this.ticketFare = ticketFare;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTicketOwner() {
        return ticketOwner;
    }

    public void setTicketOwner(String ticketOwner) {
        this.ticketOwner = ticketOwner;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public double getTicketFare() {
        return ticketFare;
    }

    public void setTicketFare(double ticketFare) {
        this.ticketFare = ticketFare;
    }

    public HashMap<String, Object> getHashMap() {
        HashMap<String, Object> ticket = new HashMap<>();
        ticket.put("from", from);
        ticket.put("to", to);
        ticket.put("ticketOwner", ticketOwner);
        ticket.put("isVerified", isVerified);
        ticket.put("qrCodeUrl", qrCodeUrl);
        ticket.put("verifiedBy", verifiedBy);
        ticket.put("createdAt", createdAt);
        ticket.put("ticketFare",ticketFare);
        return ticket;
    }

    @Override
    public boolean equals(@androidx.annotation.Nullable Object obj) {
        Ticket ticket = (Ticket) obj;
        return getTicketId().matches(ticket.getTicketId());
    }
}
