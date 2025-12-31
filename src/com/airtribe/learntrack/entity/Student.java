package com.airtribe.learntrack.entity;

/**
 * Represents a student in the system.
 * Extends Person to demonstrate inheritance.
 */
public class Student extends Person {
    private String batch;
    private boolean active;

    // Default constructor
    public Student() {
        super();
        this.active = true;
    }

    // Parameterized constructor with all fields
    public Student(int id, String firstName, String lastName, String email, String batch, boolean active) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = active;
    }

    // Constructor without email (demonstrates constructor overloading)
    public Student(int id, String firstName, String lastName, String batch) {
        super(id, firstName, lastName);
        this.batch = batch;
        this.active = true;
    }

    // Constructor with email but default active status
    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = true;
    }

    // Getters and Setters
    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Overrides Person's getDisplayName to include batch information.
     * Demonstrates polymorphism through method overriding.
     */
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (Batch: " + batch + ")";
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", batch='" + batch + '\'' +
                ", active=" + active +
                '}';
    }
}
