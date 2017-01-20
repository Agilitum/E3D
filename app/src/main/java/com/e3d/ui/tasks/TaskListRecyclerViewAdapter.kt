package com.e3d.ui.tasks

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.e3d.R

/**
 * Created by ludwig on 20/01/17.
 */

class TaskListRecyclerViewAdapter : RecyclerView.Adapter<TaskListRecyclerViewAdapter.ViewHolder>() {

    internal var mItemClickListener: OnItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {

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

    }

    override fun getItemCount(): Int {
        return 1
    }
}
