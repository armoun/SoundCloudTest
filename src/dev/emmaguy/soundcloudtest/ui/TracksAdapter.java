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
import dev.emmaguy.soundcloudtest.Track;
import dev.emmaguy.soundcloudtest.User;
import dev.emmaguy.soundcloudtest.async.DownloadImageAsyncTask;

public class TracksAdapter extends ArrayAdapter<Track> {

    private Context context;
    private List<Track> tracks;
    private final int evenRowColor = Color.parseColor("#BBFAFEFA");
    private final int oddRowColour = Color.parseColor("#BBCACACC");

    private static final Map<Long, Bitmap> trackImageCache = new HashMap<Long, Bitmap>();
    private static final Map<Long, Bitmap> userTrackImageCache = new HashMap<Long, Bitmap>();

    public TracksAdapter(Context c, int resourceId, List<Track> tracks) {
	super(c, resourceId, tracks);

	this.context = c;
	this.tracks = tracks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	View v = convertView;
	ViewHolder holder;

	if (v == null) {
	    LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    v = vi.inflate(R.layout.listfragment_row_dashboard, null);

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
	final Track track = tracks.get(position);
	final User user = track.user;

	holder.Title.setText(track.title + " " + track.kind + " " + track.sharing);
	holder.CreatedAt.setText(track.getCreatedAtDate());
	holder.Username.setText(user.username);

	final Bitmap artwork = trackImageCache.get(track.id);
	if (artwork == null) {
	    new DownloadImageAsyncTask(track.id, trackImageCache, track.artwork_url).execute();
	} else {
	    holder.Avatar.setImageBitmap(artwork);
	}

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
