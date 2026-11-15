package in.anirban360.widget

import android.content.Context
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class TrackerSnapshot(val done: Int, val total: Int, val progress: Int, val summary: String, val updated: String)

object TrackerData {
    private const val PREFS = "tracker_settings"
    fun endpoint(context: Context): String = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString("endpoint", "") ?: ""

    fun fetch(context: Context): TrackerSnapshot {
        val endpoint = endpoint(context)
        require(endpoint.startsWith("https://")) { "Set your published HTTPS tracker URL in the app settings." }
        val connection = (URL(endpoint).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"; connectTimeout = 10000; readTimeout = 10000
            setRequestProperty("Accept", "application/json")
        }
        try {
            if (connection.responseCode !in 200..299) error("Tracker server returned HTTP ${connection.responseCode}")
            val json = JSONObject(connection.inputStream.bufferedReader().use { it.readText() })
            val items = json.getJSONArray("items")
            val done = (0 until items.length()).count { items.getJSONObject(it).optString("status") == "done" }
            val total = items.length().coerceAtLeast(1)
            val progress = (done * 100) / total
            val states = (0 until items.length()).map { i ->
                val item = items.getJSONObject(i)
                val state = when (item.optString("status")) {
                    "done" -> "✓"
                    "progress" -> "↗"
                    "blocked" -> "!"
                    "verify" -> "?"
                    else -> "○"
                }
                "${item.optString("name")}:$state"
            }
            return TrackerSnapshot(done, total, progress, states.joinToString("  ·  "), json.optString("updatedAt", "—"))
        } finally { connection.disconnect() }
    }
}
