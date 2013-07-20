package dev.emmaguy.soundcloudtest.ui;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import dev.emmaguy.soundcloudtest.R;
import dev.emmaguy.soundcloudtest.Track;
import dev.emmaguy.soundcloudtest.async.GetSoundCloudTracksAsyncTask.OnRetrievedActivitiesListener;
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
    public void onRetreivedActivities(List<Track> tracks) {
	DashboardFragment fragment = new DashboardFragment();
	fragment.setArguments(tracks);
	
	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, fragment);
	transaction.addToBackStack(null);
	transaction.commit();	
    }

    @Override
    public void onSelected(Track t) {
	StreamFragment fragment = new StreamFragment();
	fragment.setArguments(t, getResources().getString(R.string.client_id));
	
	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, fragment);
	transaction.addToBackStack(null);
	transaction.commit();	
    }
}
