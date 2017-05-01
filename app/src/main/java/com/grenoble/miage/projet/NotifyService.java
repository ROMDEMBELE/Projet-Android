package com.grenoble.miage.projet;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.grenoble.miage.projet.R;
import com.grenoble.miage.projet.data.Ticket;

public class NotifyService extends Service {

    public static final String REFRESH_TIME_INTENT = "REFRESH_TIME_INTENT";
    public static final String TICKET_INTENT = "TICKET_INTENT";
    public static final String KEY_EXTRA_LONG_TIME_IN_MS = "timeInMs";
    private static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private CountDownTimer mCountDownTimer;
    private PendingIntent contentIntent;
    private boolean isRunning = false;

    public NotifyService() {
        super();
    }

    @Override
    public void onCreate() {
        contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isRunning) {
            final Ticket ticket = (Ticket) intent.getSerializableExtra(TICKET_INTENT);
            startTimer(ticket);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void startTimer(final Ticket ticket) {
        mCountDownTimer = new CountDownTimer(ticket.getDureeInit(), 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                isRunning = true;
                Log.i(NotifyService.class.getName(), "Time " + millisUntilFinished / 1000);
                showNotificationTime(ticket,millisUntilFinished);
                sendEvent(ticket,millisUntilFinished);
            }

            @Override
            public void onFinish() {
                isRunning = false;
                Log.i(NotifyService.class.getName(), "onFinish");
                sendEvent(ticket,0L);
                showNotification(ticket);
                NotifyService.this.stopSelf();
            }
        };

        mCountDownTimer.start();
    }

    private void sendEvent(Ticket ticket,long millisUntilFinished) {
        Intent intent = new Intent(REFRESH_TIME_INTENT);
        intent.putExtra(KEY_EXTRA_LONG_TIME_IN_MS, millisUntilFinished);
        intent.putExtra(TICKET_INTENT,ticket);
        sendBroadcast(intent);
    }

    private void showNotification(Ticket ticket) {
        NotificationCompat.Builder notifBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.alert_tiny_ic)
                        .setContentTitle(ticket.toString())
                        .setContentIntent(contentIntent)
                        .setContentText("Temps écoulé !!!");
        mNotificationManager.notify(NOTIFICATION_ID, notifBuilder.build());
    }

    private void showNotificationTime(Ticket ticket,long timeInMs) {
        Integer[] minSec = TimeUtil.getMinSec(timeInMs);
        NotificationCompat.Builder notifBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.alert_tiny_ic)
                        .setContentTitle(ticket.toString())
                        .setContentIntent(contentIntent)
                        .setContentText(getString(R.string.timeleft, minSec[TimeUtil.MINUTE], minSec[TimeUtil.SECOND]));
        mNotificationManager.notify(NOTIFICATION_ID, notifBuilder.build());
    }
}