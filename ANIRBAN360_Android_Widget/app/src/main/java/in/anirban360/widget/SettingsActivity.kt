package in.anirban360.widget

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class SettingsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pad = (18 * resources.displayMetrics.density).toInt()
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(pad,pad,pad,pad); setBackgroundColor(0xFF101318.toInt()) }
        root.addView(TextView(this).apply { text = "ANIRBAN 360 Widget"; textSize = 22f; setTextColor(0xFFF5B84B.toInt()) })
        root.addView(TextView(this).apply { text = "Paste the published tracker GET endpoint (HTTPS). Example: https://anrban360.floot.app/_api/tracker"; setTextColor(0xFFFFFFFF.toInt()); setPadding(0,pad,0,pad) })
        val field = EditText(this).apply {
            hint = "https://…/_api/tracker"; setSingleLine(true); setTextColor(0xFFFFFFFF.toInt()); setHintTextColor(0xFF9AA3B2.toInt())
            setText(TrackerData.endpoint(this@SettingsActivity))
        }
        root.addView(field, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        root.addView(Button(this).apply {
            text = "Save URL & Sync"
            setOnClickListener {
                val value = field.text.toString().trim()
                if (!value.startsWith("https://") || !value.endsWith("/_api/tracker")) {
                    Toast.makeText(this@SettingsActivity, "Enter the published HTTPS URL ending in /_api/tracker", Toast.LENGTH_LONG).show()
                } else {
                    getSharedPreferences("tracker_settings", MODE_PRIVATE).edit().putString("endpoint", value).apply()
                    TrackerRefreshWorker.enqueueOnce(this@SettingsActivity)
                    Toast.makeText(this@SettingsActivity, "Saved. Add the widget from your Home Screen.", Toast.LENGTH_LONG).show()
                }
            }
        })
        root.addView(TextView(this).apply {
            text = "Add widget: long-press an empty area of your Android Home Screen → Widgets → ANIRBAN 360 Widget. Updates automatically about every 30 minutes; tap ↻ to refresh."
            setTextColor(0xFFB9C1CD.toInt()); setPadding(0,pad,0,0)
        })
        setContentView(root)
    }
}
