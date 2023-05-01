package com.example.calmify.PRAYER_PAGE

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calmify.R
import com.example.calmify.databinding.ActivityPpathBinding
import com.example.calmify.databinding.ActivityPpathShowdetailsBinding

class ppathShowdetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityPpathShowdetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPpathShowdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)


        val p_path_title = intent.getStringExtra("ppath_key_title")
        val p_path_desc = intent.getStringExtra("ppath_key_desc")
        binding.idPpathCategoryDetails.setText(p_path_title)
        binding.idPpathDescriptionDetails.setText(p_path_desc)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}