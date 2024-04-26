package com.marekj.remaidy.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marekj.remaidy.R
import com.marekj.restaurantreview.recyclerview.QuizHistoryRVModel




class QuizHistoryAdapter(private val imageModelArrayList: MutableList<QuizHistoryRVModel>,
                         private val context: Context) :
    RecyclerView.Adapter<QuizHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.quiz_row_layout, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]

        holder.dateTime.text = info.getTimeDate()
        holder.answeredQuestions.text = info.getAnswers()
        holder.correctAnsweredQuestions.text = info.getCorrectAnswers()
    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var dateTime = itemView.findViewById<View>(R.id.dateTime) as TextView
        var answeredQuestions = itemView.findViewById<View>(R.id.answeredQuestionsTV) as TextView
        var correctAnsweredQuestions = itemView.findViewById<View>(R.id.correctlyAnsweredQuestionsTV) as TextView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {

        }
    }
}