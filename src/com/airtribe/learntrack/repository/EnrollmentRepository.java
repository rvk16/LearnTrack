package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * In-memory data storage for Enrollment entities.
 * Uses ArrayList to store and manage enrollments.
 */
public class EnrollmentRepository {
    private final List<Enrollment> enrollments;

    public EnrollmentRepository() {
        this.enrollments = new ArrayList<>();
    }

    /**
     * Adds a new enrollment to the repository.
     * @param enrollment the enrollment to add
     */
    public void save(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    /**
     * Finds an enrollment by its ID.
     * @param id the enrollment ID
     * @return Optional containing the enrollment if found, empty otherwise
     */
    public Optional<Enrollment> findById(int id) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                return Optional.of(enrollment);
            }
        }
        return Optional.empty();
    }

    /**
     * Returns all enrollments in the repository.
     * @return list of all enrollments
     */
    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }

    /**
     * Finds all enrollments for a specific student.
     * @param studentId the student ID
     * @return list of enrollments for the student
     */
    public List<Enrollment> findByStudentId(int studentId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                result.add(enrollment);
            }
        }
        return result;
    }

    /**
     * Finds all enrollments for a specific course.
     * @param courseId the course ID
     * @return list of enrollments for the course
     */
    public List<Enrollment> findByCourseId(int courseId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourseId() == courseId) {
                result.add(enrollment);
            }
        }
        return result;
    }

    /**
     * Finds enrollments by status.
     * @param status the enrollment status
     * @return list of enrollments with the specified status
     */
    public List<Enrollment> findByStatus(EnrollmentStatus status) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStatus() == status) {
                result.add(enrollment);
            }
        }
        return result;
    }

    /**
     * Checks if a student is already enrolled in a course (with ACTIVE status).
     * @param studentId the student ID
     * @param courseId the course ID
     * @return true if an active enrollment exists, false otherwise
     */
    public boolean existsActiveEnrollment(int studentId, int courseId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId &&
                enrollment.getCourseId() == courseId &&
                enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates an existing enrollment.
     * @param updatedEnrollment the enrollment with updated information
     * @return true if update was successful, false if enrollment not found
     */
    public boolean update(Enrollment updatedEnrollment) {
        for (int i = 0; i < enrollments.size(); i++) {
            if (enrollments.get(i).getId() == updatedEnrollment.getId()) {
                enrollments.set(i, updatedEnrollment);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an enrollment from the repository.
     * @param id the ID of the enrollment to remove
     * @return true if removal was successful, false if enrollment not found
     */
    public boolean deleteById(int id) {
        return enrollments.removeIf(enrollment -> enrollment.getId() == id);
    }

    /**
     * Checks if an enrollment exists with the given ID.
     * @param id the enrollment ID
     * @return true if enrollment exists, false otherwise
     */
    public boolean existsById(int id) {
        return findById(id).isPresent();
    }

    /**
     * Returns the count of all enrollments.
     * @return total number of enrollments
     */
    public int count() {
        return enrollments.size();
    }
}
