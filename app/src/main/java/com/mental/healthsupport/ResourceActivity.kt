package com.mental.healthsupport

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

/**
 * Created by Charles Raj I on 16/07/25
 * @project Mental Health Support
 * @author Charles Raj
 */

class ResourceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resource_activity)

        val backButton = findViewById<ImageView>(R.id.back_button)

        val counselingServiceBtn = findViewById<Button>(R.id.counseling_service_btn)
        val supportGroupBtn = findViewById<Button>(R.id.support_group_btn)


        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        counselingServiceBtn.setOnClickListener {

            val url = "https://www.ontario.ca/page/find-mental-health-support"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = url.toUri()
            startActivity(intent)

        }

        supportGroupBtn.setOnClickListener {


        }


    }

}