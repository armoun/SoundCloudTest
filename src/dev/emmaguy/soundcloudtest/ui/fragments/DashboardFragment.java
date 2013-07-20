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
import dev.emmaguy.soundcloudtest.Track;
import dev.emmaguy.soundcloudtest.ui.TracksAdapter;

public class DashboardFragment extends ListFragment {

    private final List<Track> tracks = new ArrayList<Track>();
    private OnSoundCloudActivitySelectedListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	this.setListAdapter(new TracksAdapter(getActivity(), R.layout.listfragment_row_dashboard, tracks));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	return inflater.inflate(R.layout.fragment_dashboard, null);
    }

    public void setArguments(List<Track> tracks) {
	this.tracks.clear();
	this.tracks.addAll(tracks);

	final TracksAdapter adapter = (TracksAdapter) getListAdapter();
	if (adapter != null) {
	    adapter.notifyDataSetChanged();
	}
    }

    public interface OnSoundCloudActivitySelectedListener {
	public void onSelected(Track t);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
	listener.onSelected(tracks.get(position));
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