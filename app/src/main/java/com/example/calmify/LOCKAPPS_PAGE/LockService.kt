package com.example.calmify.LOCKAPPS_PAGE

import android.accessibilityservice.AccessibilityService
import android.content.*
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.*
import com.example.calmify.BuildConfig


class LockService : AccessibilityService() {

    private val sharedPreferences : SharedPreferences
        get() = getSharedPreferences("prefs", Context.MODE_PRIVATE)

    private var passwordScreen : PasswordScreen? = null
    private val lockReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
//            passwordScreen?.resetUnlocks()
        }
    }

    override fun onServiceConnected() {
        passwordScreen = PasswordScreen(this, sharedPreferences)
        detectLock()
    }

    override fun onDestroy() {
        super.onDestroy()
        passwordScreen?.hide()
        unregisterReceiver(lockReceiver)
        stopForeground(true)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // TODO LATER it covers up the home button (and doesnt work even if you use the simulated one)

        val pkgName = event?.packageName?.toString()
        if (event?.eventType == TYPE_WINDOW_STATE_CHANGED
            && event.source?.isVisibleToUser == true
            && pkgName != null
            && pkgName != BuildConfig.APPLICATION_ID) {

//            if (Util.getAppBlocked(sharedPreferences, pkgName) && Util.getLockStatus(sharedPreferences))
            if (Util.getAppBlocked(sharedPreferences, pkgName))
                passwordScreen?.showForId(pkgName)
            else
                passwordScreen?.hide()
        }
    }

    override fun onInterrupt() {}

    private fun detectLock() {
        registerReceiver(lockReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
    }
}