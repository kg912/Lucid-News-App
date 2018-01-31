package tk.lucidna.lucid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Instant;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.Exchanger;

/**
 * Created by user on 4/16/2016.
 */
public class GCMService extends GcmTaskService {
    SharedPreferences prefs;
    JSONObject object;
    public GCMService() {
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        //Your periodic code here
        String serverText = null;
        prefs = getSharedPreferences("prefs", 0);
        if(prefs.getBoolean("autoRefresh", true) ) {
            Log.v("GCM", "RUNNING");
            ArrayList<NewsObject> list = new ArrayList<NewsObject>();
            ArrayList<NewsObject> rList = Database.read(getBaseContext());
            Long timestamp = handOverStamp(prefs.getLong("ts", getTimeStamp()), getCurrentStamp());
            Log.v("GCMSERVICE", "The service is now running");
            serverText = ApiRequest.execute(timestamp);
            try {
                JSONObject mainObject = new JSONObject(serverText);
                JSONArray jArray = mainObject.getJSONArray("result");
                for (int i = 0; i < jArray.length(); i++) {
                    object = (JSONObject) jArray.get(i);
                    list.add(new NewsObject(object));
                }
                for (NewsObject nd : rList) {
                    for (int h = 0; h < list.size(); h++) {
                        if (nd.getId().equals(list.get(h).getId())) {
                            Log.v("REMOVED", list.get(h).getTitle());
                            list.remove(h);
                        }
                    }
                }
                if (list.get(0) != null) {
                    prefs.edit().putLong("ts", getCurrentStamp()).commit();
                    Log.v("LAST TIME STAMP", getCurrentStamp() + "");
                }
                Log.v("GCM", "RETRIEVED NEW INFO");
                Collections.shuffle(list);
                Collections.shuffle(list);
                rList.addAll(0, list);
                try {
                    Database.write(getBaseContext(), rList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            catch(Exception e) {
            }
        }
        else {
            Log.v("GCM", "turned off");
        }

        return 1;
    }

    public static long getTimeStamp(){
        Date date;
        long unix;
        DateTime dateTime = new DateTime().minusHours(8);
        date = dateTime.toDate();
        unix = date.getTime()/1000;
        return unix;
    }

    public static long getCurrentStamp() {
        long unixTime = System.currentTimeMillis() / 1000L;
        return unixTime;
    }

    public long handOverStamp(long date1, long date2) {
        Hours h = Hours.hoursBetween(new Instant(date1*1000), new Instant(date2*1000));
        int j = h.getHours();
        Log.v("TIME DIFF", j + "");
        if(j > 8) {
            return getTimeStamp();
        }
        else {
            return prefs.getLong("ts", getTimeStamp());
        }
    }
}