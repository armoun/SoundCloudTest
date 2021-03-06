package dev.emmaguy.soundcloudtest.ui;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dev.emmaguy.soundcloudtest.R;
import dev.emmaguy.soundcloudtest.Track;
import dev.emmaguy.soundcloudtest.User;

public class TracksAdapter extends ArrayAdapter<Track> {

    private Context context;
    private List<Track> tracks;
    private final int evenRowColor = Color.parseColor("#f2fbfc");
    private final int oddRowColour = Color.parseColor("#e3efef");

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
	    holder.Plays = (TextView) v.findViewById(R.id.textview_plays);
	    holder.Favourites = (TextView) v.findViewById(R.id.textview_favourites);
	    holder.Comments = (TextView) v.findViewById(R.id.textview_comments);
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

	holder.Title.setText(track.title);
	holder.CreatedAt.setText(track.getCreatedAtDate());
	holder.Username.setText(user.username);

	holder.Plays.setText(Integer.valueOf(track.playback_count).toString());
	holder.Favourites.setText(Integer.valueOf(track.favoritings_count).toString());
	holder.Comments.setText(Integer.valueOf(track.comment_count).toString());

	String url = track.artwork_url;
	if(url == null || url.length() <= 0) {
	    url = user.avatar_url;
	}
	
	Picasso.with(context)
        .load(url)
        .placeholder(R.drawable.contact_picture_placeholder)
        .into(holder.Avatar);
    }

    class ViewHolder {
	public ImageView Avatar;
	public TextView Title;
	public TextView CreatedAt;
	public TextView Username;
	public TextView Plays;
	public TextView Favourites;
	public TextView Comments;
    }
}
