package com.example.Inditex.prices.model;

public enum Priority {

    LOW(0),
    HIGH(2);

    private final Integer priority;

    Priority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }
}
