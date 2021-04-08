package com.bradypusllc.abac.core;

public class TestSubject {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "TestSubject{" +
                "username='" + username + '\'' +
                '}';
    }
}
