package in.anirban360.widget

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters

class TrackerRefreshWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = try {
        val snapshot = TrackerData.fetch(applicationContext)
        TrackerWidgetProvider.updateAll(applicationContext, "Synced", snapshot)
        Result.success()
    } catch (e: Exception) {
        TrackerWidgetProvider.updateAll(applicationContext, e.message ?: "Sync failed")
        Result.retry()
    }
    companion object {
        fun enqueueOnce(context: Context) {
            WorkManager.getInstance(context).enqueue(OneTimeWorkRequestBuilder<TrackerRefreshWorker>().build())
        }
    }
}
