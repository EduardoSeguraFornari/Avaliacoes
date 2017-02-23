package com.fornari.eduardo.avaliacoes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.fornari.eduardo.avaliacoes.bo.NotificacaoBO;
import com.fornari.eduardo.avaliacoes.model.Notificacao;

/**
 * Created by dufor on 23/02/2017.
 */

public class Receiver extends BroadcastReceiver {

    private Notificacao notificacao;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar1 = Calendar.getInstance();
        int horaAtual = calendar1.get(java.util.Calendar.HOUR_OF_DAY);
        int minutoAtual = calendar1.get(java.util.Calendar.MINUTE);

        NotificacaoBO notificacaoBO = new NotificacaoBO(context);
        notificacao = notificacaoBO.buscaNotificacao();
        int horaNotificacao = calendar1.get(java.util.Calendar.HOUR_OF_DAY);
        int minutosNotificacao = calendar1.get(java.util.Calendar.MINUTE);

        calendar1.setTime(notificacao.getHorario());
        if (notificacao.isNotificar() && horaAtual == horaNotificacao && minutoAtual == minutosNotificacao) {
            showNotification(context);
        }
    }

    public void showNotification(Context context) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Avaliações")
                        .setContentText("Avaliação chegando!");
        // Creates an explicit intent for an Activity
        Intent intent = new Intent(context, AvaliacaoActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        taskStackBuilder.addParentStack(AvaliacaoActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =
                taskStackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();

        if (notificacao.isSom()) {
            notification.defaults |= Notification.DEFAULT_SOUND;
        }
        if (notificacao.isVibracao()) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        notificationManager.notify(0, notification);
    }
}
