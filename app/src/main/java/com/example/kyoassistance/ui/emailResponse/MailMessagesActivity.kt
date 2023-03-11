package com.example.kyoassistance.ui.emailResponse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kyoassistance.R
import com.example.kyoassistance.databinding.ActivityMailMessagesBinding

class MailMessagesActivity : AppCompatActivity() {
    lateinit var binding:ActivityMailMessagesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMailMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.stephanieEmail.setOnClickListener {
            val intent = Intent(this ,MailGenerationActivity::class.java)
            intent.putExtra("mailContent" ,"I hope this email finds you well. It's been a while since we last spoke, and I just wanted to check in and see how you're doing.\n" +
                    "\n" +
                    "I wanted to share some exciting news with you - I recently got accepted into the graduate program I applied for! It's been a long journey, but I'm thrilled to finally be pursuing my passion.\n" +
                    "\n" +
                    "I also wanted to thank you for your support and encouragement along the way. Your belief in me has meant so much, and I couldn't have done it without you.\n" +
                    "\n" +
                    "Let's catch up soon and celebrate together!\n" +
                    "\n" +
                    "Best regards,\n" +
                    "Stephanie")
            startActivity(intent)
        }

        binding.bankEmail.setOnClickListener {
            val intent = Intent(this ,MailGenerationActivity::class.java)
            intent.putExtra("mailContent" ,"We hope this email finds you well. We wanted to inform you about an update to your bank account.\n" +
                    "\n" +
                    "Our records show that there has been a recent transaction on your account that we want to verify with you. Please note that this is a standard security measure to ensure the safety of your funds.")
            startActivity(intent)
        }

        binding.taylorGreyEmail.setOnClickListener {
            val intent = Intent(this ,MailGenerationActivity::class.java)
            intent.putExtra("mailContent" ,"I hope this email finds you well. I wanted to reach out and thank you for your support of my music. Your kind words and enthusiasm mean the world to me, and I am grateful for your continued support.")
            startActivity(intent)
        }

        binding.secretKissShopEmail.setOnClickListener {
            val intent = Intent(this ,MailGenerationActivity::class.java)
            intent.putExtra("mailContent" ,"We hope this email finds you well. We wanted to let you know about our latest promotion that we think you'll be interested in.\n" +
                    "\n" +
                    "For a limited time only, we're offering a 20% discount on all items in our store. Whether you're looking for new clothes, accessories, or home decor, we have something for everyone.\n" +
                    "\n")
            startActivity(intent)
        }

        binding.bankEmail1.setOnClickListener {
            val intent = Intent(this ,MailGenerationActivity::class.java)
            intent.putExtra("mailContent" ,"We hope this email finds you well. We wanted to inform you about an update to your bank account.\n" +
                    "\n" +
                    "Our records show that there has been a recent transaction on your account that we want to verify with you. Please note that this is a standard security measure to ensure the safety of your funds.")
            startActivity(intent)
        }

        binding.stephanieEmail1.setOnClickListener {
            val intent = Intent(this ,MailGenerationActivity::class.java)
            intent.putExtra("mailContent" ,"I hope this email finds you well. It's been a while since we last spoke, and I just wanted to check in and see how you're doing.\n" +
                    "\n" +
                    "I wanted to share some exciting news with you - I recently got accepted into the graduate program I applied for! It's been a long journey, but I'm thrilled to finally be pursuing my passion.\n" +
                    "\n" +
                    "I also wanted to thank you for your support and encouragement along the way. Your belief in me has meant so much, and I couldn't have done it without you.\n" +
                    "\n" +
                    "Let's catch up soon and celebrate together!\n" +
                    "\n" +
                    "Best regards,\n" +
                    "Stephanie")
            startActivity(intent)
        }

        binding.secretKissShopEmail1.setOnClickListener {
            val intent = Intent(this ,MailGenerationActivity::class.java)
            intent.putExtra("mailContent" ,"We hope this email finds you well. We wanted to let you know about our latest promotion that we think you'll be interested in.\n" +
                    "\n" +
                    "For a limited time only, we're offering a 20% discount on all items in our store. Whether you're looking for new clothes, accessories, or home decor, we have something for everyone.\n" +
                    "\n")
            startActivity(intent)
        }


        binding.imageViewBackButton.setOnClickListener {
            onBackPressed()
        }
    }
}