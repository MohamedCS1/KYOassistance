package com.example.kyoassistance.ui.emailResponse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kyoassistance.R
import com.example.kyoassistance.adapters.GmailMessagesAdapter
import com.example.kyoassistance.databinding.ActivityMailMessagesBinding
import com.example.kyoassistance.pojo.GmailMessage

class MailMessagesActivity : AppCompatActivity() {
    lateinit var binding:ActivityMailMessagesBinding
    lateinit var gmailMessagesAdapter: GmailMessagesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMailMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gmailMessagesAdapter = GmailMessagesAdapter(this)

        val arrayMessages = arrayListOf<GmailMessage>()

        arrayMessages.add(GmailMessage(R.drawable.photo_profile1 ,"COD Bank" ,"Dear Mohamed,\n" +
                "\n" +
                "We wanted to inform you that your account balance as of [date] is [amount]. If you have any questions or concerns regarding your account, please feel free to contact us.\n" +
                "\n" +
                "Thank you for choosing [Bank Name].\n" +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "COD Bank Customer Support Team ." ,"10:30 AM"))

        arrayMessages.add(
            GmailMessage(R.drawable.photo_profile2,
                "Asil",
                "Subject: Reminder: Team Meeting Tomorrow\n" +
                        "\n" +
                        "Dear Team,\n" +
                        "\n" +
                        "Just a friendly reminder that our weekly team meeting is scheduled for tomorrow at 10 AM in the conference room. We will be discussing the progress on our ongoing projects and upcoming deadlines." ,"1:00 PM")
        )

        arrayMessages.add(GmailMessage(R.drawable.photo_profile3 ,"Ahmed" ,"Dear Mohamed,\n" +
                "\n" +
                "We wanted to take a moment to express our gratitude for your recent purchase from our store. We hope that you are enjoying your new product and that it is meeting all your expectations." ,"11:30 AM"))


        arrayMessages.add(
            GmailMessage(R.drawable.photo_profile4,
                "Yassine",
                "Subject: Invitation to Company Event\n" +
                        "\n" +
                        "Dear Mohamed,\n" +
                        "\n" +
                        "We are excited to invite you to our upcoming company event on 5/14/2023 at 9:00 AM. This event is a great opportunity for you to meet our team, learn more about our company and services, and network with other professionals in the industry." ,"8:42 PM")

        )

        arrayMessages.add(GmailMessage(R.drawable.photo_profile5 ,"Mohamed" ,"Dear Mohamed,\n" +
                "\n" +
                "I am pleased to announce that we have reached a major milestone in our growth strategy, and we are on track to meet our targets for the year. This is a testament to the hard work and dedication of each and every one of you, and I want to thank you for your ongoing commitment to our company." ,"5:23 PM"))


        arrayMessages.add(
            GmailMessage(R.drawable.photo_profile6, "Abdou", "Dear Mohamed,\n" +
                    "\n" +
                    "I wanted to take a moment to congratulate you on your recent promotion to senior android developer. This is a well-deserved achievement, and we are proud to have you as a member of our team.\n" +
                    "\n" +
                    "Your hard work and dedication have not gone unnoticed, and we are confident that you will excel in your new role. We look forward to seeing the positive impact you will make in your new position." ,"8:23 AM")
        )


        gmailMessagesAdapter.setList(arrayMessages)

        binding.recyclerViewGmailMessages.apply {
            adapter = gmailMessagesAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }

        binding.imageViewBackButton.setOnClickListener {
            onBackPressed()
        }
    }
}