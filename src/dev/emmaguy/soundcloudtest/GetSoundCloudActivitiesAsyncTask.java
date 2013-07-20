package dev.emmaguy.soundcloudtest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Request;

public class GetSoundCloudActivitiesAsyncTask extends ProgressAsyncTask<Void, Void, Boolean> {

    private final String clientId;
    private final String clientSecret;
    private final String username;
    private final String password;
    private final OnRetrievedActivitiesListener listener;
    private final Context context;
    
    private String jsonResponse;

    public GetSoundCloudActivitiesAsyncTask(String clientId, String clientSecret, String username, String password,
	    Context c, String dialogMessage, OnRetrievedActivitiesListener listener) {
	super(c, dialogMessage);

	this.clientId = clientId;
	this.clientSecret = clientSecret;
	this.username = username;
	this.password = password;
	this.context = c;
	this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... arg0) {
	ApiWrapper wrapper = new ApiWrapper(clientId, clientSecret, null, null);
	try {
	    wrapper.login(username, password);

	    HttpResponse resp = wrapper.get(Request.to("/me/activities"));
	    jsonResponse = new JsonParser().parse(EntityUtils.toString(resp.getEntity())).getAsJsonObject()
		    .get("collection").getAsJsonArray().toString();

	    return resp.getStatusLine().getStatusCode() == 200;
	} catch (IOException e) {
	    Log.e("SoundCloudTest", "Failed to login", e);
	}

	return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
	super.onPostExecute(result);

	if (!result) {
	    Toast.makeText(context, "Failed to authenticate", Toast.LENGTH_SHORT).show();
	    return;
	}

	if (listener != null) {
	    final Type type = new TypeToken<List<SoundCloudActivity>>() {
	    }.getType();

	    List<SoundCloudActivity> activities = new Gson().fromJson(jsonResponse, type);
	    listener.onRetreivedActivities(activities);
	}
    }

    public interface OnRetrievedActivitiesListener {
	void onRetreivedActivities(List<SoundCloudActivity> activities);
    }

    class SoundCloudActivity {
	String created_at;
	Origin origin;

	String formattedDate;

	String getCreatedAtDate() {
	    if (formattedDate == null) {
		formattedDate = new SoundCloudDateFormatter().getFormattedDateTime(created_at);
	    }

	    return formattedDate;
	}
    }

    class Origin {
	User user;
	Track track;

	boolean hasTrack() {
	    return track != null;
	}
    }

    class User {
	long id;
	String username;
	String avatar_url;
    }

    class Track {
	long id;
	String title;
	String artwork_url;
	String stream_url;
    }

    class SoundCloudDateFormatter {
	private final SimpleDateFormat soundCloudFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z", Locale.UK);

	public String getFormattedDateTime(String datetime) {
	    Date givenDateTime;
	    SimpleDateFormat desiredFormat = new SimpleDateFormat("HH:mm:ss", Locale.UK);

	    try {
		givenDateTime = soundCloudFormat.parse(datetime);
	    } catch (ParseException e) {
		Log.e("SoundCloudTestApp", e.getClass().toString(), e);
		return datetime;
	    }

	    Calendar cal = Calendar.getInstance();
	    cal.setTime(givenDateTime);

	    if (isSameDay(cal, getToday()))
		return desiredFormat.format(givenDateTime);

	    if (isSameDay(cal, getYesterday()))
		return "Yesterday " + desiredFormat.format(givenDateTime);

	    return formatDateWithTwoLetterContractions(givenDateTime);
	}

	private String formatDateWithTwoLetterContractions(Date dateTimeOfTweet) {
	    SimpleDateFormat desiredFormat = new SimpleDateFormat("d HH:mm", Locale.UK);
	    String date = desiredFormat.format(dateTimeOfTweet);

	    if (date.endsWith("1") && !date.endsWith("11")) {
		desiredFormat = new SimpleDateFormat("EE MMM d'st' HH:mm", Locale.UK);
	    } else if (date.endsWith("2") && !date.endsWith("12")) {
		desiredFormat = new SimpleDateFormat("EE MMM d'nd' HH:mm", Locale.UK);
	    } else if (date.endsWith("3") && !date.endsWith("13")) {
		desiredFormat = new SimpleDateFormat("EE MMM d'rd' HH:mm", Locale.UK);
	    } else {
		desiredFormat = new SimpleDateFormat("EE MMM d'th' HH:mm", Locale.UK);
	    }

	    return desiredFormat.format(dateTimeOfTweet);
	}

	private Calendar getYesterday() {
	    Calendar yesterday = getToday();
	    yesterday.add(Calendar.DAY_OF_YEAR, -1);
	    return yesterday;
	}

	private Calendar getToday() {
	    Calendar today = Calendar.getInstance();
	    DateFormat.getDateTimeInstance().setCalendar(today);
	    return today;
	}

	private boolean isSameDay(Calendar cal1, Calendar cal2) {
	    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
		    && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
	}
    }
}