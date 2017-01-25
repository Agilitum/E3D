package com.e3d.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ludwig on 24/01/17.
 */

public class E3D extends Application {

	@Override
	public void onCreate(){
		super.onCreate();

		Realm.init(getApplicationContext());

		RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
			.name(Realm.DEFAULT_REALM_NAME)
			.schemaVersion(0)
			.deleteRealmIfMigrationNeeded()
			.build();
		Realm.setDefaultConfiguration(realmConfiguration);
	}
}
