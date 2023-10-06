package com.example.schoolmanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.app.Dialog;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.os.StrictMode;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schoolmanagement.Adapters.ConsultationAdapter;

import com.example.schoolmanagement.ConnectionClass;
import com.example.schoolmanagement.Data.GetConsultationData;

import com.example.schoolmanagement.Entity.Consultation;

import com.example.schoolmanagement.R;
import com.example.schoolmanagement.ServiceNotification.ConsultationService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

    public class ConsultationsActivity extends AppCompatActivity {
     EditText editTextTitle, editTextDescription, editTextDate;
     Spinner spinnerT, spinnerS;
     Button buttonAddConsult, buttonViewConsult;
     Connection con;
     Consultation consultation;
     ConsultationAdapter consultationAdapter;
     GetConsultationData getConsultationData = new GetConsultationData();
     ListView consultationLine;
     ConsultationService service;

    Statement st ;

    int result = 0;

    ResultSet rs;

    String q = "";
    Dialog dialog;

    private void initDialog(Consultation consultation){
        dialog = new Dialog(ConsultationsActivity.this);

        dialog.setContentView(R.layout.dialog_consult);

        EditText ETidConsultation = dialog.findViewById(R.id.ETidCons);
        Spinner stuSpinner = dialog.findViewById(R.id.stuSpinner);
        Spinner teacherSpinner = dialog.findViewById(R.id.teacherSpinner);
        EditText ETtitle = dialog.findViewById(R.id.ETtitle);
        EditText ETdescription = dialog.findViewById(R.id.ETdescription);
        EditText ETdate = dialog.findViewById(R.id.ETdate);
        Button buttonSaveConsult = dialog.findViewById(R.id.buttonSaveConsult);
        if (consultation != null) {
        ETidConsultation.setText(String.valueOf(consultation.getCosnultationID()));
        ETtitle.setText(consultation.getSubject());
        ETdescription.setText(consultation.getDescription());
        ETdate.setText(consultation.getConsultationDate().toString());
        }
        fillSpinnerStu(stuSpinner, consultation != null ? consultation.getStudentIdCon() : 0);
        fillSpinnerTeach(teacherSpinner, consultation != null ? consultation.getTeacherIdCon() : 0);
        buttonSaveConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cons_id;
                String firstNameS, lastNameT, subject, description,consultation_date;
                result = 0;
                cons_id = Integer.parseInt(ETidConsultation.getText().toString());

                String[] parts = stuSpinner.getSelectedItem().toString().split(" - ");
                firstNameS = parts[1];

                String[] part = teacherSpinner.getSelectedItem().toString().split(" - ");
                lastNameT = part[1];
                subject = ETtitle.getText().toString();
                description = ETdescription.getText().toString();
                consultation_date = ETdate.getText().toString();



                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "update ConsultationTable set studentID='" + firstNameS + "', teacherID ='" +  lastNameT + "', subject='" + subject + "', description ='" +description + "', consultation_date='" + consultation_date + "' where consultationID=" + cons_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(ConsultationsActivity.this, "Record Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ConsultationsActivity.this, "Record NOT Updated", Toast.LENGTH_LONG).show();
                        }
                        cleanConsult();
                        con.close();
                        st.close();

                    }

                } catch (Exception e) {
                    Log.e("Error : ", e.getMessage());
                    Toast.makeText(ConsultationsActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            public void cleanConsult() {
                ETidConsultation.setText("");
                stuSpinner.setSelection(0);
                teacherSpinner.setSelection(0);
                ETtitle.setText("");
                ETdescription.setText("");
                ETdate.setText("");
            }
        });


        Button buttonDeleteConsult = dialog.findViewById(R.id.buttonDeleteConsult);
        buttonDeleteConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cons_id;
                result = 0;
                cons_id = Integer.parseInt(ETidConsultation.getText().toString());
                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(),
                            ConnectionClass.db.toString(),
                            ConnectionClass.ip.toString());
                    if (con != null) {
                        q = "delete from ConsultationTable where consultationID=" + cons_id;
                        st = con.createStatement();
                        result = st.executeUpdate(q);
                        if (result == 1) {
                            Toast.makeText(ConsultationsActivity.this, "Record Deleted", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(ConsultationsActivity.this, "Record NOT Deleted", Toast.LENGTH_LONG).show();
                        }
                        cleanConsult();
                        con.close();
                    }
                }

                catch (Exception e){
                    Log.e("Error : ", e.getMessage());
                }
            }
            public void cleanConsult() {
                ETidConsultation.setText("");
                stuSpinner.setSelection(0);
                teacherSpinner.setSelection(0);
                ETtitle.setText("");
                ETdescription.setText("");
                ETdate.setText("");
            }

        });


    }

     @Override

    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_consultations);

         buttonViewConsult = findViewById(R.id.buttonViewConsult);
         consultationLine = findViewById(R.id.Lvconsult);
         spinnerS = findViewById(R.id.spinnerS);
         spinnerT = findViewById(R.id.spinnerT);
         editTextTitle = findViewById(R.id.editTextTitle);
         editTextDescription = findViewById(R.id.editTextDescription);
         editTextDate = findViewById(R.id.editTextDate);
         buttonAddConsult = findViewById(R.id.buttonAddConsult);
         buttonViewConsult = findViewById(R.id.buttonViewConsult);
         fillSpinnerS();
         fillSpinnerT();
         Intent serviceIntent = new Intent(this, ConsultationService.class);
         startService(serviceIntent);
         initDialog(consultation);


         buttonViewConsult.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         try {
                             con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                                     ConnectionClass.ip.toString());
                             String query = "SELECT * FROM ConsultationTable";
                             PreparedStatement stmt = con.prepareStatement(query);
                             ResultSet rs = stmt.executeQuery();
                             List<Consultation> consultationList = new ArrayList<>();
                             // Retrieve the data from the query result
                             while (rs.next()) {
                                 Consultation consultation = new Consultation();
                                 consultation.setCosnultationID(rs.getInt("consultationID"));
                                 consultation.setStudentIdCon(rs.getInt("studentID"));
                                 consultation.setTeacherIdCon(rs.getInt("teacherID"));
                                 consultation.setSubject(rs.getString("subject"));
                                 consultation.setDescription(rs.getString("description"));
                                 consultation.setConsultationDate(rs.getDate("consultation_date"));

                                 consultationList.add(consultation);
                             }

                             // Close the connection and the query result
                             rs.close();
                             stmt.close();
                             con.close();

                             // Update the ListView on the main thread
                             runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     consultationAdapter = new ConsultationAdapter((Context) ConsultationsActivity.this, (ArrayList<Consultation>) consultationList);
                                     consultationLine.setAdapter(consultationAdapter);
                                 }
                             });
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }
                 }).start();
             }
         });


         consultationLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Consultation consultation = (Consultation) parent.getItemAtPosition(position);
                 Toast.makeText(ConsultationsActivity.this, "You clicked: " + consultation, Toast.LENGTH_SHORT).show();

                 LayoutInflater inflater = LayoutInflater.from(ConsultationsActivity.this);
                 View dialogView = inflater.inflate(R.layout.dialog_consult, null);


                 // Show the dialog
                 initDialog(consultation);
                 dialog.show();

             }

         });

         buttonAddConsult.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String selectedStudentName = spinnerS.getSelectedItem().toString();
                 int studentID = getStudentIDFromName(selectedStudentName);

                 String selectedTeacherName = spinnerT.getSelectedItem().toString();
                 int teacherID = getTeacherIDFromName(selectedTeacherName);

                 String consultationTitle = editTextTitle.getText().toString().trim();
                 String consultationDetails = editTextDescription.getText().toString().trim();
                 String consultationDate = editTextDate.getText().toString().trim();

                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         try {
                             con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                                     ConnectionClass.ip.toString());

                             String query = "INSERT INTO ConsultationTable (studentID, teacherID, subject, description, consultation_date) " +
                                     "VALUES ('" + studentID + "', '" + teacherID + "', '" + consultationTitle + "', '" + consultationDetails + "', '" + consultationDate + "')";
                             PreparedStatement stmt = con.prepareStatement(query);
                             stmt.executeUpdate();

                             stmt.close();
                             con.close();

                             runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     Toast.makeText(getApplicationContext(), "Consultation added successfully", Toast.LENGTH_SHORT).show();
                                 }
                             });
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }
                 }).start();
             }
         });
     }
    private void fillSpinnerT() {
        try {
            con = connectionClass(ConnectionClass.un.toString(),ConnectionClass.pass.toString(),ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            String query = "SELECT * FROM TeacherTable";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> data = new ArrayList<String>();
            while(rs.next()){
                String name = rs.getString("first_nameT");
                data.add(name);
            }
            ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_list_item_1,data);
            spinnerT.setAdapter(array);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }



    private void fillSpinnerS() {
        try {
            con = connectionClass(ConnectionClass.un.toString(),ConnectionClass.pass.toString(),ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            String query = "SELECT * FROM StudentTable";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> data = new ArrayList<String>();
            while(rs.next()){
                String name = rs.getString("first_name");
                data.add(name);
            }
            ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_list_item_1,data);
            spinnerS.setAdapter(array);
        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }
    private int getStudentIDFromName(String studentName) {
        try {
            con = connectionClass(ConnectionClass.un.toString(),ConnectionClass.pass.toString(),ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            String query = "SELECT studentID FROM StudentTable WHERE first_name = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, studentName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("studentID");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    private int getTeacherIDFromName(String teacherName) {
        try {
            con = connectionClass(ConnectionClass.un.toString(),ConnectionClass.pass.toString(),ConnectionClass.db.toString(),
                ConnectionClass.ip.toString());
            String query = "SELECT teacherID FROM TeacherTable WHERE first_nameT = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, teacherName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("teacherID");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    private void fillSpinnerTeach(Spinner spin, int id) {
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
            ArrayAdapter array = new ArrayAdapter(ConsultationsActivity.this, android.R.layout.simple_list_item_1, data);
            spin.setAdapter(array);

            int spinnerPos = array.getPosition(selectionRow);
            spin.setSelection(spinnerPos);

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    private void fillSpinnerStu(Spinner spin, int id) {
        try {
            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            String query = "SELECT * FROM StudentTable";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> data = new ArrayList<String>();

            String selectionRow = "";

            while (rs.next()) {
                int teacherID = rs.getInt("studentID");
                String firstName = rs.getString("first_name");
                String entry = firstName + " - " + teacherID;
                data.add(entry);

                if(teacherID == id){
                    selectionRow = entry;
                }
            }
            ArrayAdapter array = new ArrayAdapter(ConsultationsActivity.this, android.R.layout.simple_list_item_1, data);
            spin.setAdapter(array);

            int spinnerPos = array.getPosition(selectionRow);
            spin.setSelection(spinnerPos);

        } catch (Exception ex) {
            ex.printStackTrace();

        }

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
