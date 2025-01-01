package com.example.todolist;

public class Task {
    private long id;
    private String name;
    private boolean isCompleted;

    public Task(long id, String name, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.isCompleted = isCompleted;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
,