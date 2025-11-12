package com.studentmanagement.service;

import com.studentmanagement.dao.StudentDAO;
import com.studentmanagement.dao.PaymentDAO;
import com.studentmanagement.model.Student;
import com.studentmanagement.model.Payment;
import com.studentmanagement.model.Payment.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeeService {

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private PaymentDAO paymentDAO;

    /**
     * Process fee payment with transaction management
     * If any step fails, the entire transaction will be rolled back
     */
    @Transactional
    public void processPayment(Long studentId, Double amount, String description) {
        // Step 1: Validate student exists
        Student student = studentDAO.getById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found!");
        }

        // Step 2: Validate amount
        if (amount <= 0) {
            throw new RuntimeException("Payment amount must be positive!");
        }

        // Step 3: Update student balance (deduct amount)
        Double newBalance = student.getBalance() - amount;
        student.setBalance(newBalance);
        studentDAO.update(student);

        // Step 4: Create payment record
        Payment payment = new Payment(student, amount, PaymentType.PAYMENT, description);
        paymentDAO.save(payment);

        System.out.println("Payment of Rs." + amount + " processed successfully!");
        System.out.println("New balance: Rs." + newBalance);
    }

    /**
     * Process refund with transaction management
     * Ensures atomicity - if refund recording fails, balance update is rolled back
     */
    @Transactional
    public void processRefund(Long studentId, Double amount, String reason) {
        // Step 1: Validate student exists
        Student student = studentDAO.getById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found!");
        }

        // Step 2: Validate refund amount
        if (amount <= 0) {
            throw new RuntimeException("Refund amount must be positive!");
        }

        // Step 3: Calculate total paid and check if refund is possible
        Double totalPaid = paymentDAO.getTotalPayments(studentId);
        Double totalRefunded = paymentDAO.getTotalRefunds(studentId);
        Double availableForRefund = totalPaid - totalRefunded;

        if (amount > availableForRefund) {
            throw new RuntimeException("Refund amount exceeds available refund amount! " +
                    "Available: Rs." + availableForRefund);
        }

        // Step 4: Update student balance (add amount back)
        Double newBalance = student.getBalance() + amount;
        student.setBalance(newBalance);
        studentDAO.update(student);

        // Step 5: Create refund record
        Payment refund = new Payment(student, amount, PaymentType.REFUND, reason);
        paymentDAO.save(refund);

        System.out.println("Refund of Rs." + amount + " processed successfully!");
        System.out.println("New balance: Rs." + newBalance);
    }

    /**
     * Get payment history for a student
     */
    @Transactional(readOnly = true)
    public List<Payment> getPaymentHistory(Long studentId) {
        Student student = studentDAO.getById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found!");
        }
        return paymentDAO.getPaymentsByStudent(studentId);
    }

    /**
     * Get fee summary for a student
     */
    @Transactional(readOnly = true)
    public void displayFeeSummary(Long studentId) {
        Student student = studentDAO.getById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found!");
        }

        Double totalPaid = paymentDAO.getTotalPayments(studentId);
        Double totalRefunded = paymentDAO.getTotalRefunds(studentId);
        Double courseFee = student.getCourse() != null ? student.getCourse().getFee() : 0.0;
        Double netPaid = totalPaid - totalRefunded;
        Double balance = student.getBalance();

        System.out.println("\n===== Fee Summary for " + student.getName() + " =====");
        System.out.println("Course Fee: Rs." + courseFee);
        System.out.println("Total Paid: Rs." + totalPaid);
        System.out.println("Total Refunded: Rs." + totalRefunded);
        System.out.println("Net Paid: Rs." + netPaid);
        System.out.println("Current Balance: Rs." + balance);
        System.out.println("==========================================\n");
    }
}