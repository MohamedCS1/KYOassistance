package com.example.kyoassistance.ui.emailResponse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.kyoassistance.databinding.ActivityChoseMailBinding

class ChoseMailActivity : AppCompatActivity() {

    lateinit var binding:ActivityChoseMailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoseMailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.buttonGmail.setOnClickListener {
            startActivity(Intent(this , LoginByGmailActivity::class.java))
        }

        binding.buttonOutlook.setOnClickListener {
            Toast.makeText(this ,"has not yet been implemented" ,Toast.LENGTH_SHORT).show()
        }

        binding.buttonEchange.setOnClickListener {
            Toast.makeText(this ,"has not yet been implemented" ,Toast.LENGTH_SHORT).show()
        }

        binding.buttonYahoo.setOnClickListener {
            Toast.makeText(this ,"has not yet been implemented" ,Toast.LENGTH_SHORT).show()
        }
    }
}