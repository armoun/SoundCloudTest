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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
	TextView username = (TextView) v.findViewById(R.id.textview_username_stream);
	username.setText(track.user.username);

	TextView createdAt = (TextView) v.findViewById(R.id.textview_created_at_stream);
	createdAt.setText(track.getCreatedAtDate());

	TextView title = (TextView) v.findViewById(R.id.textview_title_stream);
	title.setText(track.title);

	ImageView avatar = (ImageView) v.findViewById(R.id.imageview_avatar_stream);
	Picasso.with(getActivity())
	.load(track.artwork_url)
	.placeholder(R.drawable.contact_picture_placeholder)
	.into(avatar);

	ImageView waveform = (ImageView) v.findViewById(R.id.imageview_waveform_stream);
	Picasso.with(getActivity())
	.load(track.waveform_url)
	.into(waveform);

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
		mediaPlayer = null;

		((ImageButton) getActivity().findViewById(R.id.button_stream))
			.setImageResource(R.drawable.soundcloud_play);
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
	((ImageButton) getActivity().findViewById(R.id.button_stream)).setImageResource(R.drawable.soundcloud_play);
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

	((ImageButton) getActivity().findViewById(R.id.button_stream)).setImageResource(R.drawable.soundcloud_pause);
    }
}