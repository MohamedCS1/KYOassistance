package com.example.kyoassistance.ui.emailResponse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.kyoassistance.R
import com.example.kyoassistance.databinding.ActivityLoginByGmailBinding

class LoginByGmailActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginByGmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginByGmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.buttonLogIn.setOnClickListener {
            startActivity(Intent(this ,MailMessagesActivity::class.java))
        }

    }
}