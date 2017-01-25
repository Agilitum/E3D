package com.e3d.ui.tasks.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by ludwig on 25/01/17.
 */

public class RealmModelAdapter<T extends RealmObject> extends RealmBaseAdapter<T> {

	public RealmModelAdapter(Context context, RealmResults<T> realmResults){
		super(context, realmResults);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		return null;
	}
}