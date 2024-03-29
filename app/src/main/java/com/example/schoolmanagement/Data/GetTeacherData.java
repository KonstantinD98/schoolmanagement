package com.example.schoolmanagement.Data;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import com.example.schoolmanagement.ConnectionClass;
import com.example.schoolmanagement.Entity.Teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetTeacherData {
    Connection con;

    public List<Teacher> GetAllTeachers() {
        List<Teacher> teacherList = new ArrayList<>();

        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from TeacherTable");
            rs.next();
            while (rs.next()) {
                Teacher teacher = new Teacher();

                teacher.setTeacherID(rs.getInt("teacherID"));
                teacher.setTeacherFirstName(rs.getString("first_nameT"));
                teacher.setTeacherLastName(rs.getString("last_nameT"));
                teacher.setTeacherGender(rs.getString("gender"));
                teacher.setTeacherPhone(rs.getString("phone"));
                teacher.setTeacherEmail(rs.getString("email"));
                teacher.setTeacherSpeciality(rs.getString("speciality"));


               teacherList.add(teacher);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacherList;
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

