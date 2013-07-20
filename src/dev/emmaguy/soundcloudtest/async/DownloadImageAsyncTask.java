package dev.emmaguy.soundcloudtest.async;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadImageAsyncTask extends AsyncTask<Void, Void, byte[]> {
    
    private final long id;
    private final Map<Long, Bitmap> bitmapCache;
    private String imageUrl;

    public DownloadImageAsyncTask(long id, Map<Long, Bitmap> cache, String imageUrl) {
	this.id = id;
	this.bitmapCache = cache;
	this.imageUrl = imageUrl;
    }

    protected byte[] doInBackground(Void... params) {
	try {
	    InputStream in = new URL(imageUrl).openStream();
	    BufferedInputStream bis = new BufferedInputStream(in);

	    ByteArrayBuffer baf = new ByteArrayBuffer(500);
	    int current = 0;
	    while ((current = bis.read()) != -1) {
		baf.append((byte) current);
	    }

	    return baf.toByteArray();
	} catch (Exception e) {
	    Log.e("SoundCloudTestApp", e.getClass().toString(), e);
	}
	return null;
    }

    protected void onPostExecute(byte[] bytes) {
	if(bytes != null && bytes.length > 0) {
	    bitmapCache.put(id, BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
	}
    }
}