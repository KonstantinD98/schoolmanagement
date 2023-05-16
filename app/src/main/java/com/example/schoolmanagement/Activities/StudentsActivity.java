package com.example.schoolmanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolmanagement.ConnectionClass;
import com.example.schoolmanagement.MainActivity;
import com.example.schoolmanagement.R;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentsActivity extends AppCompatActivity {
    EditText ETfirstNS, ETlastNS, ETgenderS, ETphoneS, ETemailS, ETgrade;
    Spinner spinnerS;
    Button btnAddS, btnBackS;
    TextView statusS;
    Connection con;
    Statement stmt;
    int result = 0;
    String q = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        ETfirstNS = findViewById(R.id.ETfirstNS);
        ETlastNS = findViewById(R.id.ETlastNS);
        ETgenderS = findViewById(R.id.ETgenderS);
        ETphoneS = findViewById(R.id.ETphoneS);
        ETemailS = findViewById(R.id.ETemailS);
        ETgrade = findViewById(R.id.ETgrade);
        spinnerS = findViewById(R.id.spinnerS);
        btnAddS = findViewById(R.id.btnAddS);
        btnBackS = findViewById(R.id.btnBackS);
        statusS = findViewById(R.id.statusS);
        btnBackS.setOnClickListener(onClick);
        fillSpinner();
        btnAddS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstNameS,lastNameS,genderS,phoneS,emailS,grade, classT;
                firstNameS = ETfirstNS.getText().toString();
                lastNameS = ETlastNS.getText().toString();
                genderS = ETgenderS.getText().toString();
                phoneS = ETphoneS.getText().toString();
                emailS = ETemailS.getText().toString();
                grade = ETgrade.getText().toString();
                String selectedTeacherName = spinnerS.getSelectedItem().toString();
                int teacherID = teacherMap.get(selectedTeacherName);

                try {con = connectionClass(ConnectionClass.un.toString(),ConnectionClass.pass.toString(),ConnectionClass.db.toString(),
                        ConnectionClass.ip.toString());
                    if (con != null ){
                        q = "insert into StudentTable(first_name, last_name, gender, phone, email, class, teacherID) values('"+firstNameS+"','"+lastNameS+"','"+genderS+"','"+phoneS+"','"
                                +emailS+"','"+grade+"','"+teacherID+"')";
                        stmt = con.createStatement();
                        result = stmt.executeUpdate(q);
                        if (result == 1){
                            Toast.makeText(StudentsActivity.this, "Record Inserted", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(StudentsActivity.this, "Record NOT Inserted", Toast.LENGTH_LONG).show();

                            clean();
                        }
                    }

                }catch (Exception e){
                    Log.e("Error",e.getMessage());
                }
            }
        });
    }
    private HashMap<String, Integer> teacherMap;

    private void fillSpinner() {
        try {
            con = connectionClass(ConnectionClass.un.toString(),ConnectionClass.pass.toString(),ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            String query = "SELECT * FROM TeacherTable";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> teacherNames = new ArrayList<String>();
            teacherMap = new HashMap<>();
            while(rs.next()){
                int teacherID = rs.getInt("teacherID");
                String name = rs.getString("first_nameT");
                teacherNames.add(name);
                teacherMap.put(name,teacherID);
            }
            ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_list_item_1,teacherNames);
            spinnerS.setAdapter(array);


        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void clean(){
        ETfirstNS.setText("");
        ETlastNS.setText("");
        ETgenderS.setText("");
        ETphoneS.setText("");
        ETemailS.setText("");
        ETgrade.setText("");
        spinnerS.setSelection(0);
    }
       private View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;

                switch (view.getId()) {
                    case R.id.btnBackS:
                        intent = new Intent(StudentsActivity.this, MainActivity.class);
                        break;
                }
                startActivity(intent);
            }
        };
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



