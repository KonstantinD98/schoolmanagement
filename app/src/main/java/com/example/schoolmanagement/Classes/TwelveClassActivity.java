package com.example.schoolmanagement.Classes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

import com.example.schoolmanagement.ConnectionClass;
import com.example.schoolmanagement.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TwelveClassActivity extends AppCompatActivity {


    private ListView studentListView;
    private ArrayList<String> studentList = new ArrayList<>();

    private ArrayAdapter<String> arrayAdapter;
    Connection con;
    Statement st ;
    int result = 0;
    ResultSet rs;
    String q = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twelve_class);
        studentListView = findViewById(R.id.LVXII);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studentListView.setAdapter(arrayAdapter);

        Button show = findViewById(R.id.btnShowXII);
        studentListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        /*ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NineClassActivity.this, ClassesActivity.class));
            }
        });*/

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                                    ConnectionClass.ip.toString());
                            String query = "SELECT * FROM StudentTable WHERE class='twelve'";
                            PreparedStatement stmt = con.prepareStatement(query);
                            ResultSet rs = stmt.executeQuery();

                            // Retrieve the data from the query result
                            while (rs.next()) {
                                String firstName = rs.getString("first_nameS");
                                String lastName = rs.getString("last_nameS");
                                String gender = rs.getString("gender");
                                String phone = rs.getString("phone");
                                String email = rs.getString("email");
                                String grade = rs.getString("class");
                                String student = "Firstname: " + firstName + "\nLastname: " + lastName + "\nGender: " + gender
                                        + "\nTelephone: " + phone+ "\nEmail: " + email+ "\nClass: " + grade;
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

        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(TwelveClassActivity.this, "You clicked: " + selectedItem, Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = LayoutInflater.from(TwelveClassActivity.this);
                View dialogView = inflater.inflate(R.layout.dialog_student, null);

                // Set up the dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(TwelveClassActivity.this);
                builder.setView(dialogView);

                Button saveButton = dialogView.findViewById(R.id.btnSaveS);
                EditText ETfirstNSEdit = dialogView.findViewById(R.id.ETfirstNSEdit);
                EditText ETlastNSEdit = dialogView.findViewById(R.id.ETlastNSEdit);
                EditText ETgenderSEdit = dialogView.findViewById(R.id.ETgenderSEdit);
                EditText ETphoneS = dialogView.findViewById(R.id.ETphoneS);
                EditText ETemailSEdit = dialogView.findViewById(R.id.ETemailSEdit);
                EditText ETgrade = dialogView.findViewById(R.id.ETgrade);
                EditText ETidS = dialogView.findViewById(R.id.ETidS);
                Spinner ETclassTEdit = dialogView.findViewById(R.id.spinnerEditS);
                Button deleteButton = dialogView.findViewById(R.id.btnDeleteS);
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
                                q = "delete from StudentTable where studentID=" +stu_id;
                                st = con.createStatement();
                                result = st.executeUpdate(q);
                                if (result == 1) {
                                    Toast.makeText(TwelveClassActivity.this, "Record Deleted", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(TwelveClassActivity.this, "Record NOT Deleted", Toast.LENGTH_LONG).show();
                                }
                                cleanStu();
                                con.close();
                            }


                        }

                        catch (Exception e){
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
                        String f_StuName, l_StuName,stuGender,stuPhone, stuEmail,stuClass,stuClassT;
                        result = 0;
                        stu_id = Integer.parseInt(ETidS.getText().toString());
                        f_StuName = ETfirstNSEdit.getText().toString();
                        l_StuName = ETlastNSEdit.getText().toString();
                        stuGender = ETgenderSEdit.getText().toString();
                        stuPhone = ETphoneS.getText().toString();
                        stuEmail = ETemailSEdit.getText().toString();
                        stuClass = ETgrade.getText().toString();
                        stuClassT = String.valueOf(ETclassTEdit.getAdapter().getItemId(1));


                        try {
                            con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(),
                                    ConnectionClass.ip.toString());
                            if (con != null) {
                                q = "update StudentTable set first_nameS='" + f_StuName + "', last_nameS ='" + l_StuName + "', gender='" + stuGender + "', phone ='" +  stuPhone + "', email='" + stuEmail + "', class='" +  stuClass + "', last_nameT='" + stuClassT + "' where studentID=" + stu_id;
                                st = con.createStatement();
                                result = st.executeUpdate(q);
                                if (result == 1) {
                                    Toast.makeText(TwelveClassActivity.this, "Record Updated", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(TwelveClassActivity.this, "Record NOT Updated", Toast.LENGTH_LONG).show();
                                }
                                cleanStu();
                                con.close();
                                st.close();

                            }

                        } catch (Exception e) {
                            Log.e("Error : ", e.getMessage());
                            Toast.makeText(TwelveClassActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    public void cleanStu(){
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


                // Show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();

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
