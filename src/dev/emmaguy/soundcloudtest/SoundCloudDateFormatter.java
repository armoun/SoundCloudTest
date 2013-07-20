package dev.emmaguy.soundcloudtest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

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