package com.e3d

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.e3d.realm.RealmController
import com.e3d.ui.tasks.adapter.RealmTaskAdapter
import com.e3d.ui.tasks.adapter.TaskListRecyclerViewAdapter
import com.e3d.ui.tasks.model.Task
import io.realm.Realm
import io.realm.RealmResults
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var realm: Realm by Delegates.notNull()
    private var taskListRecyclerViewAdapter: TaskListRecyclerViewAdapter by Delegates.notNull()
    private var taskListRecyclerView: RecyclerView by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //***** Floating Action Button *****//
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        fab.setOnClickListener {
            var inflater = this.layoutInflater
            var content = inflater.inflate(R.layout.content_main_alert_dialog, null)

            var title = content.findViewById(R.id.title_activity_main_alert_dialog) as? EditText
            var urgency = content.findViewById(R.id.urgency_activity_main_alert_dialog_spinner) as? Spinner
            var projectListTask = content.findViewById(R.id.check_activity_main_alert_dialog) as? CheckBox
            var deadline = content.findViewById(R.id.date_activity_main_alert_dialog) as? EditText
            var notes = content.findViewById(R.id.notes_activity_main_alert_dialog) as? EditText

            val builder = AlertDialog.Builder(this)
            builder.setView(content)
                    .setTitle("Add Task")
                    .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which ->
                        val task = Task()
                        task.ID = (RealmController.getInstance().getTasks().size + System
                                .currentTimeMillis())

                        task.taskName = title?.text.toString()
                        task.urgency = urgency?.selectedItem.toString()
                        task.projectListTask = projectListTask!!.isChecked
                        task.deadline = parseDate(deadline?.text.toString())
                        task.notes = notes?.text.toString()


                        if (title!!.text.isNullOrEmpty() || title.text.equals("") || title.text.equals(" ")) {
                            Toast.makeText(this, "Entry not saved, missing title", Toast
                                    .LENGTH_SHORT).show()
                        } else {
                            // Persist data easily
                            realm.beginTransaction()
                            realm.copyToRealm(task)
                            realm.commitTransaction()

                            taskListRecyclerViewAdapter.notifyDataSetChanged()

                        }
                    })
                    .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            val dialog = builder.create()
            dialog.show()

            // OnClickListener to display DatePickerDialog
            deadline?.setOnClickListener(View.OnClickListener {
                val c = Calendar.getInstance()

                val dpd = DatePickerDialog(content.getContext(),
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                            deadline.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) +
                                    "/" + year)
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE))
                dpd.show()
            })
        }


        //***** Navigation Drawer *****//
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        //***** NavigationView *****//
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        //***** Task List RecyclerView Init *****//
        taskListRecyclerView = findViewById(R.id.recycler_view_task_list) as RecyclerView
        taskListRecyclerView.setLayoutManager(LinearLayoutManager(this))
        taskListRecyclerViewAdapter = TaskListRecyclerViewAdapter(this)
        taskListRecyclerView.setAdapter(taskListRecyclerViewAdapter)

        //***** Get Realm Instance *****//
        realm = RealmController.with(this).getRealm();

//        if (!Prefs.with(this).getPreLoad()) {
//            setRealmData();
//        }

        RealmController.getInstance()
        setRealmAdapter(RealmController.with(this).getTasks())

        //***** TaskListRecyclerViewAdapater onItemClickListener *****//
        taskListRecyclerViewAdapter.SetOnItemClickListener(object : TaskListRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                var taskDetailView = Intent(applicationContext, TaskActivity::class.java)
//                var taskDetailViewExtra = taskDetailView.putExtra("id", .get(position).ID)
                startActivity(taskDetailView)
            }
        })
    }

    fun setRealmAdapter(books: RealmResults<Task>) {

        val realmAdapter = RealmTaskAdapter(this.applicationContext, books)
        taskListRecyclerViewAdapter.setRealmAdapter(realmAdapter)
        taskListRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_tasks) {

        } else if (id == R.id.nav_goals) {

        } else if (id == R.id.nav_mission_statement) {

        } else if (id == R.id.nav_once_as) {

        } else if (id == R.id.nav_import) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun parseDate(dateString: String) : Date{
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val date = format.parse(dateString)

        return date
    }
}
