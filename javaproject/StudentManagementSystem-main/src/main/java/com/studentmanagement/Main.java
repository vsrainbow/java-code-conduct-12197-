package com.studentmanagement;

import com.studentmanagement.config.AppConfig;
import com.studentmanagement.model.Student;
import com.studentmanagement.model.Course;
import com.studentmanagement.model.Payment;
import com.studentmanagement.service.StudentService;
import com.studentmanagement.service.CourseService;
import com.studentmanagement.service.FeeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static ApplicationContext context;
    private static StudentService studentService;
    private static CourseService courseService;
    private static FeeService feeService;
    private static Scanner scanner;

    public static void main(String[] args) {
        // Initialize Spring Context using Java-based configuration
        context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Get beans from context (Dependency Injection)
        studentService = context.getBean(StudentService.class);
        courseService = context.getBean(CourseService.class);
        feeService = context.getBean(FeeService.class);

        scanner = new Scanner(System.in);

        System.out.println("==============================================");
        System.out.println("   ONLINE STUDENT MANAGEMENT SYSTEM");
        System.out.println("   Spring + Hibernate Mini Project");
        System.out.println("==============================================\n");

        // Add some sample data
        initializeSampleData();

        // Main menu loop
        boolean exit = false;
        while (!exit) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        manageStudents();
                        break;
                    case 2:
                        manageCourses();
                        break;
                    case 3:
                        manageFeePayments();
                        break;
                    case 4:
                        viewReports();
                        break;
                    case 5:
                        exit = true;
                        System.out.println("\nThank you for using Student Management System!");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
            }

            if (!exit) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
        ((AnnotationConfigApplicationContext) context).close();
    }

    private static void displayMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Fee Payment & Refund");
        System.out.println("4. View Reports");
        System.out.println("5. Exit");
        System.out.println("================================");
    }

    private static void manageStudents() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add New Student");
        System.out.println("2. Enroll Student in Course");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. View All Students");
        System.out.println("6. View Student Details");
        System.out.println("7. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                enrollStudent();
                break;
            case 3:
                updateStudent();
                break;
            case 4:
                deleteStudent();
                break;
            case 5:
                viewAllStudents();
                break;
            case 6:
                viewStudentDetails();
                break;
            case 7:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void manageCourses() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. Add New Course");
        System.out.println("2. Update Course");
        System.out.println("3. Delete Course");
        System.out.println("4. View All Courses");
        System.out.println("5. View Course Details");
        System.out.println("6. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                addCourse();
                break;
            case 2:
                updateCourse();
                break;
            case 3:
                deleteCourse();
                break;
            case 4:
                viewAllCourses();
                break;
            case 5:
                viewCourseDetails();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void manageFeePayments() {
        System.out.println("\n--- Fee Payment & Refund ---");
        System.out.println("1. Process Payment");
        System.out.println("2. Process Refund");
        System.out.println("3. View Payment History");
        System.out.println("4. View Fee Summary");
        System.out.println("5. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                processPayment();
                break;
            case 2:
                processRefund();
                break;
            case 3:
                viewPaymentHistory();
                break;
            case 4:
                viewFeeSummary();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void viewReports() {
        System.out.println("\n--- Reports ---");
        System.out.println("1. Students by Course");
        System.out.println("2. All Students with Details");
        System.out.println("3. All Courses with Details");
        System.out.println("4. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                viewStudentsByCourse();
                break;
            case 2:
                viewAllStudents();
                break;
            case 3:
                viewAllCourses();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // Student Operations
    private static void addStudent() {
        System.out.println("\n=== Add New Student ===");
        scanner.nextLine(); // consume newline

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        studentService.addStudent(name, email);
    }

    private static void enrollStudent() {
        System.out.println("\n=== Enroll Student in Course ===");

        Long studentId = getLongInput("Enter Student ID: ");
        Long courseId = getLongInput("Enter Course ID: ");

        studentService.enrollStudentInCourse(studentId, courseId);
    }

    private static void updateStudent() {
        System.out.println("\n=== Update Student ===");
        scanner.nextLine(); // consume newline

        Long studentId = getLongInput("Enter Student ID: ");
        scanner.nextLine(); // consume newline

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new email: ");
        String email = scanner.nextLine();

        studentService.updateStudent(studentId, name, email);
    }

    private static void deleteStudent() {
        System.out.println("\n=== Delete Student ===");

        Long studentId = getLongInput("Enter Student ID: ");

        System.out.print("Are you sure? (yes/no): ");
        scanner.nextLine(); // consume newline
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            studentService.deleteStudent(studentId);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void viewAllStudents() {
        System.out.println("\n=== All Students ===");
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private static void viewStudentDetails() {
        System.out.println("\n=== Student Details ===");
        Long studentId = getLongInput("Enter Student ID: ");

        Student student = studentService.getStudent(studentId);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found!");
        }
    }

    // Course Operations
    private static void addCourse() {
        System.out.println("\n=== Add New Course ===");
        scanner.nextLine(); // consume newline

        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();

        Integer duration = getIntInput("Enter duration (in months): ");
        Double fee = getDoubleInput("Enter fee: ");

        courseService.addCourse(courseName, duration, fee);
    }

    private static void updateCourse() {
        System.out.println("\n=== Update Course ===");

        Long courseId = getLongInput("Enter Course ID: ");
        scanner.nextLine(); // consume newline

        System.out.print("Enter new course name: ");
        String courseName = scanner.nextLine();

        Integer duration = getIntInput("Enter new duration (in months): ");
        Double fee = getDoubleInput("Enter new fee: ");

        courseService.updateCourse(courseId, courseName, duration, fee);
    }

    private static void deleteCourse() {
        System.out.println("\n=== Delete Course ===");

        Long courseId = getLongInput("Enter Course ID: ");

        System.out.print("Are you sure? (yes/no): ");
        scanner.nextLine(); // consume newline
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            courseService.deleteCourse(courseId);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void viewAllCourses() {
        System.out.println("\n=== All Courses ===");
        List<Course> courses = courseService.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            for (Course course : courses) {
                System.out.println(course);
            }
        }
    }

    private static void viewCourseDetails() {
        System.out.println("\n=== Course Details ===");
        Long courseId = getLongInput("Enter Course ID: ");

        Course course = courseService.getCourse(courseId);
        if (course != null) {
            System.out.println(course);
        } else {
            System.out.println("Course not found!");
        }
    }

    // Fee Operations
    private static void processPayment() {
        System.out.println("\n=== Process Payment ===");

        Long studentId = getLongInput("Enter Student ID: ");
        Double amount = getDoubleInput("Enter payment amount: ");

        scanner.nextLine(); // consume newline
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        feeService.processPayment(studentId, amount, description);
    }

    private static void processRefund() {
        System.out.println("\n=== Process Refund ===");

        Long studentId = getLongInput("Enter Student ID: ");
        Double amount = getDoubleInput("Enter refund amount: ");

        scanner.nextLine(); // consume newline
        System.out.print("Enter reason: ");
        String reason = scanner.nextLine();

        feeService.processRefund(studentId, amount, reason);
    }

    private static void viewPaymentHistory() {
        System.out.println("\n=== Payment History ===");
        Long studentId = getLongInput("Enter Student ID: ");

        List<Payment> payments = feeService.getPaymentHistory(studentId);

        if (payments.isEmpty()) {
            System.out.println("No payment history found.");
        } else {
            for (Payment payment : payments) {
                System.out.println(payment);
            }
        }
    }

    private static void viewFeeSummary() {
        System.out.println("\n=== Fee Summary ===");
        Long studentId = getLongInput("Enter Student ID: ");

        feeService.displayFeeSummary(studentId);
    }

    private static void viewStudentsByCourse() {
        System.out.println("\n=== Students by Course ===");
        Long courseId = getLongInput("Enter Course ID: ");

        List<Student> students = studentService.getStudentsByCourse(courseId);

        if (students.isEmpty()) {
            System.out.println("No students enrolled in this course.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // Utility methods
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Invalid input! " + prompt);
        }
        return scanner.nextInt();
    }

    private static Long getLongInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextLong()) {
            scanner.next();
            System.out.print("Invalid input! " + prompt);
        }
        return scanner.nextLong();
    }

    private static Double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            scanner.next();
            System.out.print("Invalid input! " + prompt);
        }
        return scanner.nextDouble();
    }

    private static void initializeSampleData() {
        try {
            // Add sample courses
            courseService.addCourse("Java Full Stack Development", 6, 45000.0);
            courseService.addCourse("Python Data Science", 5, 40000.0);
            courseService.addCourse("Web Development", 4, 35000.0);

            // Add sample students
            studentService.addStudent("Rahul Sharma", "rahul.sharma@email.com");
            studentService.addStudent("Priya Singh", "priya.singh@email.com");
            studentService.addStudent("Amit Kumar", "amit.kumar@email.com");

            // Enroll students
            studentService.enrollStudentInCourse(1L, 1L);
            studentService.enrollStudentInCourse(2L, 2L);
            studentService.enrollStudentInCourse(3L, 3L);

            System.out.println("Sample data initialized successfully!\n");
        } catch (Exception e) {
            System.out.println("Sample data already exists or error: " + e.getMessage());
        }
    }
}