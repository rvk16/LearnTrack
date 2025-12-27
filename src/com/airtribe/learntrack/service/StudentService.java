package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.List;

/**
 * Business logic layer for Student operations.
 * Handles validation, ID generation, and delegates to repository.
 */
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Adds a new student to the system.
     * @param firstName student's first name
     * @param lastName student's last name
     * @param email student's email
     * @param batch student's batch
     * @return the created student
     * @throws InvalidInputException if input validation fails
     */
    public Student addStudent(String firstName, String lastName, String email, String batch)
            throws InvalidInputException {
        // Validate input
        InputValidator.validateNotEmpty(firstName, "First name");
        InputValidator.validateNotEmpty(lastName, "Last name");
        InputValidator.validateNotEmpty(batch, "Batch");
        if (email != null && !email.trim().isEmpty()) {
            InputValidator.validateEmail(email);
        }

        // Create student with generated ID
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName.trim(), lastName.trim(),
                email != null ? email.trim() : "", batch.trim());

        studentRepository.save(student);
        return student;
    }

    /**
     * Adds a student without email (method overloading demonstration).
     * @param firstName student's first name
     * @param lastName student's last name
     * @param batch student's batch
     * @return the created student
     * @throws InvalidInputException if input validation fails
     */
    public Student addStudent(String firstName, String lastName, String batch)
            throws InvalidInputException {
        return addStudent(firstName, lastName, "", batch);
    }

    /**
     * Retrieves a student by ID.
     * @param id the student ID
     * @return the student
     * @throws EntityNotFoundException if student not found
     */
    public Student getStudentById(int id) throws EntityNotFoundException {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student", id));
    }

    /**
     * Returns all students.
     * @return list of all students
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Returns only active students.
     * @return list of active students
     */
    public List<Student> getActiveStudents() {
        return studentRepository.findAllActive();
    }

    /**
     * Updates student information.
     * @param id student ID
     * @param firstName new first name (null to keep existing)
     * @param lastName new last name (null to keep existing)
     * @param email new email (null to keep existing)
     * @param batch new batch (null to keep existing)
     * @return the updated student
     * @throws EntityNotFoundException if student not found
     * @throws InvalidInputException if input validation fails
     */
    public Student updateStudent(int id, String firstName, String lastName, String email, String batch)
            throws EntityNotFoundException, InvalidInputException {
        Student student = getStudentById(id);

        if (firstName != null && !firstName.trim().isEmpty()) {
            student.setFirstName(firstName.trim());
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            student.setLastName(lastName.trim());
        }
        if (email != null) {
            if (!email.trim().isEmpty()) {
                InputValidator.validateEmail(email);
            }
            student.setEmail(email.trim());
        }
        if (batch != null && !batch.trim().isEmpty()) {
            student.setBatch(batch.trim());
        }

        studentRepository.update(student);
        return student;
    }

    /**
     * Deactivates a student (soft delete).
     * @param id the student ID
     * @return the deactivated student
     * @throws EntityNotFoundException if student not found
     */
    public Student deactivateStudent(int id) throws EntityNotFoundException {
        Student student = getStudentById(id);
        student.setActive(false);
        studentRepository.update(student);
        return student;
    }

    /**
     * Reactivates a student.
     * @param id the student ID
     * @return the reactivated student
     * @throws EntityNotFoundException if student not found
     */
    public Student reactivateStudent(int id) throws EntityNotFoundException {
        Student student = getStudentById(id);
        student.setActive(true);
        studentRepository.update(student);
        return student;
    }

    /**
     * Checks if a student exists.
     * @param id the student ID
     * @return true if student exists
     */
    public boolean studentExists(int id) {
        return studentRepository.existsById(id);
    }

    /**
     * Gets the total count of students.
     * @return total number of students
     */
    public int getStudentCount() {
        return studentRepository.count();
    }

    /**
     * Finds students by batch.
     * @param batch the batch name
     * @return list of students in the batch
     */
    public List<Student> getStudentsByBatch(String batch) {
        return studentRepository.findByBatch(batch);
    }
}
