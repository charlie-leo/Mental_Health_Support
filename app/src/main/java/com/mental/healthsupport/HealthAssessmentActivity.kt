package com.mental.healthsupport

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mental.healthsupport.model.AssessmentModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Charles Raj I on 12/07/25
 * @project Mental Health Support
 * @author Charles Raj
 */

class HealthAssessmentActivity : AppCompatActivity() {


    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()

    lateinit var question1 : TextView
    lateinit var answer1 : EditText
    lateinit var question2 : TextView
    lateinit var answer2 :  RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_assessment_activity)

        val backButton = findViewById<ImageView>(R.id.back_button)
         question1 = findViewById<TextView>(R.id.question_1)
         answer1 = findViewById<EditText>(R.id.answer_1)
         question2 = findViewById<TextView>(R.id.question_2)
         answer2 = findViewById<RadioGroup>(R.id.great_day_radio_group)
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
            val assessmentModel = AssessmentModel(
                question1.text.toString(),
                answer1Text,
                question2.text.toString(),
                answer2Text
            )

            saveForm(assessmentModel)
        }
    }

    private fun saveForm(assessmentModel: AssessmentModel) {
        try {

            val userId = auth.currentUser?.uid  // userID
            if(userId == null){
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                // log out
                startActivity(Intent(this, LoginActivity::class.java))
                return
            }

            val today = SimpleDateFormat(
                "dd-MM-yyyy",
                Locale.getDefault(),
            ).format(Date())

            val timeStamp = System.currentTimeMillis()

            database
                .getReference("assessment")
                .child(userId)
                .child(timeStamp.toString())
                .setValue(assessmentModel)
                .addOnCompleteListener {
                    answer1.setText("")
                    answer2.clearCheck()
                    Toast.makeText(this, "Assessment saved", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Assessment not saved", Toast.LENGTH_SHORT).show()
                }

        } catch (e : Exception){
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}