package com.mental.healthsupport.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mental.healthsupport.R
import com.mental.healthsupport.model.MoodModel

/**
 * Created by Charles Raj I on 20/08/25
 * @project Mental Health Support
 * @author Charles Raj
 */

class MoodHistoryAdaptor(val moodList : MutableList<MoodModel>)  : RecyclerView.Adapter<MoodHistoryAdaptor.MoodHistoryViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoodHistoryViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.adaptor_mood_history,parent,false)

        return MoodHistoryViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: MoodHistoryViewHolder,
        position: Int
    ) {
     val moodModel = moodList[position]

        holder.moodIcon.setImageResource(moodModel.mood)
        holder.moodText.text = moodModel.moodText
        holder.moodDate.text = moodModel.date

    }

    override fun getItemCount(): Int {
        return moodList.size
    }


    class MoodHistoryViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){
        val moodIcon = itemView.findViewById<ImageView>(R.id.mood_icon)
        val moodText = itemView.findViewById<TextView>(R.id.mood_text)
        val moodDate =  itemView.findViewById<TextView>(R.id.mood_date)
    }


}