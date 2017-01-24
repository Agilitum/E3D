package com.e3d

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

class TaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //***** Get Intent Extras *****//
        val taskID = intent.getIntExtra("id", 0)


        //***** TODO: get data provider *****//
        //TODO: use ID to retrieve Task object



        //***** populate UI with data *****//
        var taskTitle = findViewById(R.id.text_task_activity_title)
        var taskUrgency = findViewById(R.id.text_task_activity_urgency_editable)
        var taskProjectList = findViewById(R.id.image_task_activity_project_list_editable)
        var taskDeadline = findViewById(R.id.text_task_activity_deadline_editable)
        var taskNotes = findViewById(R.id.text_task_activity_notes_editable)




    }

}
