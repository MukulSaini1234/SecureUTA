package com.example.uta.entities;

import java.io.Serializable;

public class User implements Serializable {
    private String uid;
    private String name;
    private String emailId;
    private String phoneNo;
    private String utaId;
    private String userType;
    private String status;
    private String approvedBy;

    public User() {
    }

    public User(String uid, String name, String emailId, String phoneNo, String utaId, String userType, String status, String approvedBy) {
        this.uid = uid;
        this.name = name;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.utaId = utaId;
        this.userType = userType;
        this.status = status;
        this.approvedBy = approvedBy;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUtaId() {
        return utaId;
    }

    public void setUtaId(String utaId) {
        this.utaId = utaId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
}
