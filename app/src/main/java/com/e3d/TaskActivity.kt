package com.e3d

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.e3d.realm.RealmController

class TaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //***** Get Intent Extras *****//
        val taskID = intent.getLongExtra("id", 0)


        //***** Get Realm *****//
        var realm = RealmController.getInstance()
        var task = realm.getTask(taskID)


        //***** populate UI with data *****//
        var taskTitle = findViewById(R.id.text_task_activity_title) as TextView
        var taskUrgency = findViewById(R.id.text_task_activity_urgency_editable) as TextView
        var taskProjectList = findViewById(R.id.image_task_activity_project_list_editable) as ImageView
        var taskDeadline = findViewById(R.id.text_task_activity_deadline_editable) as TextView
        var taskNotes = findViewById(R.id.text_task_activity_notes_editable) as TextView

        taskTitle.setText(task.taskName)
        taskUrgency.setText(task.urgency)
        taskProjectList.setImageResource(setTaskProjectListIndicator(task.projectListTask))
        taskDeadline.setText(task.deadline.toString())
        taskNotes.setText(task.notes)

    }

    fun setTaskProjectListIndicator(projectListTask: Boolean): Int {
        if(!projectListTask) {
            return R.drawable.ic_project_task_false
        } else {
            return R.drawable.ic_project_task_true
        }
    }

}
