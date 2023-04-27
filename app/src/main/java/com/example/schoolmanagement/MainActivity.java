package com.example.schoolmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.schoolmanagement.Activities.ClassesActivity;
import com.example.schoolmanagement.Activities.ConsultationsActivity;
import com.example.schoolmanagement.Activities.StudentsActivity;
import com.example.schoolmanagement.Activities.TeacherActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "").toString();
        Toast.makeText(getApplicationContext(),"Welcome "+username, Toast.LENGTH_SHORT).show();

        Button btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        CardView students = findViewById(R.id.students);
        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(MainActivity.this, StudentsActivity.class));
            }
        });
        CardView teachers = findViewById(R.id.teachers);
        teachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(MainActivity.this, TeacherActivity.class));
            }
        });
        CardView calsses = findViewById(R.id.classes);
        calsses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(MainActivity.this, ClassesActivity.class));
            }
        });
        CardView consult = findViewById(R.id.consult);
        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(MainActivity.this, ConsultationsActivity.class));
            }
        });
    }
}