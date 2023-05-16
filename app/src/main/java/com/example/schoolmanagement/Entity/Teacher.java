package com.example.schoolmanagement.Entity;

public class Teacher {
    int teacherID;
    String teacherFirstName;
    String teacherLastName;
    String teacherGender;
    String teacherPhone;
    String teacherEmail;
    private String teacherSpeciality;

    public Teacher(int teacherID, String teacherFirstName, String teacherLastName, String teacherGender, String teacherPhone, String teacherEmail, String teacherSpeciality) {
        this.teacherID = teacherID;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
        this.teacherGender = teacherGender;
        this.teacherPhone = teacherPhone;
        this.teacherEmail = teacherEmail;
        this.teacherSpeciality = teacherSpeciality;
    }

    public Teacher() {

    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    public void setTeacherFirstName(String teacherFirstName) {
        this.teacherFirstName = teacherFirstName;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public void setTeacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
    }

    public String getTeacherGender() {
        return teacherGender;
    }

    public void setTeacherGender(String teacherGender) {
        this.teacherGender = teacherGender;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherSpeciality() {
        return teacherSpeciality;
    }

    public void setTeacherSpeciality(String teacherSpeciality) {
        this.teacherSpeciality = teacherSpeciality;
    }
}
