package com.example.todolistappfirstcopy.tasks.all_tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistappfirstcopy.R
import com.example.todolistappfirstcopy.tasks.Task
import java.text.SimpleDateFormat

class AllTasksRecyclerViewAdapter(private val tasksArrayList: ArrayList<Task>, private val listener: AllTasksRecyclerViewAdapter.onItemClickListener): RecyclerView.Adapter<AllTasksRecyclerViewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTasksRecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.active_task_recycler_view_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AllTasksRecyclerViewAdapter.ViewHolder, position: Int) {
        val task = tasksArrayList[position]
        val selectedDateTimeArrayList = convertMillisToDateTime(task.dateTimeInMillis)
        holder.textViewTitle.text = task.title
        holder.textViewDescription.text = task.description
        holder.textViewDate.text = "${selectedDateTimeArrayList[0]}/${selectedDateTimeArrayList[1]}/${selectedDateTimeArrayList[2]}"
    }

    private fun convertMillisToDateTime(dateTimeInMillis: Long): ArrayList<Int> {
        val tempArrayList = arrayListOf<Int>()
        tempArrayList.add(SimpleDateFormat("yyyy").format(dateTimeInMillis).toInt())
        tempArrayList.add(SimpleDateFormat("MM").format(dateTimeInMillis).toInt())
        tempArrayList.add(SimpleDateFormat("dd").format(dateTimeInMillis).toInt())
        tempArrayList.add(SimpleDateFormat("HH").format(dateTimeInMillis).toInt())
        tempArrayList.add(SimpleDateFormat("mm").format(dateTimeInMillis).toInt())
        return tempArrayList
    }

    override fun getItemCount(): Int {
        return tasksArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var textViewTitle: TextView
        var textViewDescription: TextView
        var textViewDate: TextView
        var btnFinish: Button
        var btnDelete: Button

        init{
            textViewTitle = itemView.findViewById(R.id.textViewTitleActiveTaskRecyclerView)
            textViewDescription = itemView.findViewById(R.id.textViewDescriptionActiveTaskRecyclerView)
            textViewDate = itemView.findViewById(R.id.textViewDateActiveTasksRecyclerView)
            btnFinish = itemView.findViewById(R.id.buttonFinishActiveTaskRecyclerView)
            btnDelete = itemView.findViewById(R.id.buttonDeleteActiveTaskRecyclerView)

            textViewTitle.setOnClickListener(this)
            textViewDescription.setOnClickListener(this)
            textViewDate.setOnClickListener(this)

            btnFinish.setOnClickListener(this)
            btnDelete.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                if(position == textViewTitle.id || position == textViewDescription.id || position == textViewDate.id){
                    listener.onEditClicked(position)
                }
                else if(position == btnDelete.id){
                    listener.onBtnDeleteClicked(position)
                }
                else if(position == btnFinish.id){
                    listener.onBtnFinishClicked(position)
                }
            }
        }
    }

    interface onItemClickListener {
        fun onBtnDeleteClicked(position: Int)
        fun onBtnFinishClicked(position:Int)
        fun onEditClicked(position:Int)
    }
}
