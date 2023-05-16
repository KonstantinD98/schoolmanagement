package com.example.schoolmanagement.Classes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schoolmanagement.Adapters.StudentAdapter;
import com.example.schoolmanagement.ConnectionClass;
import com.example.schoolmanagement.Data.GetStudentData;
import com.example.schoolmanagement.Entity.Student;
import com.example.schoolmanagement.Entity.Teacher;
import com.example.schoolmanagement.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElevenClassActivity extends AppCompatActivity {

    Student student;
    StudentAdapter studentAdapter;
    GetStudentData getStudentData = new GetStudentData();
    ListView studentLine;
    Connection con;
    Statement st ;
    int result = 0;
    ResultSet rs;
    String q = "";
    Dialog dialog;


    private void initDialog(Student student){


        dialog = new Dialog(ElevenClassActivity.this);

        dialog.setContentView(R.layout.dialog_student);

        Button saveButton = dialog.findViewById(R.id.btnSaveS);
        EditText ETfirstNSEdit = dialog.findViewById(R.id.ETfirstNSEdit);
        EditText ETlastNSEdit = dialog.findViewById(R.id.ETlastNSEdit);
        EditText ETgenderSEdit = dialog.findViewById(R.id.ETgenderSEdit);
        EditText ETphoneS = dialog.findViewById(R.id.ETphoneS);
        EditText ETemailSEdit = dialog.findViewById(R.id.ETemailSEdit);
        EditText ETgrade = dialog.findViewById(R.id.ETgrade);
        EditText ETidS = dialog.findViewById(R.id.ETidS);
        Spinner ETclassTEdit = dialog.findViewById(R.id.spinnerEditS);
        Button deleteButton = dialog.findViewById(R.id.btnDeleteS);


        if (student != null) {
            ETidS.setText(String.valueOf(student.getStudentID()));
            ETfirstNSEdit.setText(student.getFirstName());
            ETlastNSEdit.setText(student.getLastName());
            ETgenderSEdit.setText(student.getGender());
            ETphoneS.setText(student.getPhone());
            ETemailSEdit.setText(student.getEmail());
            ETgrade.setText(student.getGrade());
            fillSpinner(ETclassTEdit, student.getTeacherID());
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stu_id;
                result = 0;
                stu_id = Integer.parseInt(ETidS.getText().toString());
                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "delete from StudentTable where studentID=" + stu_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(ElevenClassActivity.this, "Record Deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ElevenClassActivity.this, "Record NOT Deleted", Toast.LENGTH_LONG).show();
                        }
                        cleanStu();
                        con.close();

                    }


                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                }

            }

            public void cleanStu() {
                ETidS.setText("");
                ETfirstNSEdit.setText("");
                ETlastNSEdit.setText("");
                ETgenderSEdit.setText("");
                ETphoneS.setText("");
                ETemailSEdit.setText("");
                ETgrade.setText("");
                ETclassTEdit.setSelection(0);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stu_id;
                String f_StuName, l_StuName, stuGender, stuPhone, stuEmail, stuClass, stuClassT;
                result = 0;
                stu_id = Integer.parseInt(ETidS.getText().toString());
                f_StuName = ETfirstNSEdit.getText().toString();
                l_StuName = ETlastNSEdit.getText().toString();
                stuGender = ETgenderSEdit.getText().toString();
                stuPhone = ETphoneS.getText().toString();
                stuEmail = ETemailSEdit.getText().toString();
                stuClass = ETgrade.getText().toString();
                String[] parts = ETclassTEdit.getSelectedItem().toString().split(" - ");
                stuClassT = parts[1];


                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "update StudentTable set first_name='" + f_StuName + "', last_name ='" + l_StuName + "', gender='" + stuGender + "', phone ='" + stuPhone + "', email='" + stuEmail + "', class='" + stuClass + "', teacherID='" + stuClassT + "' where studentID=" + stu_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(ElevenClassActivity.this, "Record Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ElevenClassActivity.this, "Record NOT Updated", Toast.LENGTH_LONG).show();
                        }
                        cleanStu();
                        con.close();
                        st.close();

                    }

                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                    Toast.makeText(ElevenClassActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            public void cleanStu() {
                ETidS.setText("");
                ETfirstNSEdit.setText("");
                ETlastNSEdit.setText("");
                ETgenderSEdit.setText("");
                ETphoneS.setText("");
                ETemailSEdit.setText("");
                ETgrade.setText("");
                ETclassTEdit.setSelection(0);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleven_class);
        studentLine = findViewById(R.id.LVXI);
        ImageView imgExit = findViewById(R.id.imgExit);

        Button show = findViewById(R.id.btnShowXI);


        initDialog(student);
        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                                    ConnectionClass.ip.toString());
                            String query = "SELECT * FROM StudentTable WHERE class='eleven'";
                            PreparedStatement stmt = con.prepareStatement(query);
                            ResultSet rs = stmt.executeQuery();

                            List<Student> studentList = new ArrayList<>();
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

                            // Close the connection and the query result
                            rs.close();
                            stmt.close();
                            con.close();

                            // Update the ListView on the main thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    studentAdapter = new StudentAdapter((Context) ElevenClassActivity.this, (ArrayList<Student>) studentList);
                                    studentLine.setAdapter(studentAdapter);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        studentLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                student = (Student) parent.getItemAtPosition(position);
                Toast.makeText(ElevenClassActivity.this, "You clicked: " + student, Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = LayoutInflater.from(ElevenClassActivity.this);
                View dialogView = inflater.inflate(R.layout.dialog_student, null);

                // Set up the dialog builder

                initDialog(student);
                dialog.show();

            }
        });

    }
    private void fillSpinner(Spinner spin, int id) {
        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            String query = "SELECT * FROM TeacherTable";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> data = new ArrayList<String>();

            String selectionRow = "";

            while (rs.next()) {
                int teacherID = rs.getInt("TeacherID");
                String firstName = rs.getString("first_nameT");
                String entry = firstName + " - " + teacherID;
                data.add(entry);

                if(teacherID == id){
                    selectionRow = entry;
                }
            }
            ArrayAdapter array = new ArrayAdapter(ElevenClassActivity.this, android.R.layout.simple_list_item_1, data);
            spin.setAdapter(array);

            int spinnerPos = array.getPosition(selectionRow);
            spin.setSelection(spinnerPos);

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    @SuppressLint("NewApi")
    public Connection connectionClass (String user, String password, String database, String server){
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
