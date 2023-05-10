package com.example.elearningmad.SingleCoursePage.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elearningmad.R
import com.example.elearningmad.SingleCoursePage.Model.WeekData


class WeekAdapter(private val empList: ArrayList<WeekData>) :
    RecyclerView.Adapter<WeekAdapter.ViewHolder>(){

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.add_recycleview, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEmp = empList[position]

        holder.descvription.text = currentEmp.description
        holder.about.text = currentEmp.title
    }

    override fun getItemCount(): Int {
        return empList.size
    }

    class ViewHolder(itemView: View, listener: OnItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        val descvription: TextView = itemView.findViewById(R.id.descvription)
        val about: TextView = itemView.findViewById(R.id.about)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

}