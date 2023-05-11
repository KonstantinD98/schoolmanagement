package com.example.schoolmanagement.Data;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import com.example.schoolmanagement.ConnectionClass;
import com.example.schoolmanagement.Entity.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetStudentData {

    Connection con;

    public List<Student> GetAllStudents() {
        List<Student> studentList = new ArrayList<>();

        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from StudentTable");
            rs.next();
            while (rs.next()) {
                Student student = new Student();

                student.setStudentID(rs.getInt("studentID"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setGender(rs.getString("gender"));
                student.setPhone(rs.getString("phone"));
                student.setEmail(rs.getString("email"));
                student.setGrade(rs.getString("class"));
                student.setTeacherID(rs.getInt("teacherID"));

                studentList.add(student);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
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
