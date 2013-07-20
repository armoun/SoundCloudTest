package dev.emmaguy.soundcloudtest.ui.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import dev.emmaguy.soundcloudtest.R;
import dev.emmaguy.soundcloudtest.SoundCloudActivity;
import dev.emmaguy.soundcloudtest.ui.SoundCloudActivityAdapter;

public class DashboardFragment extends ListFragment {

    private final List<SoundCloudActivity> activities = new ArrayList<SoundCloudActivity>();
    private OnSoundCloudActivitySelectedListener listener;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	this.setListAdapter(new SoundCloudActivityAdapter(getActivity(), R.layout.listfragment_row_soundcloud_activtiy, activities));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	return inflater.inflate(R.layout.fragment_soundcloud_activitites, null);
    }

    public void setArguments(List<SoundCloudActivity> activities) {	
	this.activities.clear();
	this.activities.addAll(activities);
	
	final SoundCloudActivityAdapter adapter = (SoundCloudActivityAdapter)getListAdapter();
	if(adapter != null) {
	    adapter.notifyDataSetChanged();
	}
    }

    public interface OnSoundCloudActivitySelectedListener {
	public void onSelected(SoundCloudActivity a);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
	listener.onSelected(activities.get(position));
    }

    @Override
    public void onAttach(Activity activity) {
	super.onAttach(activity);

	try {
	    listener = (OnSoundCloudActivitySelectedListener) activity;
	} catch (ClassCastException e) {
	    throw new ClassCastException(activity.toString() + " must implement OnSoundCloudActivitySelectedListener");
	}
    }
    
    
}