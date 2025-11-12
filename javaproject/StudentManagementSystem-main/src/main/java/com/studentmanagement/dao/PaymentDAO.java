package com.studentmanagement.dao;

import com.studentmanagement.model.Payment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    // Create
    public void save(Payment payment) {
        Session session = sessionFactory.getCurrentSession();
        session.save(payment);
    }

    // Read - Get by ID
    public Payment getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Payment.class, id);
    }

    // Read - Get all payments
    public List<Payment> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Payment> query = session.createQuery("FROM Payment", Payment.class);
        return query.getResultList();
    }

    // Read - Get payments by student
    public List<Payment> getPaymentsByStudent(Long studentId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Payment> query = session.createQuery(
                "FROM Payment WHERE student.studentId = :studentId ORDER BY paymentDate DESC",
                Payment.class);
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }

    // Get total payments for a student
    public Double getTotalPayments(Long studentId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Double> query = session.createQuery(
                "SELECT COALESCE(SUM(amount), 0.0) FROM Payment " +
                        "WHERE student.studentId = :studentId AND paymentType = 'PAYMENT'",
                Double.class);
        query.setParameter("studentId", studentId);
        return query.uniqueResult();
    }

    // Get total refunds for a student
    public Double getTotalRefunds(Long studentId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Double> query = session.createQuery(
                "SELECT COALESCE(SUM(amount), 0.0) FROM Payment " +
                        "WHERE student.studentId = :studentId AND paymentType = 'REFUND'",
                Double.class);
        query.setParameter("studentId", studentId);
        return query.uniqueResult();
    }
}