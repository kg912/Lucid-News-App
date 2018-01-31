package tk.lucidna.lucid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by user on 4/9/2016.
 */
public class SplashActivity extends AppCompatActivity {
    ArrayList<NewsObject> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent2 = new Intent(this, HomeActivity.class);
        startActivity(intent2);
        finish();
    }


}
