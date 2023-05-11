package com.example.schoolmanagement.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.Person;
import java.util.ArrayList;
import com.example.schoolmanagement.ConnectionClass;
import com.example.schoolmanagement.Entity.Student;
import com.example.schoolmanagement.R;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Student> {

    Context context;
    ArrayList<Student> studentArrayList;


    public StudentAdapter(Context context, ArrayList<Student> studentArrayList) {
        super(context, R.layout.student_line, studentArrayList);

        this.context = context;
        this.studentArrayList = studentArrayList;


        }
        public class Holder {
        TextView TvfName, TVlName, TVgender,TVphone,TVemail, TVclass;
        }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = getItem(position);
        Holder viewHolder;

        if (convertView == null)
        {
            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.student_line, parent,false);

            viewHolder.TvfName = convertView.findViewById(R.id.TvfName);
            viewHolder.TVlName = convertView.findViewById(R.id.TVlName);
            viewHolder.TVgender = convertView.findViewById(R.id.TVgender);
            viewHolder.TVphone= convertView.findViewById(R.id.TVphone);
            viewHolder.TVemail = convertView.findViewById(R.id.TVemail);
            viewHolder.TVclass = convertView.findViewById(R.id.TVclass);
            convertView.setTag(viewHolder);

            }else {
            viewHolder = (Holder) convertView.getTag();
        }
        viewHolder.TvfName.setText(student.getFirstName());
        viewHolder.TVlName.setText(student.getLastName());
        viewHolder.TVgender.setText(student.getGender());
        viewHolder.TVphone.setText(student.getPhone());
        viewHolder.TVemail.setText(student.getEmail());
        viewHolder.TVclass.setText(student.getGrade());

        return convertView;

    }
    }
