package tk.lucidna.lucid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by karan on 3/16/16.
 */

public class NewsObject implements Serializable {
    private String id;
    private String title;
    private String url;
    private String feed_id;
    private String content;
    private String site_url;
    private String feed_title;
    private String updated;
    private String pText;
    private boolean processed = false;
    private byte[] bitmap;

    public NewsObject(JSONObject json) throws JSONException {
        this.id = json.getString("id");
        this.title = json.getString("title");
        this.content = json.getString("content");
        this.feed_id = json.getString("feed_id");
        this.url = json.getString("url");
        this.site_url = json.getString("site_url");
        this.feed_title = json.getString("feed_title");
        this.updated = json.getString("updated");
        this.pText = " ";
    }



    public String getContent() {
        return this.content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getId() {
        return this.id;
    }

    public String getUrl() {
        return this.url;
    }

    public String getFeed_id() {
        return this.feed_id;
    }

    public String getSite_url() {
        return this.site_url;
    }

    public String getFeed_title() {
        return this.feed_title;
    }


    public String getUpdated() {
        return this.updated;
    }

    public void setContent(String cont) {
        this.content = cont;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String getpText() {
        return pText;
    }

    public void setpText(String pText) {
        this.pText = pText;
    }

    public void setBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 65, baos);
        this.bitmap = baos.toByteArray();
    }

    public byte[] getByteBitmap() {
        return this.bitmap;
    }

    public void setByteBitmap(byte[] inOb) {
        this.bitmap = inOb;
    }

    public Bitmap getBitmap() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(this.getByteBitmap(), 0, this.getByteBitmap().length);
        return bitmap;
    }
}
