package com.e3d.ui.tasks.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by ludwig on 21/01/17.
 */

//class Task (@PrimaryKey var ID: Int, var taskName: String, var urgency: String, var
//projectListTask: Boolean?, var notes: String, var deadline: Date)

open class Task : RealmObject() {

    @PrimaryKey
    internal var ID: Long = 0

    internal var taskName: String? = null
    internal var urgency: String? = null
    internal var projectListTask: Boolean? = null
    internal var notes: String? = null
    internal var deadline: Date? = null


}
