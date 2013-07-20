package dev.emmaguy.soundcloudtest;


public class SoundCloudActivity {
    String created_at;
    String formattedDate;
    
    public Origin origin;
    
    public String getCreatedAtDate() {
        if (formattedDate == null) {
    	formattedDate = new SoundCloudDateFormatter().getFormattedDateTime(created_at);
        }

        return formattedDate;
    }
}