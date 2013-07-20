package dev.emmaguy.soundcloudtest;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

    class SoundCloudActivity {
	String createdAt;
	String userUsername;
	String userAvatar;
	String trackTitle;
	String trackStreamUrl;
    }

    @Override
    protected void onPostExecute(Boolean result) {
	super.onPostExecute(result);

	if (!result) {
	    Toast.makeText(context, "Failed to authenticate", Toast.LENGTH_SHORT).show();
	    return;
	}

	if (listener != null) {
	    List<SoundCloudActivity> activities = new Gson().fromJson(jsonResponse,
		    new TypeToken<List<SoundCloudActivity>>() {
		    }.getType());
	    Log.i("xx", "count: " + activities.size());

	    listener.onRetreivedActivities(activities);
	}
    }

    public interface OnRetrievedActivitiesListener {
	void onRetreivedActivities(List<SoundCloudActivity> activities);
    }
}