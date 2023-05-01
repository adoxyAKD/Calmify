package com.example.calmify


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import android.widget.*
import androidx.databinding.DataBindingUtil

import com.example.calmify.LOCK_APPS_PAGE.installedAppListActivity
import com.example.calmify.NOTE_PAGE.NotePageActivity
import com.example.calmify.PRAYER_PAGE.PrayerPageActivity

import com.example.calmify.WEBVIEW_PAGE.webviewAssignment
import com.example.calmify.databinding.ActivityMainBinding


import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseAuth: FirebaseAuth

    lateinit var  googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)



        firebaseAuth = Firebase.auth
//        firebaseAuth = FirebaseAuth.getInstance()

        //for google:=> getting google signIn option:
        val googleSignInOption_gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestEmail().requestIdToken(getString(R.string.client_id))
            .build()
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOption_gso)

        binding.idInstalledAppList.setOnClickListener {
//            val intent = Intent(this, lockAppsListactivity::class.java)
            val intent = Intent(this, installedAppListActivity::class.java)
            startActivity(intent)
        }
        binding.idNotePage.setOnClickListener {
            val intent = Intent(this, NotePageActivity::class.java)
            startActivity(intent)
        }
        binding.idPrayerPage.setOnClickListener {
            val intent = Intent(this, PrayerPageActivity::class.java)
            startActivity(intent)
        }

        binding.idWebViewPage.setOnClickListener {
//            val intent = Intent(this, webviewActivity::class.java)
            val intent = Intent(this, webviewAssignment::class.java)
            startActivity(intent)
        }
        binding.idFloatingButtonPage.setOnClickListener {
//            val intent = Intent(this, myFloatingWidget::class.java)
//            startActivity(intent)
        }
    }


    // FOR Menu option in main activity:
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting_id -> {
                Toast.makeText(applicationContext, "click on setting", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.profile_id -> {
//                Toast.makeText(applicationContext, "click on profile", Toast.LENGTH_LONG).show()
                val intent = Intent(this, proflileActivity::class.java)
                startActivity(intent)
                return true

            }
            R.id.logout_id -> {
                Toast.makeText(applicationContext, "logout", Toast.LENGTH_LONG).show()
                firebaseAuth.signOut()
                googleSignInClient.signOut().addOnCompleteListener(this) {
                    val intent = Intent(this, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()

                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }
}