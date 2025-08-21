package com.mental.healthsupport

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mental.healthsupport.adaptor.MoodHistoryAdaptor
import com.mental.healthsupport.model.MoodModel

/**
 * Created by Charles Raj I on 18/08/25
 * @project Mental Health Support
 * @author Charles Raj
 */


class MoodHistoryActivity : AppCompatActivity() {


    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_history)

        val backButton = findViewById<ImageView>(R.id.back_button)
        val historyRecyclerView = findViewById<RecyclerView>(R.id.history_recyclerview)

        getHistory { moodList ->

            val adaptor = MoodHistoryAdaptor(moodList)
            historyRecyclerView.layoutManager = LinearLayoutManager(this)
            historyRecyclerView.adapter = adaptor

        }

        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    fun getHistory(callback: (MutableList<MoodModel>) -> Unit) {

        val userId = auth.currentUser?.uid
        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // finish the current activity
            return
        }


        val moodRef = database.getReference("moods").child(userId)

        moodRef.addListenerForSingleValueEvent(object  : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                val moodList = mutableListOf<MoodModel>()

                for(moodSnapshot in snapshot.children){
                    val date = moodSnapshot.key ?: continue
                    val mood = moodSnapshot.child("mood").getValue(Int::class.java) ?: R.drawable.happy_face
                    val moodText = moodSnapshot.child("moodText").getValue(String::class.java) ?: "Happy"
                    val moodData = MoodModel(date,mood,moodText)
                    moodList.add(moodData)
                }
                callback(moodList)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
        return
    }

}