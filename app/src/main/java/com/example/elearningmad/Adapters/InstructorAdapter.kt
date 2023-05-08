package com.example.elearningmad.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elearningmad.DataClasses.Instructor
import com.example.elearningmad.R

class InstructorAdapter (var mList: List<Instructor>) :
    RecyclerView.Adapter<InstructorAdapter.DonationsViewHolder>() {

    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class DonationsViewHolder(itemView: View, listner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val course: TextView = itemView.findViewById(R.id.tvCourse)

        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_instructor_view, parent, false)



        return DonationsViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: DonationsViewHolder, position: Int) {
        holder.name.text = mList[position].name
        holder.course.text = mList[position].course
    }


}