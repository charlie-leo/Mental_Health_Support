package com.mental.healthsupport

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Charles Raj I on 12/07/25
 * @project Mental Health Support
 * @author Charles Raj
 */

class HealthAssessmentActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_assessment_activity)

        val backButton = findViewById<ImageView>(R.id.back_button)
        val answer1 = findViewById<EditText>(R.id.answer_1)
        val answer2 = findViewById<RadioGroup>(R.id.great_day_radio_group)
        val submitButton = findViewById<Button>(R.id.submit_button)


        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        submitButton.setOnClickListener {

            val answer1Text = answer1.text.toString()
            var answer2Text = ""
            when (answer2.checkedRadioButtonId) {
                R.id.yes_button -> {
                    answer2Text = "Yes"
                }
                R.id.no_button -> {
                    answer2Text = "No"
                }
            }

            val testText = "The answer 1 is $answer1Text and the answer 2 is $answer2Text"
            Toast.makeText(this, testText, Toast.LENGTH_SHORT).show()

        }


    }

}