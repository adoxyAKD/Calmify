package com.example.calmify

import android.net.Uri
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.calmify.databinding.ActivityProflileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class proflileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProflileBinding
    private lateinit var databaseRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    lateinit var  googleSignInClient: GoogleSignInClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProflileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        fetchData()

        firebaseAuth = Firebase.auth
//        firebaseAuth = FirebaseAuth.getInstance()

        //for google:=> getting google signIn option:
        val googleSignInOption_gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestEmail().requestIdToken(getString(R.string.client_id))
            .build()
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOption_gso)



//        val personGivenName = firebaseAuth.currentUser?

//        val personEmail = firebaseAuth.currentUser?.getEmail()
//        val personId = firebaseAuth.currentUser?.getId()
//        Uri personPhoto = firebaseAuth.currentUser?.getPhotoUrl()
//        val personFamilyName = firebaseAuth.currentUser?.getFamilyName()
//        val personName = firebaseAuth.currentUser?.getDisplayName()

        //getting data from google auth:
        binding.idFnameShow.setText("Name: "+firebaseAuth.currentUser?.getDisplayName())
        binding.idEmailShow.setText("Email: "+firebaseAuth.currentUser?.email)
//        binding.idPNmberShow.setText("Contact no.: "+firebaseAuth.currentUser?.phoneNumber)


    }

    private fun fetchData() {

        databaseRef= FirebaseDatabase.getInstance().getReference("Users")

        if(firebaseAuth.currentUser!=null){
            val currentUser=firebaseAuth.uid.toString()
            databaseRef.child(currentUser).get().addOnSuccessListener {
                if(it.exists()){
                    val fname_show=it.child("firstName").value
                    val lname_show=it.child("lastName").value
                    val pNumber_show=it.child("phoneNumber").value
                    val gender_show=it.child("gender").value
                    val dob_show=it.child("dob").value
                    val email_show=it.child("email").value

                    binding.idFnameShow.text="First name: "+fname_show.toString()
                    binding.idLnameShow.text="Last name: "+lname_show.toString()
                    binding.idPNmberShow.text="Phone number: "+pNumber_show.toString()
                    binding.idGenderShow.text="Gender: "+gender_show.toString()
                    binding.idDobShow.text="D.O.B: "+dob_show.toString()
                    binding.idEmailShow.text="Email: "+email_show.toString()
//

                    Toast.makeText(this, "Profile fetched successfully", Toast.LENGTH_SHORT).show()

                }

            }.addOnFailureListener{
                Toast.makeText(this, "error in getting profile", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "No such profile exist", Toast.LENGTH_SHORT).show()
        }



    }
}