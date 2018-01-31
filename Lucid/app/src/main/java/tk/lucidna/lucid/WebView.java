package tk.lucidna.lucid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.thefinestartist.finestwebview.FinestWebView;

public class WebView extends AppCompatActivity {
    String webLink;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        title = getIntent().getStringExtra("Title");
        webLink = getIntent().getStringExtra("KEY_URL");
        getSupportActionBar().setTitle(title);
        new FinestWebView.Builder(getBaseContext()).show(webLink);
        /*android.webkit.WebView aWebView = (android.webkit.WebView)findViewById(R.id.webView);
        WebSettings aWebSettings = aWebView.getSettings();
        aWebSettings.setJavaScriptEnabled(true); // enable JavaScript usage
        aWebView.loadUrl(webLink); // display the web
        aWebView.setWebViewClient(new WebViewClient());*/
    }

}
