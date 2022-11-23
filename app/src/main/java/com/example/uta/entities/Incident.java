package com.example.uta.entities;

import java.io.Serializable;

public class Incident implements Serializable {
    private String incidentId;
    private String senderName;
    private String senderEmail;
    private String senderPhone;
    private String senderUtaId;
    private String sendertype;
    private String datetime;
    private String location;
    private String remarks;
    private String status;

    public Incident() {
    }

    public Incident(String senderName, String senderEmail, String senderPhone, String senderUtaId, String sendertype, String datetime, String location, String remarks, String status) {
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.senderPhone = senderPhone;
        this.senderUtaId = senderUtaId;
        this.sendertype = sendertype;
        this.datetime = datetime;
        this.location = location;
        this.remarks = remarks;
        this.status = status;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderUtaId() {
        return senderUtaId;
    }

    public void setSenderUtaId(String senderUtaId) {
        this.senderUtaId = senderUtaId;
    }

    public String getSendertype() {
        return sendertype;
    }

    public void setSendertype(String sendertype) {
        this.sendertype = sendertype;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
