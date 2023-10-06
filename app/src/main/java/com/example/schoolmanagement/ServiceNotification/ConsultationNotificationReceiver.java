package com.example.schoolmanagement.ServiceNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.schoolmanagement.Entity.Consultation;

public class ConsultationNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Изпращане на нотификация за следващата консултация
        ConsultationService consultationService = new ConsultationService();
        Consultation consultation = consultationService.getConsultationData();
        if (consultation != null && consultationService.isUpcomingConsultation(consultation)) {
            consultationService.showNotification("Upcoming Consultation", "You have an upcoming consultation: " + consultation.getDescription());
        }
    }
}