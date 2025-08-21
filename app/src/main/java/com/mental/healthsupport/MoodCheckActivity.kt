package com.mental.healthsupport

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Charles Raj I on 07/07/25
 * @project Mental Health Support
 * @author Charles Raj
 */

class MoodCheckActivity  : AppCompatActivity() {


    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()

    lateinit var selectedMoodImgView : ImageView
    lateinit var yourMoodText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_check_activity)

        val loveSileLinLay = findViewById<LinearLayout>(R.id.love_smile_lin_lay)
        val happySileLinLay = findViewById<LinearLayout>(R.id.happy_smile_lin_lay)
        val okaySileLinLay = findViewById<LinearLayout>(R.id.okay_smile_lin_lay)
        val awSileLinLay = findViewById<LinearLayout>(R.id.aw_smile_lin_lay)
        val sadSileLinLay = findViewById<LinearLayout>(R.id.sad_smile_lin_lay)
        selectedMoodImgView = findViewById<ImageView>(R.id.selected_mood_img_view)
        yourMoodText = findViewById<TextView>(R.id.your_mood_text)
        val moodHistoryText =findViewById<TextView>(R.id.mood_history_txt)

        val haCard = findViewById<CardView>(R.id.ha_card)
        val resourceCard = findViewById<CardView>(R.id.resource_card)
        val chatCard = findViewById<CardView>(R.id.chat_card)

        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        getTodayMood()

        loveSileLinLay.setOnClickListener {
           saveMood(R.drawable.love_smile, "Very Happy")
        }
        happySileLinLay.setOnClickListener {
            saveMood(R.drawable.happy_face, "Happy")
        }

        okaySileLinLay.setOnClickListener {
            saveMood(R.drawable.okay_smile, "Okay")
        }

        awSileLinLay.setOnClickListener {
            saveMood(R.drawable.aw, "Aww")
        }

        sadSileLinLay.setOnClickListener {
            saveMood(R.drawable.sad, "Sad")
        }

        haCard.setOnClickListener {
            val intent = Intent(this, HealthAssessmentActivity::class.java)
            startActivity(intent)
        }
        resourceCard.setOnClickListener {
            val intent = Intent(this, ResourceActivity::class.java)
            startActivity(intent)
        }

        moodHistoryText.setOnClickListener {
            val intent = Intent(this, MoodHistoryActivity::class.java)
            startActivity(intent)
        }


        logoutBtn.setOnClickListener {
            logout()
        }

    }

    fun logout(){
        auth.signOut()  // sign out from firebase

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun getTodayMood(){
        try {
            // get today's mood
            val userId = auth.currentUser?.uid
            if (userId == null) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                logout()
                return
            }

            val today = SimpleDateFormat(
                "dd-MM-yyyy",
                Locale.getDefault()
            ).format(Date()) // taking todays date

            val moodRef = database.getReference("moods").child(userId)
                .child(today)  // database referance for today

            moodRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        val icon = snapshot.child("mood").getValue(Int::class.java) ?: 0
                        val moodText = snapshot.child("moodText").getValue(String::class.java) ?: ""

                        selectedMoodImgView.setImageResource(icon) //0
                        yourMoodText.text = moodText
                    } else {
                        selectedMoodImgView.setImageResource(R.drawable.happy_face) //0
                        yourMoodText.text = "Happy"

                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }catch (e : Exception){

            Toast.makeText(this, "Error : ${e.message}", Toast.LENGTH_SHORT).show()
        }


    }

    fun saveMood(icon : Int, moodText: String ) {
        // firebase base code

        val userId = auth.currentUser?.uid
        if(userId == null){
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return // stop here
        }
        val today = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        val moodData = mapOf(
            "mood" to icon,
            "moodText" to moodText,
        )

        database.getReference("moods")
            .child(userId)
            .child(today)
            .setValue(moodData)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    selectedMoodImgView.setImageResource(icon) //0
                    yourMoodText.text = moodText
                }

                Toast.makeText(this, "Mood saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error : ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

}