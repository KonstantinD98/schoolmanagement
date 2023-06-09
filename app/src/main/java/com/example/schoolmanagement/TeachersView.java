package com.example.schoolmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.example.schoolmanagement.Adapters.TeacherAdapter;
import com.example.schoolmanagement.Classes.ElevenClassActivity;
import com.example.schoolmanagement.Classes.NineClassActivity;
import com.example.schoolmanagement.Data.GetStudentData;
import com.example.schoolmanagement.Data.GetTeacherData;
import com.example.schoolmanagement.Entity.Student;
import com.example.schoolmanagement.Entity.Teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeachersView extends AppCompatActivity {

    Teacher teacher;
    TeacherAdapter teacherAdapter;
    GetTeacherData getTeacherData = new GetTeacherData();
    ListView teacherLine;
    Connection con;
    Statement st ;
    int result = 0;
    String q = "";

    Dialog dialog;

    private void initDialog(Teacher teacher) {


        dialog = new Dialog(TeachersView.this);

        dialog.setContentView(R.layout.dialog_teacher);

        Button saveButton = dialog.findViewById(R.id.btnSaveT);
        EditText ETfirstNTEdit = dialog.findViewById(R.id.ETfirstNTEdit);
        EditText ETlastNTEdit = dialog.findViewById(R.id.ETlastNTEdit);
        EditText ETgenderTEdit = dialog.findViewById(R.id.ETgenderTEdit);
        EditText ETphoneT = dialog.findViewById(R.id.ETphoneTeach);
        EditText ETemailTEdit = dialog.findViewById(R.id.ETemailTEdit);
        EditText ETidT = dialog.findViewById(R.id.ETidT);
        Spinner spinnerEditT = dialog.findViewById(R.id.spinnerEditT);
        Button deleteButton = dialog.findViewById(R.id.btnDeleteT);


        if (teacher != null) {
            ETidT.setText(String.valueOf(teacher.getTeacherID()));
            ETfirstNTEdit.setText(teacher.getTeacherFirstName());
            ETlastNTEdit.setText(teacher.getTeacherLastName());
            ETgenderTEdit.setText(teacher.getTeacherGender());
            ETphoneT.setText(teacher.getTeacherPhone());
            ETemailTEdit.setText(teacher.getTeacherEmail());
            String[] subjectsArray = getResources().getStringArray(R.array.subjects);
            fillSpinner(spinnerEditT, subjectsArray, String.valueOf(teacher.getTeacherSpeciality()));
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int t_id;
                result = 0;
                t_id = Integer.parseInt(ETidT.getText().toString());
                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "delete from TeacherTable where teacherID=" + t_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(TeachersView.this, "Record Deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(TeachersView.this, "Record NOT Deleted", Toast.LENGTH_LONG).show();
                        }
                        cleanStu();
                        con.close();

                    }


                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                }

            }

            public void cleanStu() {
                ETidT.setText("");
                ETfirstNTEdit.setText("");
                ETlastNTEdit.setText("");
                ETgenderTEdit.setText("");
                ETphoneT.setText("");
                ETemailTEdit.setText("");
                spinnerEditT.setSelection(0);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int t_id;
                String f_TName, l_TName, tGender, tPhone, tEmail;
                result = 0;
                t_id = Integer.parseInt(ETidT.getText().toString());
                f_TName = ETfirstNTEdit.getText().toString();
                l_TName = ETlastNTEdit.getText().toString();
                tGender = ETgenderTEdit.getText().toString();
                tPhone = ETphoneT.getText().toString();
                tEmail = ETemailTEdit.getText().toString();
                final String selectedSpinnerValue = spinnerEditT.getSelectedItem().toString();

                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "update TeacherTable set first_nameT='" + f_TName + "', last_nameT ='" + l_TName + "', gender='" + tGender
                                + "', phone ='" + tPhone + "', email='" + tEmail + "', speciality='" + selectedSpinnerValue + "' where teacherID=" + t_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(TeachersView.this, "Record Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(TeachersView.this, "Record NOT Updated", Toast.LENGTH_LONG).show();
                        }
                        cleanStu();
                        con.close();
                        st.close();
                    }
                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                    Toast.makeText(TeachersView.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            public void cleanStu() {
                ETidT.setText("");
                ETfirstNTEdit.setText("");
                ETlastNTEdit.setText("");
                ETgenderTEdit.setText("");
                ETphoneT.setText("");
                ETemailTEdit.setText("");

                Spinner spinner = findViewById(R.id.spinnerEditT);
                if (spinner != null) {
                    spinner.setSelection(0);
                }
            }
        });
    }
        private void fillSpinner(Spinner spin, String[] subjects, String teacherSubject) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(spin.getContext(), android.R.layout.simple_list_item_1, subjects);
        spin.setAdapter(arrayAdapter);

        int spinnerPos = -1;
        for (int i = 0; i < subjects.length; i++) {
            if (subjects[i].equals(teacherSubject)) {
                spinnerPos = i;
                break;
            }
        }

        if (spinnerPos >= 0) {
            spin.setSelection(spinnerPos);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_view);

        teacherLine = findViewById(R.id.lvTeacher);
        ImageView imgExit = findViewById(R.id.imgExit);

        // SetRecords();

        Button show = findViewById(R.id.btnShow);

        //initDialog(student);
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
                            String query = "SELECT * FROM TeacherTable";
                            PreparedStatement stmt = con.prepareStatement(query);
                            ResultSet rs = stmt.executeQuery();

                            List<Teacher> teacherList = new ArrayList<>();
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

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    teacherAdapter = new TeacherAdapter((Context) TeachersView.this, (ArrayList<Teacher>) teacherList);
                                    teacherLine.setAdapter(teacherAdapter);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        teacherLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                teacher = (Teacher) parent.getItemAtPosition(position);
                Toast.makeText(TeachersView.this, "You clicked: " + teacher, Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = LayoutInflater.from(TeachersView.this);
                View dialogView = inflater.inflate(R.layout.dialog_student, null);

                // Set up the dialog builder
                initDialog(teacher);
                dialog.show();



            }
        });

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
