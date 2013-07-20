package dev.emmaguy.soundcloudtest.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import dev.emmaguy.soundcloudtest.R;
import dev.emmaguy.soundcloudtest.SoundCloudActivity;

public class StreamFragment extends Fragment implements OnClickListener {
    
    private SoundCloudActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	final View v = inflater.inflate(R.layout.fragment_stream, null);

	v.findViewById(R.id.button_stream).setOnClickListener(this);

	return v;
    }

    @Override
    public void onClick(View v) {
	if (v.getId() == R.id.button_stream) {

	}
    }

    public void setArguments(SoundCloudActivity activity) {
	this.activity = activity;
    }
}