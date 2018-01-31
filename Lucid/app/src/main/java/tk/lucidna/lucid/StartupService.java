package tk.lucidna.lucid;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class StartupService extends IntentService {
    Log log = null;
    String serverText = null;
    ArrayList<NewsObject> list;
    String x;
    String t;
    String text;
    JSONObject object;
    NewsObject newsObject;
    Document doc;
    Handler mHandler;
    long timestamp;
    Elements paragraphs;
    public StartupService() {
        super("StartupService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        list = new ArrayList<NewsObject>();
        ResultReceiver rec = intent.getParcelableExtra("rr");
        timestamp = intent.getLongExtra("timestamp", getTimeStamp());
        log.v("StartupService", "The service is now running");
        this.serverText = ApiRequest.execute(timestamp);
        try {
            JSONObject mainObject = new JSONObject(this.serverText);
            JSONArray jArray = mainObject.getJSONArray("result");
            for(int i = 0; i < jArray.length(); i++) {
                object =(JSONObject) jArray.get(i);
                list.add(new NewsObject(object));
            }

            Bundle b = new Bundle();
            b.putSerializable("KEY", list);
            Log.v("SERVICE -> ACTIVITY", "Sending...");
            rec.send(0, b);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void onStopCommand() {

    }

    public String getServerText() {
        return this.serverText;
    }

    private void fixContent(final NewsObject noj) {
        doc = Jsoup.parse(noj.getContent());
        Elements p = doc.select("p");
        for(Element px: p) {
            text += px.text() + "\n\n";
        }
        Log.v("LENGTH IS:", text.length() + "");
        if(text.length() < 770) {
            mHandler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run () {
                    String text2;
                    try {
                        doc = Jsoup.connect(noj.getUrl()).get();
                        paragraphs = doc.select("p");
                        text2 = "";
                        for (Element pz : paragraphs) {
                            text2 += pz.text().trim();
                            text2 += "\n\n";
                        }
                        final String x = text2;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mHandler.post(new Runnable() {
                        @Override
                        public void run () {
                            noj.setContent(x);
                        }
                    });
                }
            }).start();
        }
        else {
            noj.setContent(text);
        }
    }

    public static long getTimeStamp(){
        Date date;
        long unix;
        DateTime dateTime = new DateTime().minusHours(8);
        date = dateTime.toDate();
        unix = date.getTime()/1000;
        return unix;
    }

}
