package dev.emmaguy.soundcloudtest;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.emmaguy.soundcloudtest.GetSoundCloudActivitiesAsyncTask.SoundCloudActivity;

public class SoundCloudActivitiesFragment extends ListFragment {

    private final List<SoundCloudActivity> activities = new ArrayList<SoundCloudActivity>();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	this.setListAdapter(new SoundCloudActivityAdapter(getActivity(), R.layout.row_soundcloud_activtiy, activities));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	final View v = inflater.inflate(R.layout.fragment_soundcloud_activitites, null);
	return v;
    }

    public void setArguments(List<SoundCloudActivity> activities) {	
	this.activities.clear();
	this.activities.addAll(activities);
	
	final SoundCloudActivityAdapter adapter = (SoundCloudActivityAdapter)getListAdapter();
	if(adapter != null) {
	    adapter.notifyDataSetChanged();
	}
    }
}