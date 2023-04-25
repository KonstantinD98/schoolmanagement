package com.example.schoolmanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolmanagement.ConnectionClass;
import com.example.schoolmanagement.MainActivity;
import com.example.schoolmanagement.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TeacherActivity extends AppCompatActivity {

    EditText ETfirstNT, ETlastNT, ETgenderT, ETphoneT, ETemailT,ETspeciality;
    Button btnAddT, btnBackT;
    Connection con;
    Statement stmt;
    int result = 0;
    String q = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        ETfirstNT = findViewById(R.id.ETfirstNT);
        ETlastNT = findViewById(R.id.ETlastNT);
        ETgenderT = findViewById(R.id.ETgenderT);
        ETphoneT = findViewById(R.id.ETphoneT);
        ETemailT = findViewById(R.id.ETemailT);
        ETspeciality = findViewById(R.id.ETspeciality);
        btnAddT = findViewById(R.id.btnAddT);
        btnBackT = findViewById(R.id.btnBackT);
        btnBackT.setOnClickListener(onClick);
        btnAddT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameT,lastNameT,genderT,phoneT,emailT,speciality;
                firstNameT = ETfirstNT.getText().toString();
                lastNameT = ETlastNT.getText().toString();
                genderT = ETgenderT.getText().toString();
                phoneT = ETphoneT.getText().toString();
                emailT = ETemailT.getText().toString();
                speciality = ETspeciality.getText().toString();

                try {con = connectionClass(ConnectionClass.un.toString(),ConnectionClass.pass.toString(),ConnectionClass.db.toString(),
                        ConnectionClass.ip.toString());
                    if (con != null ){
                        q = "insert into TeacherTable(first_name, last_name, gender, phone, email, speciality) values('"+firstNameT+"','"+lastNameT+"','"+genderT+"','"+phoneT+"','"
                                +emailT+"',"+speciality+")";
                        stmt = con.createStatement();
                        result = stmt.executeUpdate(q);
                        if (result == 1){
                            Toast.makeText(TeacherActivity.this, "Record Inserted", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(TeacherActivity.this, "Record NOT Inserted", Toast.LENGTH_LONG).show();

                            clean();
                        }
                    }

                }catch (Exception e){
                    Log.e("Error",e.getMessage());
                }
            }
        });
    }

    public void clean(){
        ETfirstNT.setText("");
        ETlastNT.setText("");
        ETgenderT.setText("");
        ETphoneT.setText("");
        ETemailT.setText("");
        ETspeciality.setText("");
    }
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;

            switch (view.getId()) {
                case R.id.btnBackT:
                    intent = new Intent(TeacherActivity.this, MainActivity.class);
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
