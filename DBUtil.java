package com.securevault.model;

public class Credential {
    private int id;
    private String website;
    private String username;
    private String password;
    private String category;
    private String notes;

    public Credential() {
    }

    public Credential(int id, String website, String username, String password, String category, String notes) {
        this.id = id;
        this.website = website;
        this.username = username;
        this.password = password;
        this.category = category;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
