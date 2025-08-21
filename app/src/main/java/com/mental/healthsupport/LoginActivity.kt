package com.mental.healthsupport

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Charles Raj I on 06/07/25
 * @project Mental Health Support
 * @author Charles Raj
 */

class LoginActivity : AppCompatActivity() {


    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()

    /*
    * on create method
    * on start method
    * on resume method
    * on pause method
    * on stop method
    * on destroy method
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_activity)

        val emailEditText = findViewById<EditText>(R.id.email_edt)
        val passwordEditText = findViewById<EditText>(R.id.password_edt)
        val loginBtn = findViewById<Button>(R.id.login_btn)
        val signUpText = findViewById<TextView>(R.id.sign_up_txt)


        if (auth.currentUser != null){
            val intent = Intent(this, MoodCheckActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginBtn.setOnClickListener {

            if (emailEditText.text.toString().isEmpty()){
                Toast.makeText(this, "Email should not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwordEditText.text.toString().isEmpty()){
                Toast.makeText(this, "Password should not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginWithFirebase(
                emailEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        signUpText.setOnClickListener {
            // TODO : add sign up logic , navigate to sign up activity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

        }
    }

    fun loginWithFirebase(email: String, password: String) {

        try {
            auth.signInWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener {  task ->
                if(task.isSuccessful){
                    val intent = Intent(this, MoodCheckActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener { error ->
                Toast.makeText(this, "Error : ${error.message}", Toast.LENGTH_SHORT).show()
            }

        } catch (e : Exception){

        }


    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }


}