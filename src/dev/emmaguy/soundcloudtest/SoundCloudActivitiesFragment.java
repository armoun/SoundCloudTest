package dev.emmaguy.soundcloudtest;

import java.util.List;

import dev.emmaguy.soundcloudtest.GetSoundCloudActivitiesAsyncTask.SoundCloudActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SoundCloudActivitiesFragment extends Fragment {
    
    private List<SoundCloudActivity> activities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	final View v = inflater.inflate(R.layout.fragment_soundcloud_activitites, null);
	
	return v;
    }

    public void setArguments(List<SoundCloudActivity> activities) {
	this.activities = activities;
    }
}