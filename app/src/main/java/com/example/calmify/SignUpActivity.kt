package com.example.calmify

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.view.isEmpty
import com.example.calmify.UTILS.LoadingDialog
import com.example.calmify.UTILS.USER
import com.example.calmify.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference



    private var dob=""
    private var gender=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.goToSignInPageId.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.signUpBtnId.setOnClickListener {
            SignUp()
        }

        //for Date picking:
        val myCalendar = Calendar.getInstance()
        val datePicker =  DatePickerDialog.OnDateSetListener{ view, year , month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR,year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate_Lable(myCalendar)
        }
        binding.dobId.setOnClickListener{
            DatePickerDialog(this, datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        //for Gender:
        binding.radioGroupId.setOnCheckedChangeListener{ group, checkedId->

            if(checkedId== R.id.male_id){
                Toast.makeText(this, binding.maleId.text.toString(), Toast.LENGTH_LONG).show()
                gender=binding.maleId.text.toString()
            }else if(checkedId== R.id.female_id){
                Toast.makeText(this, binding.femaleId.text.toString(), Toast.LENGTH_LONG).show()
                gender=binding.femaleId.text.toString()
            }else if(checkedId== R.id.other_id){
                Toast.makeText(this, binding.otherId.text.toString(), Toast.LENGTH_LONG).show()
                gender=binding.otherId.text.toString()
            }else if(checkedId== R.id.disclose_id){
                Toast.makeText(this, binding.discloseId.text.toString(), Toast.LENGTH_LONG).show()
                gender=binding.discloseId.text.toString()
            }

        }


    }


    private fun updateDate_Lable(myCalendar: Calendar) {
        val myDateFormate ="dd-MM-yyyy"
        val simpleDataFormate= SimpleDateFormat(myDateFormate)
        dob= simpleDataFormate.format(myCalendar.time)
        binding.dobId.setText("Date of birth : "+dob)

        Toast.makeText(this, "dob: "+dob, Toast.LENGTH_LONG).show()
    }

    private fun SignUp() {

        //loading progressbar/dialog
        val loading = LoadingDialog(this)
        loading.startLoading()
        //

        val fname = binding.firstNameId.text.toString()
        val lname = binding.lastNameId.text.toString()
        val pNumber = binding.phoneNoId.text.toString()
        val email = binding.emailId.text.toString()
        val pass = binding.passId.text.toString()
        val confPass = binding.confermPassId.text.toString()

        if (fname.isEmpty()){ binding.firstNameId.error="First name required" }
        if (lname.isEmpty()){ binding.lastNameId.error="Last name required" }
        if (pNumber.isEmpty() ){ binding.phoneNoId.error="Phone no. required" }
        if (email.isEmpty()){ binding.emailId.error="Email required" }
        if (pass.isEmpty()){ binding.passId.error="Password required" }
        if (confPass.isEmpty()){ binding.confermPassId.error="Confirm password required" }




        //val emailPattern = "[a-zA-Z0-9._-]+@[gmail]+\\.+[com]+"
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!email.matches(emailPattern.toRegex())) {
            binding.emailId.error="Correct Email formate is required  \ni.e Abc.xyz_123@xyz.xyz"
        }

        if(pass.length<8 ){ binding.passId.error="Min. password length is 8" }
        if(confPass!=pass ){ binding.confermPassId.error="password do not match" }


        val gender_empty= binding.radioGroupId.getCheckedRadioButtonId() == -1
        if (gender_empty) {
            Toast.makeText(this, "please selection gender", Toast.LENGTH_LONG).show()
        } else { // one of the radio buttons is checked
        }

        val empty_DOB=binding.dobId.text.toString()
        if(empty_DOB=="Click to select Date of Birth"){
            Toast.makeText(this, "please selection DOB", Toast.LENGTH_LONG).show()
        }else{
//            empty_DOB==binding.dobId.text.toString()
        }




        if (fname.isNotEmpty() && lname.isNotEmpty() && pNumber.isNotEmpty()
            && !gender_empty && empty_DOB!="Click to select Date of Birth"
            && email.isNotEmpty() && pass.isNotEmpty() && confPass.isNotEmpty()) {
            if (pass == confPass) {

                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                            //store data to firebase realtime database:
                            databaseRef= FirebaseDatabase.getInstance().getReference("Users")
                            val User =USER(fname,lname,pNumber,dob, gender,email)//must be in same sequence as in USER.class

                            val currentUser=firebaseAuth.uid.toString()
                            databaseRef.child(currentUser).setValue(User).addOnSuccessListener {
                                Toast.makeText(this, "Signup successfully", Toast.LENGTH_LONG).show()
                                loading.isdismiss_progressbar()

                                val intent = Intent(this, SignInActivity::class.java)
                                startActivity(intent)
                            }.addOnFailureListener{
                                loading.isdismiss_progressbar()
                                Toast.makeText(this, "failed to store data", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            loading.isdismiss_progressbar()
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                loading.isdismiss_progressbar()
//                Toast.makeText(this, "Password is not matching.", Toast.LENGTH_LONG).show()
            }
        }else{
            loading.isdismiss_progressbar()
            Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_LONG)
                .show()
        }

    }
}
