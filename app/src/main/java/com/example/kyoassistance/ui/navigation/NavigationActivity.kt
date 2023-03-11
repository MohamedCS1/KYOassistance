package com.example.kyoassistance.ui.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kyoassistance.R
import com.example.kyoassistance.databinding.ActivityNavigationBinding
import com.example.kyoassistance.ui.chat.MainActivity

class NavigationActivity : AppCompatActivity() {

    lateinit var binding: ActivityNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonToChat.setOnClickListener {
            startActivity(Intent(this ,MainActivity::class.java))
        }
    }
}