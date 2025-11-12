package com.studentmanagement.service;

import com.studentmanagement.dao.CourseDAO;
import com.studentmanagement.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseDAO courseDAO;

    // Add course
    @Transactional
    public void addCourse(String courseName, Integer duration, Double fee) {
        Course course = new Course(courseName, duration, fee);
        courseDAO.save(course);
        System.out.println("Course added successfully with ID: " + course.getCourseId());
    }

    // Update course
    @Transactional
    public void updateCourse(Long courseId, String courseName, Integer duration, Double fee) {
        Course course = courseDAO.getById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found!");
        }

        course.setCourseName(courseName);
        course.setDuration(duration);
        course.setFee(fee);
        courseDAO.update(course);
        System.out.println("Course updated successfully!");
    }

    // Delete course
    @Transactional
    public void deleteCourse(Long courseId) {
        Course course = courseDAO.getById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found!");
        }

        if (!course.getStudents().isEmpty()) {
            throw new RuntimeException("Cannot delete course with enrolled students!");
        }

        courseDAO.delete(courseId);
        System.out.println("Course deleted successfully!");
    }

    // Get course by ID
    @Transactional(readOnly = true)
    public Course getCourse(Long courseId) {
        return courseDAO.getById(courseId);
    }

    // Get all courses
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseDAO.getAll();
    }
}