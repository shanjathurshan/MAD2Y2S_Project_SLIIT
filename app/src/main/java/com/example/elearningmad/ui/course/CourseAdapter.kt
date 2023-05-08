package com.example.elearningmad.ui.course

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elearningmad.CourseModel
import com.example.elearningmad.R

class CourseAdapter (private val courseList: ArrayList<CourseModel>) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    private lateinit var mListner: onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListner: onItemClickListner) {
        mListner = clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseAdapter.ViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_course_list_item, parent, false)
        return ViewHolder(itemView, mListner)
    }

    override fun onBindViewHolder(holder: CourseAdapter.ViewHolder, position: Int) {
        val currentEmp = courseList[position]
        holder.tvCourseName.text = currentEmp.cName
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    class ViewHolder(itemView: View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
        val tvCourseName : TextView = itemView.findViewById(R.id.textView)

        init{
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }


    }

}