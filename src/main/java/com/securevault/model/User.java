package com.securevault.model;

public class User {
    private int id;
    private String username;
    private String masterPasswordHash;

    public User() {
    }

    public User(int id, String username, String masterPasswordHash) {
        this.id = id;
        this.username = username;
        this.masterPasswordHash = masterPasswordHash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMasterPasswordHash() {
        return masterPasswordHash;
    }

    public void setMasterPasswordHash(String masterPasswordHash) {
        this.masterPasswordHash = masterPasswordHash;
    }
}
