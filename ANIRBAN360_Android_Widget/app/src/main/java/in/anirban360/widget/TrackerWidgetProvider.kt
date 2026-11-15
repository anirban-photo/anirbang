package in.anirban360.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class TrackerWidgetProvider : AppWidgetProvider() {
    override fun onEnabled(context: Context) {
        val request = PeriodicWorkRequestBuilder<TrackerRefreshWorker>(30, TimeUnit.MINUTES)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork("anirban360-widget-refresh", ExistingPeriodicWorkPolicy.UPDATE, request)
    }
    override fun onDisabled(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork("anirban360-widget-refresh")
    }
    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        ids.forEach { update(context, manager, it, "Updating…") }
        TrackerRefreshWorker.enqueueOnce(context)
    }
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_REFRESH) TrackerRefreshWorker.enqueueOnce(context)
    }
    companion object {
        const val ACTION_REFRESH = "in.anirban360.widget.REFRESH"
        fun update(context: Context, manager: AppWidgetManager, id: Int, message: String, snapshot: TrackerSnapshot? = null) {
            val views = RemoteViews(context.packageName, R.layout.tracker_widget)
            val refresh = Intent(context, TrackerWidgetProvider::class.java).setAction(ACTION_REFRESH)
            val pending = PendingIntent.getBroadcast(context, id, refresh, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(R.id.refresh, pending)
            if (snapshot == null) {
                views.setTextViewText(R.id.summary, message)
            } else {
                views.setTextViewText(R.id.percent, "${snapshot.progress}%")
                views.setTextViewText(R.id.count, "  ${snapshot.done} / ${snapshot.total} completed")
                views.setProgressBar(R.id.progress, 100, snapshot.progress, false)
                views.setTextViewText(R.id.summary, snapshot.summary)
                views.setTextViewText(R.id.updated, "Last sync: ${snapshot.updated}")
            }
            manager.updateAppWidget(id, views)
        }
        fun updateAll(context: Context, message: String, snapshot: TrackerSnapshot? = null) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(ComponentName(context, TrackerWidgetProvider::class.java))
            ids.forEach { update(context, manager, it, message, snapshot) }
        }
    }
}
