package com.example.schoolmanagement.ServiceNotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.schoolmanagement.MainActivity;
import com.example.schoolmanagement.R;

import java.util.Calendar;

import android.app.AlarmManager;

import com.example.schoolmanagement.Data.GetConsultationData;
import com.example.schoolmanagement.Entity.Consultation;

    public class ConsultationService extends Service {

    private static final String CHANNEL_ID = "ConsultationServiceChannel";
    private static final int NOTIFICATION_ID = 123;
    private Consultation consultation;
    private GetConsultationData getConsultationData;

    @Override
    public void onCreate() {
        super.onCreate();
        consultation = new Consultation();
        getConsultationData = new GetConsultationData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Стартиране на услугата в преден план и създаване на основната нотификация
        startForeground(NOTIFICATION_ID, createNotification("Consultation Service is running"));

        // Вземане на предстоящите консултации от базата данни
        consultation = getConsultationData();

        // Проверка дали има предстояща консултация и показване на съответната нотификация
        if (isUpcomingConsultation(consultation)) {
            showNotification("Upcoming Consultation", "You have an upcoming consultation: " + consultation.getDescription());
        }

        // Планиране на следващата консултация
        scheduleNextConsultationNotification();

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Consultation getConsultationData() {
        // Извикване на метода GetConsultation() от класа GetConsultationData
        GetConsultationData consultationData = new GetConsultationData();
        Consultation consultation = consultationData.GetConsultation();
        return consultation;
    }

    private void scheduleNextConsultationNotification() {
        // Вземане на текущата дата и час
        Calendar currentDateTime = Calendar.getInstance();

        // Вземане на датата и часа на следващата консултация
        Calendar nextConsultationDateTime = Calendar.getInstance();
        nextConsultationDateTime.setTime(consultation.getConsultationDate());

        // Нулиране на часа, минутите, секундите и милисекундите на текущата дата и час
        currentDateTime.set(Calendar.HOUR_OF_DAY, 0);
        currentDateTime.set(Calendar.MINUTE, 0);
        currentDateTime.set(Calendar.SECOND, 0);
        currentDateTime.set(Calendar.MILLISECOND, 0);

        // Нулиране на часа, минутите, секундите и милисекундите на датата и часа на следващата консултация
        nextConsultationDateTime.set(Calendar.HOUR_OF_DAY, 0);
        nextConsultationDateTime.set(Calendar.MINUTE, 0);
        nextConsultationDateTime.set(Calendar.SECOND, 0);
        nextConsultationDateTime.set(Calendar.MILLISECOND, 0);

        // Проверка дали следващата консултация е в бъдеще
        if (nextConsultationDateTime.after(currentDateTime)) {
            // Изчисляване на времето между текущата дата и час и следващата консултация
            long timeInMillis = nextConsultationDateTime.getTimeInMillis() - currentDateTime.getTimeInMillis();

            // Планиране на изпращане на нотификация за следващата консултация
            Intent notificationIntent = new Intent(this, ConsultationNotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeInMillis, pendingIntent);
        }
    }

    boolean isUpcomingConsultation(Consultation currentConsultation) {
        // Получаване на текущата дата
        Calendar currentDate = Calendar.getInstance();

        // Получаване на датата на консултацията
        Calendar consultationDate = Calendar.getInstance();
        consultationDate.setTime(currentConsultation.getConsultationDate());

        // Нулиране на часовете, минутите, секундите и милисекундите на текущата дата
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        // Нулиране на часовете, минутите, секундите и милисекундите на датата на консултацията
        consultationDate.set(Calendar.HOUR_OF_DAY, 0);
        consultationDate.set(Calendar.MINUTE, 0);
        consultationDate.set(Calendar.SECOND, 0);
        consultationDate.set(Calendar.MILLISECOND, 0);

        // Проверка дали консултацията е предстояща (равна на текущата дата)
        return consultationDate.equals(currentDate);
    }

    private Notification createNotification(String contentText) {
        createNotificationChannel();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Consultation Service")
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Consultation Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    void showNotification(String title, String contentText) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}