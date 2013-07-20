package dev.emmaguy.soundcloudtest.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dev.emmaguy.soundcloudtest.R;
import dev.emmaguy.soundcloudtest.SoundCloudActivity;
import dev.emmaguy.soundcloudtest.Track;
import dev.emmaguy.soundcloudtest.User;
import dev.emmaguy.soundcloudtest.async.DownloadImageAsyncTask;

public class SoundCloudActivityAdapter extends ArrayAdapter<SoundCloudActivity> {

    private Context context;
    private List<SoundCloudActivity> activities;
    private final int evenRowColor = Color.parseColor("#BBFAFEFA");
    private final int oddRowColour = Color.parseColor("#BBCACACC");

    private static final Map<Long, Bitmap> trackImageCache = new HashMap<Long, Bitmap>();
    private static final Map<Long, Bitmap> userTrackImageCache = new HashMap<Long, Bitmap>();

    public SoundCloudActivityAdapter(Context c, int resourceId, List<SoundCloudActivity> activities) {
	super(c, resourceId, activities);

	this.context = c;
	this.activities = activities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	View v = convertView;
	ViewHolder holder;

	if (v == null) {
	    LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    v = vi.inflate(R.layout.listfragment_row_soundcloud_activtiy, null);

	    holder = new ViewHolder();
	    holder.Username = (TextView) v.findViewById(R.id.textview_username);
	    holder.CreatedAt = (TextView) v.findViewById(R.id.textview_created_at);
	    holder.Title = (TextView) v.findViewById(R.id.textview_title);
	    holder.Avatar = (ImageView) v.findViewById(R.id.imageview_avatar);

	    v.setTag(holder);
	} else {
	    holder = (ViewHolder) v.getTag();
	}

	updateRowContents(position, holder);
	updateRowBackground(position, v);
	
	return v;
    }

    private void updateRowBackground(int position, View v) {
	if (position % 2 == 0) {
	    v.setBackgroundColor(evenRowColor);
	} else {

	    v.setBackgroundColor(oddRowColour);
	}
    }

    private void updateRowContents(int position, ViewHolder holder) {
	final SoundCloudActivity soundCloudActivity = activities.get(position);
	final User user = soundCloudActivity.origin.user;

	holder.CreatedAt.setText(soundCloudActivity.getCreatedAtDate());
	if (soundCloudActivity.origin.hasTrack()) {

	    final Track track = soundCloudActivity.origin.track;
	    final Bitmap artwork = trackImageCache.get(track.id);
	    if (artwork == null) {
		new DownloadImageAsyncTask(track.id, trackImageCache, track.artwork_url).execute();
	    } else {
		holder.Avatar.setImageBitmap(artwork);
	    }
	    holder.Title.setText(track.title);
	}

	holder.Username.setText(user.username);

	final Bitmap avatar = userTrackImageCache.get(user.id);
	if (avatar == null) {
	    new DownloadImageAsyncTask(user.id, userTrackImageCache, user.avatar_url).execute();
	} else {
	    holder.Avatar.setImageBitmap(avatar);
	}
    }

    class ViewHolder {
	public ImageView Avatar;
	public TextView Title;
	public TextView CreatedAt;
	public TextView Username;

    }
}
