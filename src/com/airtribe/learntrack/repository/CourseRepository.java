package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * In-memory data storage for Course entities.
 * Uses ArrayList to store and manage courses.
 */
public class CourseRepository {
    private final List<Course> courses;

    public CourseRepository() {
        this.courses = new ArrayList<>();
    }

    /**
     * Adds a new course to the repository.
     * @param course the course to add
     */
    public void save(Course course) {
        courses.add(course);
    }

    /**
     * Finds a course by its ID.
     * @param id the course ID
     * @return Optional containing the course if found, empty otherwise
     */
    public Optional<Course> findById(int id) {
        for (Course course : courses) {
            if (course.getId() == id) {
                return Optional.of(course);
            }
        }
        return Optional.empty();
    }

    /**
     * Returns all courses in the repository.
     * @return list of all courses
     */
    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }

    /**
     * Returns only active courses.
     * @return list of active courses
     */
    public List<Course> findAllActive() {
        List<Course> activeCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.isActive()) {
                activeCourses.add(course);
            }
        }
        return activeCourses;
    }

    /**
     * Updates an existing course.
     * @param updatedCourse the course with updated information
     * @return true if update was successful, false if course not found
     */
    public boolean update(Course updatedCourse) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId() == updatedCourse.getId()) {
                courses.set(i, updatedCourse);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a course from the repository.
     * @param id the ID of the course to remove
     * @return true if removal was successful, false if course not found
     */
    public boolean deleteById(int id) {
        return courses.removeIf(course -> course.getId() == id);
    }

    /**
     * Checks if a course exists with the given ID.
     * @param id the course ID
     * @return true if course exists, false otherwise
     */
    public boolean existsById(int id) {
        return findById(id).isPresent();
    }

    /**
     * Returns the count of all courses.
     * @return total number of courses
     */
    public int count() {
        return courses.size();
    }

    /**
     * Finds courses by name (partial match, case-insensitive).
     * @param name the course name to search for
     * @return list of matching courses
     */
    public List<Course> findByNameContaining(String name) {
        List<Course> result = new ArrayList<>();
        for (Course course : courses) {
            if (course.getCourseName() != null &&
                course.getCourseName().toLowerCase().contains(name.toLowerCase())) {
                result.add(course);
            }
        }
        return result;
    }
}
