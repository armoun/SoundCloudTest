package dev.emmaguy.soundcloudtest;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dev.emmaguy.soundcloudtest.GetSoundCloudActivitiesAsyncTask.SoundCloudActivity;

public class SoundCloudActivityAdapter extends ArrayAdapter<SoundCloudActivity> {

    private Context context;
    private List<SoundCloudActivity> activities;
    private final int evenRowColor = Color.parseColor("#BBFAFEFA");
    private final int oddRowColour = Color.parseColor("#BBCACACC");
    private final SparseArray<byte[]> trackImageCache = new SparseArray<byte[]>();
    private final SparseArray<byte[]> userTrackImageCache = new SparseArray<byte[]>();
    
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
            v = vi.inflate(R.layout.row_soundcloud_activtiy, null);

            holder = new ViewHolder();
            holder.Username = (TextView) v.findViewById(R.id.textview_username);
            holder.CreatedAt = (TextView) v.findViewById(R.id.textview_created_at);
            holder.Title = (TextView) v.findViewById(R.id.textview_title);
            holder.Avatar = (ImageView) v.findViewById(R.id.imageview_avatar);

            v.setTag(holder);
        }
        else {
            holder = (ViewHolder)v.getTag();
        }
        
        final SoundCloudActivity soundCloudActivity = activities.get(position);
        
	holder.CreatedAt.setText(soundCloudActivity.created_at);
	if(soundCloudActivity.origin.track != null) {
	    holder.Title.setText(soundCloudActivity.origin.track.title);
	}
	holder.Username.setText(soundCloudActivity.origin.user.username);

	if(position % 2 == 0) {
	    v.setBackgroundColor(evenRowColor);
	} else {
	    
	    v.setBackgroundColor(oddRowColour);
	}
	return v;
    }
    
    class ViewHolder 
    {
	public ImageView Avatar;
	public TextView Title;
	public TextView CreatedAt;
	public TextView Username;
	
    }
}
