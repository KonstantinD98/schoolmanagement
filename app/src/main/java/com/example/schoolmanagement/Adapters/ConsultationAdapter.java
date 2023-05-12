package com.example.schoolmanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.schoolmanagement.Entity.Consultation;
import com.example.schoolmanagement.Entity.Student;
import com.example.schoolmanagement.R;

import java.util.ArrayList;

public class ConsultationAdapter extends ArrayAdapter<Consultation> {
    Context context;
    ArrayList<Consultation> consultationArrayList;


    public ConsultationAdapter(Context context, ArrayList<Consultation> consultationArrayList) {
        super(context, R.layout.consultation_line, consultationArrayList);

        this.context = context;
        this.consultationArrayList = consultationArrayList;


    }
    public class Holder {
        TextView tvStudentId, tvTeacherId, tvSubject,tvDescription,tvDate;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Consultation consultation = getItem(position);
        ConsultationAdapter.Holder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ConsultationAdapter.Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.consultation_line, parent,false);

            viewHolder.tvStudentId= convertView.findViewById(R.id.tvStudentName);
            viewHolder.tvTeacherId = convertView.findViewById(R.id.tvTeacherName);
            viewHolder.tvSubject = convertView.findViewById(R.id.tvSubject);
            viewHolder.tvDescription= convertView.findViewById(R.id.tvDescription);
            viewHolder.tvDate = convertView.findViewById(R.id.tvDate);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ConsultationAdapter.Holder) convertView.getTag();
        }
        viewHolder.tvStudentId.setText(String.valueOf(consultation.getStudentIdCon()));
        viewHolder.tvTeacherId.setText(String.valueOf(consultation.getTeacherIdCon()));
        viewHolder.tvSubject.setText(consultation.getSubject());
        viewHolder.tvDescription.setText(consultation.getDescription());
        viewHolder.tvDate.setText(consultation.getConsultationDate().toString());


        return convertView;

    }
}


