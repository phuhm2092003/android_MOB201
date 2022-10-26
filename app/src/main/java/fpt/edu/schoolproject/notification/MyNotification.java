package fpt.edu.schoolproject.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;

import fpt.edu.schoolproject.R;

public class MyNotification {
    public static final String CHANNEL_ID = "Fpt School";
    public static void checkSDK(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Fpt School", "Fpt School", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager  = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
    public static void getNotification(Context context, String strNotification){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentTitle("FPT School");
        builder.setContentText(strNotification);
        builder.setColor(context.getResources().getColor(R.color.red));
        builder.setSmallIcon(R.drawable.ic_khoahoc);
        builder.setAutoCancel(true);
        int numberId = (int) ((new Date(). getTime () / 1000L)% Integer.MAX_VALUE);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(numberId, builder.build());
    }
}
