package com.hostel.mess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Hostel Mess Management System
 * Implements OOP principles and Spring Boot best practices
 */
@SpringBootApplication
public class MessManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessManagementSystemApplication.class, args);
        System.out.println("🚀 Hostel Mess Management System Started Successfully!");
        System.out.println("📱 Access the API at: http://localhost:8081");
        System.out.println("🗄️  H2 Database Console: http://localhost:8081/h2-console");
    }
}
