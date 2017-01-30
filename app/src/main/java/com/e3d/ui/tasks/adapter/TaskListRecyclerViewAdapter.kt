package com.e3d.ui.tasks.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.e3d.R
import com.e3d.realm.RealmController
import com.e3d.ui.tasks.helper.ItemTouchHelperAdapter
import com.e3d.ui.tasks.model.Task
import io.realm.Realm


/**
 * Created by ludwig on 20/01/17.
 */

class TaskListRecyclerViewAdapter : RealmRecyclerViewAdapter<Task>, ItemTouchHelperAdapter {

    internal val context: Context

    var mItemClickListener: OnItemClickListener? = null

    private var realm: Realm? = null
    private var inflater: LayoutInflater? = null


    constructor(context: Context) : super() {
        this.context = context
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View
    .OnClickListener {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        // inflate a new card view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_row_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        realm = RealmController.getInstance().realm

        // get the article
        val task = getItem(position)
        // cast the generic view holder to our specific one
        val holder = viewHolder as CardViewHolder

        // set the title and the snippet
        holder.taskTitle.setText(task.taskName)
        holder.taskPriorityLvL.setText(task.urgency)
        holder.taskProjectList.setImageResource(setTaskProjectListIndicator(task.projectListTask!!))

    }

    override fun getItemCount(): Int {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount()
        }
        return 0
    }


    fun setTaskProjectListIndicator(projectListTask: Boolean): Int {
            if(!projectListTask) {
                return R.drawable.ic_project_task_false
            } else {
                return R.drawable.ic_project_task_true
            }
        }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun SetOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }

    override fun onItemDismiss(adapterPosition: Int) {
        var taskID = getItem(adapterPosition).ID

        var realm = RealmController.getInstance()
        realm.removeTask(taskID)

        notifyItemRemoved(adapterPosition)

        Toast.makeText(context, "Task removed", Toast.LENGTH_LONG).show()
    }

    override fun onItemMove(fromAdapterPosition: Int, toAdapterPosition: Int): Boolean {
        //TODO: needs correct update of underlying data objects

        notifyItemMoved(fromAdapterPosition, toAdapterPosition)
        return true
    }
}

