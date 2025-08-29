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


class SignUpActivity : AppCompatActivity() {


    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.signup_activity)

        val nameEditText = findViewById<EditText>(R.id.signup_name_edt)
        val emailEditText = findViewById<EditText>(R.id.signup_email_edt)
        val passwordEditText = findViewById<EditText>(R.id.signup_password_edt)
        val confirmPasswordEditText = findViewById<EditText>(R.id.signup_confirm_password_edt)

        val createAccountBtn = findViewById<Button>(R.id.create_account_btn)
        val alreadyHaveAccountTxt = findViewById<TextView>(R.id.already_have_account_txt)


        createAccountBtn.setOnClickListener {

            if(nameEditText.text.toString().isEmpty()){
                nameEditText.error = "Name should not be empty"
                Toast.makeText(this, "Name should not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(emailEditText.text.toString().isEmpty()){
                emailEditText.error = "Email should not be empty"
                Toast.makeText(this, "Email should not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(passwordEditText.text.toString().isEmpty()){
                passwordEditText.error = "Password should not be empty"
                Toast.makeText(this, "Password should not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwordEditText.text.toString() != confirmPasswordEditText.text.toString()){
                confirmPasswordEditText.error = "Passwords do not match"
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create Account for user

            signUp(
                nameEditText.text.toString(),
                emailEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        alreadyHaveAccountTxt.setOnClickListener {
            // Navigate to login activity
            // TODO : add sign up logic , navigate to sign up activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun signUp(
        name: String,
        email: String,
        password: String,
    ) {


        try {
           auth.createUserWithEmailAndPassword(
               email,
               password
           ).addOnCompleteListener { task ->

                   if(task.isSuccessful){
                       val userId = auth.currentUser?.uid ?: ""
                        val userMap = mapOf(
                            "name" to name,
                            "email" to email,
                            "userId" to userId
                        )
                       database.getReference("Users").child(userId)
                           .setValue(userMap)
                           .addOnCompleteListener {

                               val intent = Intent(this, MoodCheckActivity::class.java)
                               startActivity(intent)
                               finish()
                           }
                           .addOnFailureListener {
                               Toast.makeText(this, "Error : ${it.message}", Toast.LENGTH_SHORT).show()
                           }
                   }
                }
               .addOnFailureListener {
                Toast.makeText(this, "Error : ${it.message}", Toast.LENGTH_SHORT).show()
               }
        } catch (e : Exception){

        }
    }
}