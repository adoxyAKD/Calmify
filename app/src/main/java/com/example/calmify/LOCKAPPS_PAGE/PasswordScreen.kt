package com.example.calmify.LOCKAPPS_PAGE

import android.app.Service
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import com.example.calmify.R

import kotlin.collections.HashMap


class PasswordScreen(private val context: Context, private val sharedPreferences: SharedPreferences) {


    private val view = View.inflate(context, R.layout.password_screen, null)
    private val params = WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY)
    private val winMgr = context.getSystemService(Service.WINDOW_SERVICE) as WindowManager

    init {
//        Util.listenForGo(view.findViewById(R.id.password)) { onEnter() }
        view.findViewById<Button>(R.id.submit).setOnClickListener {
            onEnter()
        }
        view.findViewById<Button>(R.id.cancel).setOnClickListener {
            Util.goToHomeScreen(context)
            hide()
        }
    }


    private fun onEnter() {
        unlockApp(currentId,
            view.findViewById<CheckBox>(R.id.persistent_check).isChecked,
            view.findViewById<CheckBox>(R.id.unlock_all_check).isChecked)
        hide()

    }


    private var unlockedDict = HashMap<String, Boolean>()
    private var persistentUnlockedDict = HashMap<String, Boolean>()
    private var allUnlocked = false
    private var allUnlockedPersistent = false
    private fun appUnlocked(id: String) : Boolean =
        unlockedDict[id] == true
     || persistentUnlockedDict[id] == true
     || allUnlocked
     || allUnlockedPersistent

    private fun unlockApp(id: String){}
//    for unlocking the App:
    private fun unlockApp(id: String, persistent: Boolean, all: Boolean) {
        if (all && persistent)
            allUnlockedPersistent = true
        else if (all && !persistent)
            allUnlocked = true
        else if (!all && persistent)
            persistentUnlockedDict[id] = true
        else if (!all && !persistent)
            unlockedDict[id] = true
    }

    private var currentId = ""
    fun showForId(id: String) {
//        checkLockNum()
        currentId = id
        if (appUnlocked(id))
            hide()
        else
            show()
    }




    private var viewShown = false
    private fun show() {
        view.requestFocus()
        if (viewShown) return
        viewShown = true
        winMgr.addView(view, params)
    }
    fun hide() {
        if (!viewShown) return
        viewShown = false
        winMgr.removeView(view)
    }


/*
    //for Timer:
    enum class TimerState{
        Stop, Pause, Running
    }
    private lateinit var timer:CountDownTimer
    private var timeLengthSecond=0L
    private var secondRemaining=0L
    private var timerState= TimerState.Stop
    init {
        view.findViewById<Button>(R.id.id_stopBtn).setOnClickListener {
            startTimer()
            timerState= TimerState.Running
            updateTimer()
        }
        view.findViewById<Button>(R.id.id_pauseBtn).setOnClickListener {
            timer.cancel()
            timerState= TimerState.Pause
            updateTimer()
        }
        view.findViewById<Button>(R.id.id_runningBtn).setOnClickListener {
            timer.cancel()
            onTimerFinished()
        }

    }
    private fun startTimer() {
        TODO("Not yet implemented")
    }
    private fun updateTimer() {
        TODO("Not yet implemented")
    }
    private fun onTimerFinished() {
        TODO("Not yet implemented")
    }



 */

}