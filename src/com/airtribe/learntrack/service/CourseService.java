package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;
import java.util.List;

/**
 * Business logic layer for Course operations.
 * Handles validation, ID generation, and delegates to repository.
 */
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Adds a new course to the system.
     * @param courseName the course name
     * @param description the course description
     * @param durationInWeeks the duration in weeks
     * @return the created course
     * @throws InvalidInputException if input validation fails
     */
    public Course addCourse(String courseName, String description, int durationInWeeks)
            throws InvalidInputException {
        // Validate input
        InputValidator.validateNotEmpty(courseName, "Course name");
        InputValidator.validatePositive(durationInWeeks, "Duration in weeks");

        // Create course with generated ID
        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName.trim(),
                description != null ? description.trim() : "", durationInWeeks);

        courseRepository.save(course);
        return course;
    }

    /**
     * Adds a course without description (method overloading).
     * @param courseName the course name
     * @param durationInWeeks the duration in weeks
     * @return the created course
     * @throws InvalidInputException if input validation fails
     */
    public Course addCourse(String courseName, int durationInWeeks) throws InvalidInputException {
        return addCourse(courseName, "", durationInWeeks);
    }

    /**
     * Retrieves a course by ID.
     * @param id the course ID
     * @return the course
     * @throws EntityNotFoundException if course not found
     */
    public Course getCourseById(int id) throws EntityNotFoundException {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course", id));
    }

    /**
     * Returns all courses.
     * @return list of all courses
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Returns only active courses.
     * @return list of active courses
     */
    public List<Course> getActiveCourses() {
        return courseRepository.findAllActive();
    }

    /**
     * Updates course information.
     * @param id course ID
     * @param courseName new course name (null to keep existing)
     * @param description new description (null to keep existing)
     * @param durationInWeeks new duration (0 or negative to keep existing)
     * @return the updated course
     * @throws EntityNotFoundException if course not found
     * @throws InvalidInputException if input validation fails
     */
    public Course updateCourse(int id, String courseName, String description, int durationInWeeks)
            throws EntityNotFoundException, InvalidInputException {
        Course course = getCourseById(id);

        if (courseName != null && !courseName.trim().isEmpty()) {
            course.setCourseName(courseName.trim());
        }
        if (description != null) {
            course.setDescription(description.trim());
        }
        if (durationInWeeks > 0) {
            course.setDurationInWeeks(durationInWeeks);
        }

        courseRepository.update(course);
        return course;
    }

    /**
     * Activates a course.
     * @param id the course ID
     * @return the activated course
     * @throws EntityNotFoundException if course not found
     */
    public Course activateCourse(int id) throws EntityNotFoundException {
        Course course = getCourseById(id);
        course.setActive(true);
        courseRepository.update(course);
        return course;
    }

    /**
     * Deactivates a course.
     * @param id the course ID
     * @return the deactivated course
     * @throws EntityNotFoundException if course not found
     */
    public Course deactivateCourse(int id) throws EntityNotFoundException {
        Course course = getCourseById(id);
        course.setActive(false);
        courseRepository.update(course);
        return course;
    }

    /**
     * Toggles the active status of a course.
     * @param id the course ID
     * @return the updated course
     * @throws EntityNotFoundException if course not found
     */
    public Course toggleCourseStatus(int id) throws EntityNotFoundException {
        Course course = getCourseById(id);
        course.setActive(!course.isActive());
        courseRepository.update(course);
        return course;
    }

    /**
     * Checks if a course exists.
     * @param id the course ID
     * @return true if course exists
     */
    public boolean courseExists(int id) {
        return courseRepository.existsById(id);
    }

    /**
     * Gets the total count of courses.
     * @return total number of courses
     */
    public int getCourseCount() {
        return courseRepository.count();
    }

    /**
     * Searches courses by name.
     * @param name the search term
     * @return list of matching courses
     */
    public List<Course> searchCoursesByName(String name) {
        return courseRepository.findByNameContaining(name);
    }
}
