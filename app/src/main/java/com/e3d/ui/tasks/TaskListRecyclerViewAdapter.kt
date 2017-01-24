package com.e3d.ui.tasks

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.e3d.R
import com.e3d.ui.tasks.model.Task
import java.util.*

/**
 * Created by ludwig on 20/01/17.
 */

class TaskListRecyclerViewAdapter(var taskList: ArrayList<Task>) : RecyclerView
.Adapter<TaskListRecyclerViewAdapter.ViewHolder>
() {

    var mItemClickListener: OnItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var taskTitle: TextView
        var taskPriorityLvL: TextView
        var taskProjectList: ImageView

        init {
            taskTitle = itemView.findViewById(R.id.text_task_row_title) as TextView
            taskPriorityLvL = itemView.findViewById(R.id.text_task_row_priority_lvl) as TextView
            taskProjectList = itemView.findViewById(R.id.image_task_row_project_list) as ImageView
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mItemClickListener != null) {
                mItemClickListener!!.onItemClick(view, position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun SetOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_row_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.taskTitle.setText(taskList.get(position).taskName)
        holder.taskPriorityLvL.setText(taskList.get(position).urgency)
        holder.taskProjectList.setImageResource(setTaskProjectListIndicator(position))

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun setTaskProjectListIndicator(position: Int): Int {
        if(taskList.get(position).projectListTask != true){
            return R.drawable.ic_project_task_false
        } else {
            return R.drawable.ic_project_task_true
        }
    }
}
