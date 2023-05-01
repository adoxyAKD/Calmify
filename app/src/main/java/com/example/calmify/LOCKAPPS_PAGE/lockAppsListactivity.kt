package com.example.calmify.LOCKAPPS_PAGE

import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.example.calmify.BuildConfig
import com.example.calmify.MainActivity
import com.example.calmify.R
import com.example.calmify.databinding.ActivityLockAppsListactivityBinding
import com.example.calmify.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timerTask

private enum class DialogReason {
    NO_REASON,
    PASSWORD,
    SERVICE
}
class lockAppsListactivity : AppCompatActivity() {


    private lateinit var binding: ActivityLockAppsListactivityBinding

    private val sharedPreferences : SharedPreferences
        get() = getSharedPreferences("prefs", Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLockAppsListactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.idConfermBLockAppBtn.setOnClickListener{
            Toast.makeText(this, "still need to work on block app", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }

        scheduleInitialChecks()




        val timer = Timer()
//
//        // TODO LATER might be able to get rid of the timer task
        timer.scheduleAtFixedRate(timerTask { runOnUiThread {
            //Installed app list
            maybeGenerateList()
        }}, 0, 10000) // ms

    }


    // APP LIST RELATED
    private val appList
        get() = findViewById<LinearLayout>(R.id.app_list)
    private fun clearList() {
        appList.removeAllViews()
    }
    private fun addListItem(applicationInfo: ApplicationInfo) {
        // TODO LATER remove settings from the list too?
        if (applicationInfo.packageName == BuildConfig.APPLICATION_ID || applicationInfo.packageName == "com.android.settings")
            return

        val item = View.inflate(this, R.layout.app_list_item, null)

        val checkBox = item.findViewById<CheckBox>(R.id.app_list_item_checkbox)

        val pkgName = applicationInfo.packageName
        checkBox.isChecked = Util.getAppBlocked(sharedPreferences, pkgName)
        checkBox.text = Util.nameOfApp(this, applicationInfo)

        checkBox.setOnCheckedChangeListener { _, checked ->
            Util.setAppBlocked(sharedPreferences, pkgName, checked)
            Toast.makeText(this, "app name:"+pkgName, Toast.LENGTH_SHORT).show()
        }

        appList.addView(item)
    }
    private fun generateList() {
        clearList()
        for (app in Util.currentlyInstalledApps(this))
            addListItem(app)
    }

    private var currentlyListed = emptySet<String>()
    private fun maybeGenerateList() {
        val pkgList = Util.getAppPkgNameList(this)
        if (pkgList != currentlyListed) {
            generateList()
            currentlyListed = pkgList
        }
    }


    // INITIAL SETUP RELATED
    private var currentDialog: AlertDialog? = null
    private var currentDialogReason = DialogReason.NO_REASON
    private val dialogShowing: Boolean
        get() = currentDialog?.isShowing == true
    private fun scheduleInitialChecks() {
        val timer = Timer()
        timer.scheduleAtFixedRate(timerTask { runOnUiThread {
            if (!initialChecks())
                timer.cancel()
        }}, 0, 100) // ms
    }
    private fun initialChecks(): Boolean { // returns true if we should continue running the timer for checks
        if (dialogShowing) {
            if (currentDialogReason == DialogReason.SERVICE && serviceIsRunning ) {
                currentDialog?.cancel()
                // otherwise, just let the dialog be and wait until the user is done

//                val timer = Timer()
//                timer.scheduleAtFixedRate(timerTask {
//                    runOnUiThread {
//                        Installed app list
//                        maybeGenerateList()
//                    }
//                }, 0, 10000)

            }else {
                return true
            }
        }

        // now we know there's no dialog showing; let's see if we should put one up
//        if (!serviceIsRunning) {
//            currentDialog = Util.makeAlert(
//                this,
//                R.string.service_alert,
//                R.string.service_alert_button,
//                { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) })
//            currentDialogReason = DialogReason.SERVICE
//            return true
//        }

        return false
    }


    // SERVICE RELATED
    private val serviceIsRunning: Boolean
        get() {
            val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

            @Suppress("DEPRECATION")
            // still works, just only returns services running for this app (which is all we need)
            for (service in manager.getRunningServices(Int.MAX_VALUE))
                if (LockService::class.java.name == service.service.className)
                    return true
            return false
        }

}