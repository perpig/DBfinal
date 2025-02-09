package com.shu.dto;

import java.math.BigDecimal;

public class StudentSearchDTO {
    private Integer studentId;
    private String studentName;
    private String gender;
    private Integer gradeNumber;
    private String departmentName;
    private String majorName;
    private String email;
    private BigDecimal grade; // 成绩分数

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getGradeNumber() {
        return gradeNumber;
    }

    public void setGradeNumber(Integer gradeNumber) {
        this.gradeNumber = gradeNumber;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "StudentSearchDTO{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", gender='" + gender + '\'' +
                ", gradeNumber=" + gradeNumber +
                ", departmentName='" + departmentName + '\'' +
                ", majorName='" + majorName + '\'' +
                ", email='" + email + '\'' +
                ", grade=" + grade +
                '}';
    }
}