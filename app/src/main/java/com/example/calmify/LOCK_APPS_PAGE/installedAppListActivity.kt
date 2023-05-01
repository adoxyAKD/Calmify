package com.example.calmify.LOCK_APPS_PAGE

import android.content.pm.ApplicationInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calmify.PRAYER_PAGE.PrayerInfoModel
import com.example.calmify.PRAYER_PAGE.PrayerInfoModelItem
import com.example.calmify.PRAYER_PAGE.myPrayerAdapter
import com.example.calmify.R
import com.example.calmify.databinding.ActivityInstalledAppListBinding

class installedAppListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInstalledAppListBinding
    val installedAppInfoItem_data = ArrayList<AppListModelItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_installed_app_list)
//
//        binding = ActivityInstalledAppListBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        installedApps()

        val adapter_app = AppListAdapter( installedAppInfoItem_data)
        binding.myInstalledAppRecycler.layoutManager = LinearLayoutManager(this)
        binding.myInstalledAppRecycler.adapter = adapter_app

    }

    private fun installedApps() {

        val list = packageManager.getInstalledPackages(0)
        for (i in list.indices) {
            val packageInfo = list[i]

            if (packageInfo!!.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val appName = packageInfo.applicationInfo.loadLabel(packageManager).toString()
                val appIcon = packageInfo.applicationInfo.loadIcon(packageManager)

                Log.e("App List $i ",appName)
                installedAppInfoItem_data.add(AppListModelItem(appName, appIcon))
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}