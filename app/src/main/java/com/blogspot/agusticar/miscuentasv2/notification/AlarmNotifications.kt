package com.blogspot.agusticar.miscuentasv2.notification



import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.blogspot.agusticar.miscuentasv2.MainActivity
import com.blogspot.agusticar.miscuentasv2.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AlarmNotifications:BroadcastReceiver() {


    companion object{
        const val ALARM_LIMIT_NOTIFICATION=1
        const val ALARM_BALANCE=2
        const val REPORT_DAYRY=3
        const val REPORT_WEEKLY=4
        const val REPORT_MONTLY=5
    }

    override fun onReceive(context: Context, intent: Intent?) {
        // Verifica si el intent no es nulo y contiene datos adicionales (si es necesario)
        if (intent != null) {
            val notificationType = intent.getIntExtra("notificationType", -1)
            val message = intent.getStringExtra("message")

            // Asegúrate de tener un valor válido para notificationType
            if (notificationType != -1) {
                createNotification(context, message.orEmpty(), notificationType)
            }
        }
    }


    private fun createNotification(context: Context, str:String, notificationType: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(context, MainActivity.CHANEL_NOTIFICATION)
            .setSmallIcon(R.drawable.contabilidad)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)


        when (notificationType) {
            ALARM_LIMIT_NOTIFICATION -> {
                val bigTextStyle = NotificationCompat.BigTextStyle()
                    .bigText(str)

                notificationBuilder
                    .setContentTitle(context.getString(R.string.hightexpenses))
                    .setContentText(str)
                    .setStyle(bigTextStyle)
            }
            ALARM_BALANCE -> {
                val bigTextStyle = NotificationCompat.BigTextStyle()
                    .bigText(str)

                notificationBuilder
                    .setContentTitle(context.getString(R.string.lowbalance))
                    .setContentText(str)
                    .setStyle(bigTextStyle)
            }
            REPORT_DAYRY -> {
                val bigTextStyle = NotificationCompat.BigTextStyle()
                    .bigText(str)
                val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                notificationBuilder
                    .setContentTitle(context.getString(R.string.daylyreport) +": $currentDate")
                    .setContentText(str)
                    .setStyle(bigTextStyle)
                           }
            REPORT_WEEKLY -> {
                val bigTextStyle = NotificationCompat.BigTextStyle()
                    .bigText(str)
                notificationBuilder
                    .setContentTitle(context.getString(R.string.weekreport))
                    .setContentText(str)
                    .setStyle(bigTextStyle)
            }
            REPORT_MONTLY -> {
                val bigTextStyle = NotificationCompat.BigTextStyle()
                    .bigText(str)
                notificationBuilder
                    .setContentTitle(context.getString(R.string.monthreport))
                    .setContentText(str)
                    .setStyle(bigTextStyle)
            }
            else -> {
                // Puedes agregar un caso predeterminado o manejar otros tipos de notificaciones según sea necesario
            }
        }

        val notification = notificationBuilder.build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationType, notification)
    }

}