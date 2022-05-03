package com.example.fyp.model.student;

import java.util.UUID;

public class Student {
    private UUID id;
    private String name;
    private String studentId;
    private String faculty;
    private String email;
    private String password;

    public Student() {
    }

    public Student(UUID id, String name, String studentId, String faculty, String email, String password) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.faculty = faculty;
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", studentId='" + studentId + '\'' +
                ", faculty='" + faculty + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
