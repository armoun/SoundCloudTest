package dev.emmaguy.soundcloudtest;

public class Track {
    public long id;
    public long playback_count;
    public long download_count;
    public long favoritings_count;
    public long comment_count;
    
    public String title;
    public String artwork_url;
    public String stream_url;
    public String kind;
    public String sharing;
    public String created_at;
    
    public User user;
    
    private String formattedDate = null;
    private static SoundCloudDateFormatter formatter = new SoundCloudDateFormatter();

    public String getCreatedAtDate() {
	if(formattedDate == null) {
	    formattedDate = formatter.getFormattedDateTime(created_at);
	}
	
	return formattedDate;
    }
}