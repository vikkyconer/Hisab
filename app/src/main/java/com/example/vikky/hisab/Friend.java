package com.example.vikky.hisab;

/**
 * Created by vikky on 6/29/15.
 */
public class Friend {
    private int id;
    private String name;
    private boolean status;
    private String createdAt;

    public Friend() {
    }

    public Friend(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Friend(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
