package com.example.schoolmanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.schoolmanagement.Classes.NineClassActivity;
import com.example.schoolmanagement.ConnectionClass;
import com.example.schoolmanagement.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ConsultationsActivity extends AppCompatActivity {
     EditText editTextStudent, editTextTeacher, editTextTitle, editTextDescription, editTextDate;
     Button buttonAddConsult, buttonViewConsult;
     Connection con;
    private ListView consultListView;

    private ArrayList<String> consultList = new ArrayList<>();

    private ArrayAdapter<String> arrayAdapter;

    Statement st ;

    int result = 0;

    ResultSet rs;

    String q = "";


     @Override

    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_consultations);
         consultListView = findViewById(R.id.Lvconsult);
         arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, consultList);
         consultListView.setAdapter(arrayAdapter);
         buttonViewConsult = findViewById(R.id.buttonViewConsult);
         consultListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
         editTextStudent = findViewById(R.id.editTextStudent);
         editTextTeacher = findViewById(R.id.editTextTeacher);
         editTextTitle = findViewById(R.id.editTextTitle);
         editTextDescription = findViewById(R.id.editTextDescription);
         editTextDate = findViewById(R.id.editTextDate);
         buttonAddConsult = findViewById(R.id.buttonAddConsult);
         buttonViewConsult = findViewById(R.id.buttonViewConsult);
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

                             // Retrieve the data from the query result
                             while (rs.next()) {
                                 String firstNameStudent = rs.getString("first_nameS");
                                 String firstNameTeacher = rs.getString("last_nameT");
                                 String subject = rs.getString("subject");
                                 String description = rs.getString("description");
                                 String consultation_date = rs.getString("consultation_date");
                                 String consultation = "FirstNameStudent: " + firstNameStudent + "\nLastNameTeacher : " + firstNameTeacher + "\nAbout subject: " + subject
                                         + "\nDescription: " + description + "\nOn Date: " + consultation_date;
                                 consultList.add(consultation);
                             }

                             // Close the connection and the query result
                             rs.close();
                             stmt.close();
                             con.close();

                             // Update the ListView on the main thread
                             runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     arrayAdapter.notifyDataSetChanged();
                                 }
                             });
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }
                 }).start();
             }
         });
         consultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 String selectedItem = (String) parent.getItemAtPosition(position);
                 Toast.makeText(ConsultationsActivity.this, "You clicked: " + selectedItem, Toast.LENGTH_SHORT).show();

                 LayoutInflater inflater = LayoutInflater.from(ConsultationsActivity.this);
                 View dialogView = inflater.inflate(R.layout.dialog_consult, null);

                 // Set up the dialog builder
                 AlertDialog.Builder builder = new AlertDialog.Builder(ConsultationsActivity.this);
                 builder.setView(dialogView);
                 EditText ETidConsultation = dialogView.findViewById(R.id.ETidCons);
                 EditText ETstuNameCon = dialogView.findViewById(R.id.ETstuNameCon);
                 EditText ETteachNameCon = dialogView.findViewById(R.id.ETteachNameCon);
                 EditText ETtitle = dialogView.findViewById(R.id.ETtitle);
                 EditText ETdescription = dialogView.findViewById(R.id.ETdescription);
                 EditText ETdate = dialogView.findViewById(R.id.ETdate);
                 Button buttonSaveConsult = dialogView.findViewById(R.id.buttonSaveConsult);
                 buttonSaveConsult.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         int cons_id;
                         String firstNameS, lastNameT, subject, description,consultation_date;
                         result = 0;
                         cons_id = Integer.parseInt(ETidConsultation.getText().toString());
                         firstNameS = ETstuNameCon.getText().toString();
                         lastNameT = ETteachNameCon.getText().toString();
                         subject = ETtitle.getText().toString();
                         description = ETdescription.getText().toString();
                         consultation_date = ETdate.getText().toString();



                         try {
                             con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                                     ConnectionClass.ip.toString());
                             if (con != null) {
                                 q = "update ConsultationTable set first_nameS='" + firstNameS + "', last_nameT ='" +  lastNameT + "', subject='" + subject + "', description ='" +description + "', consultation_date='" + consultation_date + "' where consultationID=" + cons_id;
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
                         ETstuNameCon.setText("");
                         ETteachNameCon.setText("");
                         ETtitle.setText("");
                         ETdescription.setText("");
                         ETdate.setText("");
                     }
                 });


                Button buttonDeleteConsult = dialogView.findViewById(R.id.buttonDeleteConsult);
                buttonDeleteConsult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cons_id;
                        result = 0;
                        cons_id = Integer.parseInt(ETidConsultation.getText().toString());
                        try {
                            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
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
                        ETstuNameCon.setText("");
                        ETteachNameCon.setText("");
                        ETtitle.setText("");
                        ETdescription.setText("");
                        ETdate.setText("");
                    }

                });

                // Show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }

        });

        buttonAddConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentName = editTextStudent.getText().toString().trim();
                String teacherName = editTextTeacher.getText().toString().trim();
                String consultationTitle = editTextTitle.getText().toString().trim();
                String consultationDetails = editTextDescription.getText().toString().trim();
                String consultationDate = editTextDate.getText().toString().trim();

                if (!TextUtils.isEmpty(studentName) && !TextUtils.isEmpty(teacherName)
                        && !TextUtils.isEmpty(consultationDate) && !TextUtils.isEmpty(consultationTitle)
                        && !TextUtils.isEmpty(consultationDetails)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                                        ConnectionClass.ip.toString());

                                String query = "INSERT INTO ConsultationTable (first_nameS, last_nameT, subject, description, consultation_date) " +
                                        "VALUES ('" + studentName + "', '" + teacherName + "', '" + consultationTitle + "', '" + consultationDetails + "', '" + consultationDate + "')";
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
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
