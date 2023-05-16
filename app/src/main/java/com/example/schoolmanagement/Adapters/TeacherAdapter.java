package com.example.schoolmanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.schoolmanagement.Entity.Student;
import com.example.schoolmanagement.Entity.Teacher;
import com.example.schoolmanagement.R;

import java.util.ArrayList;

public class TeacherAdapter extends ArrayAdapter<Teacher> {

    Context context;
    ArrayList<Teacher> teacherArrayList;


    public TeacherAdapter(Context context, ArrayList<Teacher> teacherArrayList) {
        super(context, R.layout.teacher_line, teacherArrayList);

        this.context = context;
        this.teacherArrayList = teacherArrayList;


    }
    public class Holder {
        TextView TvfNameT, TVlNameT, TVgenderT,TVphoneT,TVemailT, TVspeciality;
        ImageView imageView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Teacher teacher = getItem(position);
        TeacherAdapter.Holder viewHolder;

        if (convertView == null)
        {
            viewHolder = new TeacherAdapter.Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.teacher_line, parent,false);

            viewHolder.TvfNameT = convertView.findViewById(R.id.TvfNameT);
            viewHolder.TVlNameT = convertView.findViewById(R.id.TVlNameT);
            viewHolder.TVgenderT = convertView.findViewById(R.id.TVgenderT);
            viewHolder.TVphoneT= convertView.findViewById(R.id.TVphoneT);
            viewHolder.TVemailT = convertView.findViewById(R.id.TVemailT);
            viewHolder.TVspeciality = convertView.findViewById(R.id.TVspeciality);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (TeacherAdapter.Holder) convertView.getTag();
        }
        viewHolder.TvfNameT.setText(teacher.getTeacherFirstName());
        viewHolder.TVlNameT.setText(teacher.getTeacherLastName());
        viewHolder.TVgenderT.setText(teacher.getTeacherGender());
        viewHolder.TVphoneT.setText(teacher.getTeacherPhone());
        viewHolder.TVemailT.setText(teacher.getTeacherEmail());
        viewHolder.TVspeciality.setText(teacher.getTeacherSpeciality());
        viewHolder.imageView.setImageResource(R.drawable.teacher_profile);

        return convertView;

    }
}

