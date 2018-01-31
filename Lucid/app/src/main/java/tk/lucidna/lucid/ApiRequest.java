package tk.lucidna.lucid;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by karan on 3/14/16.
 */
public class ApiRequest {
    private final String LOG_TAG = ApiRequest.class.getSimpleName();
    private static final int REQUEST_TIMEOUT = 5000;
    public static String execute(long timestamp){
        Log logx = null;
        String res = "NOT CONNECTED TO THE INTERNET";
        HttpURLConnection conn;
        try {
            String url = "http://www.lucidnews.science/miniflux/jsonrpc.php";
            JSONObject json = new JSONObject();
            JSONObject json2 = new JSONObject();
            json2.put("timestamp", timestamp);
            json.put("jsonrpc", "2.0");
            json.put("method", "item.get_all_since");
            json.put("params", json2);
            json.put("id", 1);
            URL urlObject = new URL(url);
            conn = (HttpURLConnection) urlObject.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Basic YWRtaW46N2RkNjk4NWU4MjUyM2M2ZDI5YTFmNjZhMTMwYTI5MmU1NzEzMTdjYTZiYTY3ODIxYmEzZTU1Mzk1YzIy");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", "Lucid/0.1");
            conn.setDoOutput(true);
            conn.getDoInput();
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(json.toString().getBytes("UTF-8"));
            os.close();
            int status = conn.getResponseCode();
            String stat = status + "";
            logx.v("RESPONSE CODE", stat);
            InputStream is = null;
            if (status >= 400) {
                is = conn.getErrorStream();
                res = getStringFromInputStream(is);
                logx.v("ERROR STREAM ----> ", res);
            } else {
                is = conn.getInputStream();
                res = getStringFromInputStream(is);
                logx.v("INPUT STREAM ----> ", res);

            }
        } catch (Exception e) {
            logx.v("JSON TEST", "FAILED!");
            e.printStackTrace();
        } finally {
            return res;
        }
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public static void update(){
        Log logx = null;
        String ix = "";
        String res = "NOT CONNECTED TO THE INTERNET";
        HttpURLConnection conn;
        for (int i = 0; i < 30; i++) {
            try {
                    JSONObject json = new JSONObject();
                    JSONObject json2 = new JSONObject();
                    json2.put("feed_id", i);

                    json.put("jsonrpc", "2.0");

                    json.put("method", "feed.update");
                    json.put("params", json2);
                    json.put("id", 1);
                    String url = "http://52.63.158.20/miniflux/jsonrpc.php";
                    URL urlObject = new URL(url);
                    conn = (HttpURLConnection) urlObject.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization", "Basic YWRtaW46YzdiMzc2NWViZTcwNTIxMzU4ODdjMDVhZTA1NWNiMjQxM2QzNTViNTQzMTA0MDZmZDdkNWU0NmZmMjRk");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("User-Agent", "Lucid/0.1");
                    conn.setDoOutput(true);
                    conn.getDoInput();
                    conn.connect();
                    OutputStream os = conn.getOutputStream();
                    os.write(json.toString().getBytes("UTF-8"));
                    os.close();
                    int status = conn.getResponseCode();
                    String stat = status + "";
                    logx.v("RESPONSE CODE", stat);
                    InputStream is = null;
                    if (status >= 400) {
                        is = conn.getErrorStream();
                        res = getStringFromInputStream(is);
                        logx.v("ERROR STREAM ("+ i +") ----> ", res);
                    } else {
                        is = conn.getInputStream();
                        res = getStringFromInputStream(is);
                        final String ie = "Feed Number " + i + " " + res;
                        logx.v("INPUT STREAM ("+ i +") ----> ", res);
                    }
                }catch(Exception e){
                    logx.v("JSON TEST", "FAILED!");
                    e.printStackTrace();
                }finally {

                }
        }
    }

}
