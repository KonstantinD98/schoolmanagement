package com.example.schoolmanagement.Data;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import com.example.schoolmanagement.ConnectionClass;
import com.example.schoolmanagement.Entity.Consultation;
import com.example.schoolmanagement.Entity.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetConsultationData {

    Connection con;
    public Consultation GetConsultation(){
        Consultation consultation = new Consultation();

        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(), ConnectionClass.ip.toString());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ConsultationTable");
            if (rs.next()) {
                consultation = new Consultation();

                consultation.setStudentIdCon(rs.getInt("studentID"));
                consultation.setTeacherIdCon(rs.getInt("teacherID"));
                consultation.setSubject(rs.getString("subject"));
                consultation.setDescription(rs.getString("description"));
                consultation.setConsultationDate(rs.getDate("consultation_date"));
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultation;
    }

    public List<Consultation> GetAllConsultations() {
        List<Consultation> consultationList = new ArrayList<>();

        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from ConsultationTable");
            while (rs.next()) {
                Consultation consultation = new Consultation();

                consultation.setStudentIdCon(rs.getInt("studentID"));
                consultation.setTeacherIdCon(rs.getInt("teacherID"));
                consultation.setSubject(rs.getString("subject"));
                consultation.setDescription(rs.getString("description"));
                consultation.setConsultationDate(rs.getDate("consultation_date"));


                consultationList.add(consultation);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultationList;
    }



    @SuppressLint("NewApi")
    public Connection connectionClass(String user, String password, String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + server + "/" + database + ";user=" + user + ";password=" + password + ";";
            connection = DriverManager.getConnection(connectionURL);
        } catch (Exception e) {
            Log.e("SQL Connection Error : ", e.getMessage());
        }
        return connection;
    }
}

