package com.example.calmify

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.calmify.UTILS.LoadingDialog
import com.example.calmify.databinding.ActivitySignInBinding
import com.facebook.*
import com.facebook.appevents.AppEventsLogger


import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    val TAG = ""

    //for google
    companion object { const val RC_SIGNIN = 215 }

    //for facebook:
    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.goToSignupPageBtnId.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.signInBtnId.setOnClickListener {
            SignIn()
        }

        binding.googlbtnId.setOnClickListener {
            //we need a client id from google-servives.json and paste it in res.string.xml
            doGoogleSignIn()
        }

        //for google:=> getting google signIn option:
       val googleSignInOption_gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestIdToken(getString(R.string.client_id))  //client id can be seen from (google.servive.json)
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption_gso)


        //for facebook: facebooksdk initialisation
        FacebookSdk.sdkInitialize(applicationContext)
        //        AppEventsLogger.activateApp(this)
        AppEventsLogger.activateApp(Application())

        callbackManager = CallbackManager.Factory.create()
        binding.facebookBtnId.setOnClickListener {
            if (userLoggedIn_FaceBook()) {
                firebaseAuth.signOut()
            } else {
                //buttonFacebookLogin.setReadPermissions("email", "public_profile")
                LoginManager.getInstance()
                    .logInWithReadPermissions(this, listOf("public_profile", "email"))
            }
        }

        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }
            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }
            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        })
    }


    //for already logined users...it will directly enter in app if logined once
    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    //for custom email:
    private fun SignIn() {
        //loading progressbar/dialog
        val loading = LoadingDialog(this)
        loading.startLoading()
        //

        val email = binding.emailId.text.toString()
        val pass = binding.passId.text.toString()

        if (email.isEmpty()) {
            binding.emailId.error = "Email required"
        }
        if (pass.isEmpty()) {
            binding.passId.error = "Password required"
        }

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!email.matches(emailPattern.toRegex())) {
            binding.emailId.error = "Email formate is incorrect \ni.e Abc.xyz_123@xyz.xyz"
//            Toast.makeText(this, "Invalid email", Toast.LENGTH_LONG).show()
        }

        if (pass.length < 8) {
            binding.passId.error = "Min. password length should be 8"
        }


        if (email.isNotEmpty() && pass.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {

                    Toast.makeText(this, "Login successfully", Toast.LENGTH_LONG)
                        .show()
                    loading.isdismiss_progressbar()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //for clearing all stacks of activity opened
                    startActivity(intent)
                    finish()

                } else {
//                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                    loading.isdismiss_progressbar()
                    Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show()
                }
            }

        } else {
            loading.isdismiss_progressbar()
            Toast.makeText(this, "Empty fields are not allowed.", Toast.LENGTH_LONG).show()

        }

    }






    //for both google and facebook authentication:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //for google authentication:
        if (requestCode == RC_SIGNIN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data) //(data = we'll get the user Google mail id data )
            try {
                //After gettting gmail account of google then we'll find the idToken
                val account_gmail = task.getResult(ApiException::class.java)!!
                doAuthentication(account_gmail.idToken!!)

            } catch (e: ApiException) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                //Toast.makeText(this,"my error",Toast.LENGTH_LONG).show()
            }

        }

        //for facebook:
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)

    }


    //for google
    private fun doGoogleSignIn() {
        //A diologbox will appear for google Account selection
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGNIN)
    }

    private fun doAuthentication(idToken: String?) {
        //loading progressbar/dialog
        val loading = LoadingDialog(this)
        loading.startLoading()
        //

        //after getting token , we'll give it to authentication to authenticate:
        val credentials = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credentials)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    //After successful authentication ,it will take u to main activity.
                    Toast.makeText(this, "Successfully login with Google", Toast.LENGTH_LONG).show()
                    loading.isdismiss_progressbar()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    loading.isdismiss_progressbar()
                    Toast.makeText(this, "Google authentication failed", Toast.LENGTH_LONG).show()
                }
            }

    }


    //for facebook authentication:
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        //loading progressbar/dialog
        val loading = LoadingDialog(this)
        loading.startLoading()
        //

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Log.d(TAG, "signInWithCredential:success")
                    // val user = firebaseAuth.currentUser
                    Toast.makeText(baseContext, "Successfully login with Facebook.", Toast.LENGTH_SHORT).show()
                    loading.isdismiss_progressbar()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    loading.isdismiss_progressbar()
                    //Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "failed to authentication with facebook .", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun userLoggedIn_FaceBook(): Boolean {
        if (firebaseAuth.currentUser != null && !AccessToken.getCurrentAccessToken()!!.isExpired) {
            return true
        }
        return false
    }


}