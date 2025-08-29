package com.mental.healthsupport.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mental.healthsupport.R
import com.mental.healthsupport.model.ChatNode
import com.mental.healthsupport.model.ChatOption
import com.mental.healthsupport.model.Sender

/**
 * Created by Charles Raj I on 25/08/25
 * @project Mental Health Support
 * @author Charles Raj
 */

class ChatAdaptor(
    private val messages : List<ChatNode>,
    private val onOptionClick: (ChatOption) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    companion object{
        const val BOT_VIEW_TYPE = 0
        const val USER_VIEW_TYPE = 1
    }


    override fun getItemViewType(position: Int): Int {
        return if(messages[position].sender == Sender.BOT) BOT_VIEW_TYPE else USER_VIEW_TYPE
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == BOT_VIEW_TYPE){
            val view = inflater.inflate(R.layout.item_bot_message,parent,false)
            BotViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_user_message,parent,false)
            UserViewHolder(view)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        val chat = messages[position]
        when(holder) {
            is BotViewHolder -> holder.bind(chat)
            is UserViewHolder -> holder.bind(chat)
        }

    }

    override fun getItemCount(): Int {
       return messages.size
    }

    inner class BotViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val message = view.findViewById<TextView>(R.id.bot_message)
        private val optionContainer = view.findViewById<LinearLayout>(R.id.option_container)


        fun bind(chat : ChatNode){

            message.text = chat.message
            optionContainer.removeAllViews()

            chat.options.forEach { option ->
                val btn = Button(itemView.context).apply{
                    text = option.label
                    setOnClickListener {
                        onOptionClick(option)
                    }
                }
                optionContainer.addView(btn)
            }

        }

    }

    inner class UserViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val message = view.findViewById<TextView>(R.id.user_message)

        fun bind(chat : ChatNode){

            message.text = chat.message

        }

    }


}