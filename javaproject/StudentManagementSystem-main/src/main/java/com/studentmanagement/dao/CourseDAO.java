package com.studentmanagement.dao;

import com.studentmanagement.model.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    // Create
    public void save(Course course) {
        Session session = sessionFactory.getCurrentSession();
        session.save(course);
    }

    // Read - Get by ID
    public Course getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Course.class, id);
    }

    // Read - Get all courses
    public List<Course> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Course> query = session.createQuery("FROM Course", Course.class);
        return query.getResultList();
    }

    // Read - Get by name
    public Course getByName(String courseName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Course> query = session.createQuery(
                "FROM Course WHERE courseName = :courseName", Course.class);
        query.setParameter("courseName", courseName);
        return query.uniqueResult();
    }

    // Update
    public void update(Course course) {
        Session session = sessionFactory.getCurrentSession();
        session.update(course);
    }

    // Delete
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Course course = session.get(Course.class, id);
        if (course != null) {
            session.delete(course);
        }
    }
}