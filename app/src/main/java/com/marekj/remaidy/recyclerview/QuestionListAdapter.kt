package com.marekj.remaidy.recyclerview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.marekj.remaidy.R
import com.marekj.remaidy.controlpanel.EditQuestion
import com.marekj.restaurantreview.recyclerview.RecyclerViewModel
import java.io.File




class QuestionListAdapter(private val imageModelArrayList: MutableList<RecyclerViewModel>,
                          private val context: Context) :
    RecyclerView.Adapter<QuestionListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.question_row_layout, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]

        val imageFile = File(context.filesDir, info.getImages())
        val imageUri = Uri.fromFile(imageFile)

        holder.questionPicture.setImageURI(imageUri)
        Log.w("URI", imageUri.toString())
        holder.questionName.text = info.getNames()
        holder.questionId = info.getId()
    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var questionPicture = itemView.findViewById<View>(R.id.questionPicture) as ImageView
        var questionName = itemView.findViewById<View>(R.id.questionName) as TextView
        var questionId = "-1"

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            var intent = Intent(itemView.context, EditQuestion::class.java)
            intent.putExtra("id", this.questionId)
            itemView.context.startActivity(intent)
        }
    }
}