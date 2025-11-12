package com.studentmanagement.service;

import com.studentmanagement.dao.StudentDAO;
import com.studentmanagement.dao.CourseDAO;
import com.studentmanagement.model.Student;
import com.studentmanagement.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private CourseDAO courseDAO;

    // Create student
    @Transactional
    public void addStudent(String name, String email) {
        if (studentDAO.emailExists(email)) {
            throw new RuntimeException("Student with email " + email + " already exists!");
        }
        Student student = new Student(name, email);
        studentDAO.save(student);
        System.out.println("Student added successfully with ID: " + student.getStudentId());
    }

    // Enroll student in course
    @Transactional
    public void enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = studentDAO.getById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found!");
        }

        Course course = courseDAO.getById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found!");
        }

        student.setCourse(course);
        studentDAO.update(student);
        System.out.println("Student enrolled in " + course.getCourseName() + " successfully!");
    }

    // Update student
    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentDAO.getById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found!");
        }

        if (!student.getEmail().equals(email) && studentDAO.emailExists(email)) {
            throw new RuntimeException("Email already exists!");
        }

        student.setName(name);
        student.setEmail(email);
        studentDAO.update(student);
        System.out.println("Student updated successfully!");
    }

    // Delete student
    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = studentDAO.getById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found!");
        }
        studentDAO.delete(studentId);
        System.out.println("Student deleted successfully!");
    }

    // Get student by ID
    @Transactional(readOnly = true)
    public Student getStudent(Long studentId) {
        return studentDAO.getById(studentId);
    }

    // Get all students
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentDAO.getAll();
    }

    // Get students by course
    @Transactional(readOnly = true)
    public List<Student> getStudentsByCourse(Long courseId) {
        return studentDAO.getStudentsByCourse(courseId);
    }
}