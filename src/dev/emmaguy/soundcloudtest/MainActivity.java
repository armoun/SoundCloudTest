package dev.emmaguy.soundcloudtest;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import dev.emmaguy.soundcloudtest.GetSoundCloudActivitiesAsyncTask.OnRetrievedActivitiesListener;
import dev.emmaguy.soundcloudtest.GetSoundCloudActivitiesAsyncTask.SoundCloudActivity;

public class MainActivity extends FragmentActivity implements OnRetrievedActivitiesListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	SignInAndRetrieveActivitiesFragment fragment = new SignInAndRetrieveActivitiesFragment();

	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, fragment);
	transaction.addToBackStack(null);
	transaction.commit();
    }

    @Override
    public void onRetreivedActivities(List<SoundCloudActivity> activities) {
	SoundCloudActivitiesFragment fragment = new SoundCloudActivitiesFragment();
	fragment.setArguments(activities);

	FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
	transaction.replace(R.id.fragment_container, fragment);
	transaction.addToBackStack(null);
	transaction.commit();	
    }
}
