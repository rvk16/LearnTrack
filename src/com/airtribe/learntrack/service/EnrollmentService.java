package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import java.util.List;

/**
 * Business logic layer for Enrollment operations.
 * Handles validation, ID generation, and delegates to repository.
 */
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                            StudentService studentService,
                            CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    /**
     * Enrolls a student in a course.
     * @param studentId the student ID
     * @param courseId the course ID
     * @return the created enrollment
     * @throws EntityNotFoundException if student or course not found
     * @throws InvalidInputException if student is already enrolled
     */
    public Enrollment enrollStudent(int studentId, int courseId)
            throws EntityNotFoundException, InvalidInputException {
        // Validate student and course exist
        studentService.getStudentById(studentId); // Throws if not found
        courseService.getCourseById(courseId);    // Throws if not found

        // Check for existing active enrollment
        if (enrollmentRepository.existsActiveEnrollment(studentId, courseId)) {
            throw new InvalidInputException("Student is already actively enrolled in this course");
        }

        // Create enrollment with generated ID
        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId);

        enrollmentRepository.save(enrollment);
        return enrollment;
    }

    /**
     * Retrieves an enrollment by ID.
     * @param id the enrollment ID
     * @return the enrollment
     * @throws EntityNotFoundException if enrollment not found
     */
    public Enrollment getEnrollmentById(int id) throws EntityNotFoundException {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment", id));
    }

    /**
     * Returns all enrollments.
     * @return list of all enrollments
     */
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    /**
     * Gets enrollments for a specific student.
     * @param studentId the student ID
     * @return list of enrollments for the student
     * @throws EntityNotFoundException if student not found
     */
    public List<Enrollment> getEnrollmentsByStudent(int studentId) throws EntityNotFoundException {
        studentService.getStudentById(studentId); // Validate student exists
        return enrollmentRepository.findByStudentId(studentId);
    }

    /**
     * Gets enrollments for a specific course.
     * @param courseId the course ID
     * @return list of enrollments for the course
     * @throws EntityNotFoundException if course not found
     */
    public List<Enrollment> getEnrollmentsByCourse(int courseId) throws EntityNotFoundException {
        courseService.getCourseById(courseId); // Validate course exists
        return enrollmentRepository.findByCourseId(courseId);
    }

    /**
     * Updates enrollment status.
     * @param enrollmentId the enrollment ID
     * @param status the new status
     * @return the updated enrollment
     * @throws EntityNotFoundException if enrollment not found
     */
    public Enrollment updateEnrollmentStatus(int enrollmentId, EnrollmentStatus status)
            throws EntityNotFoundException {
        Enrollment enrollment = getEnrollmentById(enrollmentId);
        enrollment.setStatus(status);
        enrollmentRepository.update(enrollment);
        return enrollment;
    }

    /**
     * Marks an enrollment as completed.
     * @param enrollmentId the enrollment ID
     * @return the updated enrollment
     * @throws EntityNotFoundException if enrollment not found
     */
    public Enrollment completeEnrollment(int enrollmentId) throws EntityNotFoundException {
        return updateEnrollmentStatus(enrollmentId, EnrollmentStatus.COMPLETED);
    }

    /**
     * Cancels an enrollment.
     * @param enrollmentId the enrollment ID
     * @return the updated enrollment
     * @throws EntityNotFoundException if enrollment not found
     */
    public Enrollment cancelEnrollment(int enrollmentId) throws EntityNotFoundException {
        return updateEnrollmentStatus(enrollmentId, EnrollmentStatus.CANCELLED);
    }

    /**
     * Gets enrollments by status.
     * @param status the enrollment status
     * @return list of enrollments with the specified status
     */
    public List<Enrollment> getEnrollmentsByStatus(EnrollmentStatus status) {
        return enrollmentRepository.findByStatus(status);
    }

    /**
     * Checks if an enrollment exists.
     * @param id the enrollment ID
     * @return true if enrollment exists
     */
    public boolean enrollmentExists(int id) {
        return enrollmentRepository.existsById(id);
    }

    /**
     * Gets the total count of enrollments.
     * @return total number of enrollments
     */
    public int getEnrollmentCount() {
        return enrollmentRepository.count();
    }
}
