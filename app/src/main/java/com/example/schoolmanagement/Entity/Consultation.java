package com.example.schoolmanagement.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Consultation {
    int cosnultationID;
    int studentIdCon;
    int teacherIdCon;
    String subject;
    String description;
    Date consultationDate;

    public Consultation(int studentIdCon, int teacherIdCon, String subject, String description, Date consultationDate) {
        this.studentIdCon = studentIdCon;
        this.teacherIdCon = teacherIdCon;
        this.subject = subject;
        this.description = description;
        this.consultationDate = consultationDate;
    }
    public Consultation() {

    }

    public int getCosnultationID() {
        return cosnultationID;
    }

    public void setCosnultationID(int cosnultationID) {
        this.cosnultationID = cosnultationID;
    }

    public int getStudentIdCon() {
        return studentIdCon;
    }

    public void setStudentIdCon(int studentIdCon) {
        this.studentIdCon = studentIdCon;
    }

    public int getTeacherIdCon() {
        return teacherIdCon;
    }

    public void setTeacherIdCon(int teacherIdCon) {
        this.teacherIdCon = teacherIdCon;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(Date consultationDate) {
        this.consultationDate = consultationDate;
    }
    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        return dateFormat.format(consultationDate);
    }
}
