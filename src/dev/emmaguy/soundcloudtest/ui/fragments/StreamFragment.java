package dev.emmaguy.soundcloudtest.ui.fragments;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import dev.emmaguy.soundcloudtest.R;
import dev.emmaguy.soundcloudtest.Track;

public class StreamFragment extends Fragment implements OnClickListener, OnPreparedListener, OnErrorListener,
	OnCompletionListener {

    private Track track;
    private String clientId;
    private MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	final View v = inflater.inflate(R.layout.fragment_stream, null);

	v.findViewById(R.id.button_stream).setOnClickListener(this);

	return v;
    }

    @Override
    public void onClick(View v) {
	if (v.getId() == R.id.button_stream) {

	    if (track == null) {
		Toast.makeText(getActivity(), "No track to stream :-(", Toast.LENGTH_SHORT).show();
		return;
	    }

	    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
		mediaPlayer.stop();
		mediaPlayer.release();
		((Button) getActivity().findViewById(R.id.button_stream)).setText(getResources().getString(R.string.stream));
		return;

	    } else {
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	    }

	    String url = track.stream_url + "?client_id=" + clientId;
	    Toast.makeText(getActivity(), "Stream url: " + url, Toast.LENGTH_LONG).show();

	    mediaPlayer.setOnPreparedListener(this);
	    mediaPlayer.setOnErrorListener(this);
	    mediaPlayer.setOnCompletionListener(this);

	    try {
		mediaPlayer.setDataSource(url);
		mediaPlayer.prepareAsync();

	    } catch (Exception e) {
		Log.e("xx", "Failed to set data source ", e);
	    }
	}
    }

    public void setArguments(Track activity, String clientId) {
	this.track = activity;
	this.clientId = clientId;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
	Toast.makeText(getActivity(), "End of playback", Toast.LENGTH_SHORT).show();
	((Button) getActivity().findViewById(R.id.button_stream)).setText(getResources().getString(R.string.again));
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
	Toast.makeText(getActivity(), "Error :(", Toast.LENGTH_SHORT).show();
	Log.d("xx", "what: " + what + " extra: " + extra);
	return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
	Toast.makeText(getActivity(), "Prepared and ready to play - beginning playback", Toast.LENGTH_SHORT).show();
	mp.start();

	((Button) getActivity().findViewById(R.id.button_stream)).setText(getResources().getString(R.string.pause));
    }
}