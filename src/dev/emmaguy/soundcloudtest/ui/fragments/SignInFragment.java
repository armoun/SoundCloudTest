package dev.emmaguy.soundcloudtest.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import dev.emmaguy.soundcloudtest.R;
import dev.emmaguy.soundcloudtest.async.GetSoundCloudTracksAsyncTask;
import dev.emmaguy.soundcloudtest.async.GetSoundCloudTracksAsyncTask.OnRetrievedActivitiesListener;

public class SignInFragment extends Fragment implements OnClickListener {

    private OnRetrievedActivitiesListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	final View v = inflater.inflate(R.layout.fragment_sign_in, null);

	v.findViewById(R.id.button_login).setOnClickListener(this);
	
	((EditText) v.findViewById(R.id.edittext_username)).setText(getResources().getString(R.string.username));
	((EditText) v.findViewById(R.id.edittext_password)).setText(getResources().getString(R.string.password));

	return v;
    }

    @Override
    public void onClick(View v) {
	if (v.getId() == R.id.button_login) {
	    
	    String clientId = getResources().getString(R.string.client_id);
	    String clientSecret = getResources().getString(R.string.client_secret);
	    String username = ((EditText) getActivity().findViewById(R.id.edittext_username)).getText().toString();
	    String password = ((EditText) getActivity().findViewById(R.id.edittext_password)).getText().toString();

	    new GetSoundCloudTracksAsyncTask(clientId, clientSecret, username, password, getActivity(),
		    "Authenticating and retrieving your SoundCloud dashboard...", listener).execute();
	}
    }

    @Override
    public void onAttach(Activity activity) {
	super.onAttach(activity);

	try {
	    listener = (OnRetrievedActivitiesListener) activity;
	} catch (ClassCastException e) {
	    throw new ClassCastException(activity.toString() + " must implement OnRetrievedActivitiesListener");
	}
    }
}