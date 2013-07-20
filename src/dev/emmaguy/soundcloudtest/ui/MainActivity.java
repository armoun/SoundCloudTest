package dev.emmaguy.soundcloudtest.ui;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import dev.emmaguy.soundcloudtest.R;
import dev.emmaguy.soundcloudtest.SoundCloudActivity;
import dev.emmaguy.soundcloudtest.async.GetSoundCloudActivitiesAsyncTask.OnRetrievedActivitiesListener;
import dev.emmaguy.soundcloudtest.ui.fragments.DashboardFragment;
import dev.emmaguy.soundcloudtest.ui.fragments.DashboardFragment.OnSoundCloudActivitySelectedListener;
import dev.emmaguy.soundcloudtest.ui.fragments.SignInFragment;
import dev.emmaguy.soundcloudtest.ui.fragments.StreamFragment;

public class MainActivity extends FragmentActivity implements OnRetrievedActivitiesListener, OnSoundCloudActivitySelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	if (findViewById(R.id.fragment_container) != null) {
	    if (savedInstanceState != null) {
		return;
	    }
	}
	
	SignInFragment fragment = new SignInFragment();

	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, fragment);
	transaction.commit();
    }

    @Override
    public void onRetreivedActivities(List<SoundCloudActivity> activities) {
	DashboardFragment fragment = new DashboardFragment();
	fragment.setArguments(activities);
	
	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, fragment);
	transaction.addToBackStack(null);
	transaction.commit();	
    }

    @Override
    public void onSelected(SoundCloudActivity a) {
	StreamFragment fragment = new StreamFragment();
	fragment.setArguments(a);
	
	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, fragment);
	transaction.addToBackStack(null);
	transaction.commit();	
    }
}
