package com.studentmanagement.dao;

import com.studentmanagement.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    // Create
    public void save(Student student) {
        Session session = sessionFactory.getCurrentSession();
        session.save(student);
    }

    // Read - Get by ID
    public Student getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Student.class, id);
    }

    // Read - Get all students
    public List<Student> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Student> query = session.createQuery("FROM Student", Student.class);
        return query.getResultList();
    }

    // Read - Get by email
    public Student getByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<Student> query = session.createQuery(
                "FROM Student WHERE email = :email", Student.class);
        query.setParameter("email", email);
        return query.uniqueResult();
    }

    // Read - Get students by course
    public List<Student> getStudentsByCourse(Long courseId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Student> query = session.createQuery(
                "FROM Student WHERE course.courseId = :courseId", Student.class);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }

    // Update
    public void update(Student student) {
        Session session = sessionFactory.getCurrentSession();
        session.update(student);
    }

    // Delete
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Student student = session.get(Student.class, id);
        if (student != null) {
            session.delete(student);
        }
    }

    // Check if email exists
    public boolean emailExists(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery(
                "SELECT COUNT(*) FROM Student WHERE email = :email", Long.class);
        query.setParameter("email", email);
        return query.uniqueResult() > 0;
    }
}