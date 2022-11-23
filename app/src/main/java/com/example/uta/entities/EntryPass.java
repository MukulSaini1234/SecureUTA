package com.example.uta.entities;

public class EntryPass {
    private String name;
    private String email;
    private String phone;
    private String utaId;
    private String type;
    private String passcode;

    public EntryPass() {
    }

    public EntryPass(String name, String email, String phone, String utaId, String type, String passcode) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.utaId = utaId;
        this.type = type;
        this.passcode = passcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUtaId() {
        return utaId;
    }

    public void setUtaId(String utaId) {
        this.utaId = utaId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
