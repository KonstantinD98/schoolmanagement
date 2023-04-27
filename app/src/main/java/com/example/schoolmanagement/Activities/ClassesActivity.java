package com.example.schoolmanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.schoolmanagement.Classes.ElevenClassActivity;
import com.example.schoolmanagement.Classes.NineClassActivity;
import com.example.schoolmanagement.Classes.TenClassActivity;
import com.example.schoolmanagement.Classes.TwelveClassActivity;
import com.example.schoolmanagement.LoginActivity;
import com.example.schoolmanagement.MainActivity;
import com.example.schoolmanagement.R;

public class ClassesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "").toString();
        Toast.makeText(getApplicationContext(),"Welcome "+username, Toast.LENGTH_SHORT).show();

        Button btnExit = findViewById(R.id.btnExitClass);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(ClassesActivity.this, MainActivity.class));
            }
        });

        CardView nine = findViewById(R.id.nine);
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(ClassesActivity.this, NineClassActivity.class));
            }
        });
        CardView ten = findViewById(R.id.ten);
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(ClassesActivity.this, TenClassActivity.class));
            }
        });
        CardView eleven = findViewById(R.id.eleven);
        eleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(ClassesActivity.this, ElevenClassActivity.class));
            }
        });
        CardView twelve = findViewById(R.id.twelve);
        twelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(ClassesActivity.this, TwelveClassActivity.class));
            }
        });
    }
}