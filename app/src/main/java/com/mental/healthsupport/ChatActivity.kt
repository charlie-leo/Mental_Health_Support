package com.mental.healthsupport

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mental.healthsupport.adaptor.ChatAdaptor
import com.mental.healthsupport.model.ChatDataProvider
import com.mental.healthsupport.model.ChatNode
import com.mental.healthsupport.model.ChatOption
import com.mental.healthsupport.model.Sender


/**
 * Created by Charles Raj I on 24/08/25
 * @project Mental Health Support
 * @author Charles Raj
 */

class ChatActivity : AppCompatActivity() {

    lateinit var chatRecyclerView: RecyclerView
    private lateinit var adaptor: ChatAdaptor
    private val messages = mutableListOf<ChatNode>()
    private val allChatNodes = ChatDataProvider.getAllChats()

    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val timeStamp = System.currentTimeMillis()
    lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatRecyclerView = findViewById<RecyclerView>(R.id.chat_recyclerview)

        val userId = auth.currentUser?.uid ?: ""

        dbRef = FirebaseDatabase
            .getInstance()
            .getReference("chat")
            .child(userId)
            .child(timeStamp.toString())


        adaptor = ChatAdaptor(messages){ selectedOption ->
            // start work when user select option
            onUserSelectOption(selectedOption)
        }
        chatRecyclerView.adapter = adaptor
        chatRecyclerView.layoutManager = LinearLayoutManager(this)

        allChatNodes.find { it.id == 1 }?.let {
            messages.add(it)
            adaptor.notifyDataSetChanged()

            dbRef.push().setValue(it)

        }
    }

    private fun onUserSelectOption(option: ChatOption){

        val userChat = ChatNode(id = -1, message = option.label, sender = Sender.USER)
        messages.add(userChat)
        adaptor.notifyItemInserted(messages.size -1)
        // save the user chat data to firebase
        dbRef.push().setValue(userChat)

        val next = allChatNodes.find { it.id == option.nextId }
        next?.let { nx->

            messages.add(nx)
            adaptor.notifyItemInserted(messages.size -1)
            chatRecyclerView.scrollToPosition(messages.size -1)

            // save the bot chat data to firebase
            dbRef.push().setValue(nx)

        }

    }

}