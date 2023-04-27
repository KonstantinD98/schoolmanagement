package com.example.schoolmanagement.Classes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.schoolmanagement.ConnectionClass;
import com.example.schoolmanagement.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ElevenClassActivity extends AppCompatActivity {

    private ListView studentListView;
    private ArrayList<String> studentList = new ArrayList<>();

    private ArrayAdapter<String> arrayAdapter;
    Connection con;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleven_class);

        studentListView = findViewById(R.id.LVXI);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        studentListView.setAdapter(arrayAdapter);

        Button show = findViewById(R.id.btnShowXI);
        //Button searchButton = findViewById(R.id.search_button);
        //Button ExitButton = findViewById(R.id.exitView);
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
                            String query = "SELECT * FROM StudentTable WHERE class='eleven'";
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
                Toast.makeText(ElevenClassActivity.this, "You clicked: " + selectedItem, Toast.LENGTH_SHORT).show();

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
