package com.e3d.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.e3d.ui.tasks.model.Task;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ludwig on 24/01/17.
 */

public class RealmController {

	private static RealmController instance;
	private final Realm realm;

	public RealmController(Application application){
		realm = Realm.getDefaultInstance();
	}

	public static RealmController with(Fragment fragment){
		if(instance == null){
			instance = new RealmController(fragment.getActivity().getApplication());
		}
		return instance;
	}

	public static RealmController with(Activity activity){
		if(instance == null){
			instance = new RealmController(activity.getApplication());
		}
		return  instance;
	}

	public static RealmController with(Application application){
		if(instance == null){
			instance = new RealmController(application);
		}
		return  instance;
	}

	public static RealmController getInstance(){
		return instance;
	}

	public Realm getRealm(){
		return realm;
	}

	public RealmResults<Task> getTasks(){
		return realm.where(Task.class).findAll();
	}

	public Task getTask(Long id){
		return realm.where(Task.class).equalTo("ID", id).findFirst();
	}

}
