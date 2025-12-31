package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * In-memory data storage for Student entities.
 * Uses ArrayList to store and manage students.
 */
public class StudentRepository {
    private final List<Student> students;

    public StudentRepository() {
        this.students = new ArrayList<>();
    }

    /**
     * Adds a new student to the repository.
     * @param student the student to add
     * @throws IllegalArgumentException if student is null
     */
    public void save(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        students.add(student);
    }

    /**
     * Finds a student by their ID.
     * @param id the student ID
     * @return Optional containing the student if found, empty otherwise
     */
    public Optional<Student> findById(int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst();
    }

    /**
     * Returns all students in the repository.
     * @return list of all students
     */
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    /**
     * Returns only active students.
     * @return list of active students
     */
    public List<Student> findAllActive() {
        return students.stream()
                .filter(Student::isActive)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing student.
     * @param updatedStudent the student with updated information
     * @return true if update was successful, false if student not found
     */
    public boolean update(Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == updatedStudent.getId()) {
                students.set(i, updatedStudent);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a student from the repository.
     * @param id the ID of the student to remove
     * @return true if removal was successful, false if student not found
     */
    public boolean deleteById(int id) {
        return students.removeIf(student -> student.getId() == id);
    }

    /**
     * Checks if a student exists with the given ID.
     * @param id the student ID
     * @return true if student exists, false otherwise
     */
    public boolean existsById(int id) {
        return findById(id).isPresent();
    }

    /**
     * Returns the count of all students.
     * @return total number of students
     */
    public int count() {
        return students.size();
    }

    /**
     * Finds students by batch.
     * @param batch the batch name
     * @return list of students in the specified batch
     */
    public List<Student> findByBatch(String batch) {
        return students.stream()
                .filter(student -> student.getBatch() != null &&
                        student.getBatch().equalsIgnoreCase(batch))
                .collect(Collectors.toList());
    }
}
