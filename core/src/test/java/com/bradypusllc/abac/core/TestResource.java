package com.bradypusllc.abac.core;

public class TestResource {
    private String createdBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "TestResource{" +
                "createdBy='" + createdBy + '\'' +
                '}';
    }
}
