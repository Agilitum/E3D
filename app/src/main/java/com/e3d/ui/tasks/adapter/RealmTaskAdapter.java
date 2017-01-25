package com.e3d.ui.tasks.adapter;

import android.content.Context;

import com.e3d.ui.tasks.model.Task;

import io.realm.RealmResults;

/**
 * Created by ludwig on 25/01/17.
 */

public class RealmTaskAdapter extends RealmModelAdapter<Task> {

	public RealmTaskAdapter(Context context, RealmResults<Task> realmResults){
		super(context, realmResults);
	}
}
