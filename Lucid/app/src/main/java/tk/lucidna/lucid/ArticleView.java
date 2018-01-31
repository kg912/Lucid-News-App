package tk.lucidna.lucid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.thefinestartist.finestwebview.FinestWebView;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ArticleView extends AppCompatActivity {
    TextView tt;
    TextView bt;
    String res;
    NewsObject nxt = null;
    Toolbar toolbar;
    int num;
    String html;
    Bundle b;
    Document doc;
    String text = "";
    String text3 = "CONNECTION TIMED OUT";
    String text2 = "CONNECTION TIMED OUT";
    String scmpText = "Enter the characters shown in the image.";
    String leng;
    Elements paragraphs;
    Handler mHandler;
    ImageView imgView;
    ArrayList<NewsObject> sendList;
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    CollapsingToolbarLayout toolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tt = (TextView) findViewById(R.id.article_title);
        Typeface typeFace = Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/gidole.ttf");
        Typeface bodyFace = Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/museo.ttf");
        tt.setTypeface(typeFace);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
        sendList = HomeActivity.getAdapter().list;
        imgView  = (ImageView) findViewById(R.id.article_image);
        bt = (TextView) findViewById(R.id.body);
        bt.setTypeface(bodyFace);
        final RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.relative_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        nxt = (NewsObject) getIntent().getSerializableExtra("KEY");

        getSupportActionBar().setTitle(nxt.getFeed_title());


        if(nxt.getFeed_id().equals("5") || nxt.getFeed_id().equals("32") || nxt.getFeed_id().equals("33") || nxt.getFeed_id().equals("37")) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#005689"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#004166"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#005689"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#005689"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#004166"));
            }
        }

        else if(nxt.getFeed_id().equals("25") || nxt.getFeed_id().equals("26") || nxt.getFeed_id().equals("27") || nxt.getFeed_id().equals("28")) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#f5180e"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#c41208"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#f5180e"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#f5180e"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#c41208"));
            }
        }

        else if(nxt.getFeed_id().equals("24") || nxt.getFeed_id().equals("49")) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#0078B4"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#005580"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#0078B4"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#0078B4"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#005580"));
            }
        }

        else if(nxt.getFeed_id().equals("7")) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#00A5E1"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#0083b3"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#00A5E1"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#00A5E1"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#0083b3"));
            }
        }

        else if (nxt.getFeed_id().equals("14") || nxt.getFeed_id().equals("15")) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#ffaaaa"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#ff8080"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#ffaaaa"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#ff6666"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#ff8080"));
            }
        }


        else if(nxt.getFeed_id().equals("4")) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#E68403"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#c97303"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#E68403"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#E68403"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#c97303"));
            }
        }

        else if(nxt.getFeed_id().equals("16") || nxt.getFeed_id().equals("39")) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#BB1919"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#9d1515"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#BB1919"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#BB1919"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#9d1515"));
            }
        }

        else if(nxt.getFeed_id().equals("17")) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#323940"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#22262b"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#323940"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#323940"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#22262b"));
            }
        }

        else if(nxt.getFeed_id().equals("13")) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#0C022C"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#070118"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#0C022C"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#0C022C"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#070118"));
            }
        }

        else if(nxt.getFeed_id().equals("18") || nxt.getFeed_id().equals("36")) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#3467B0"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#295189"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#3467B0"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#3467B0"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#295189"));
            }
        }

        else if(nxt.getFeed_id().equals("19")) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#333333"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#1a1a1a"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#333333"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#333333"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#1a1a1a"));
            }
        }

        else if(nxt.getFeed_id().equals("20") || nxt.getFeed_id().equals("21") || nxt.getFeed_id().equals("40") || nxt.getFeed_id().equals("41") ) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#666666"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#333333"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#666666"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#666666"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#333333"));
            }
        }

        else if(nxt.getFeed_id().equals("31") || nxt.getFeed_id().equals("35")) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#FD0D0D"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#ca0202"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#FD0D0D"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#FD0D0D"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#ca0202"));
            }
        }

        else if(nxt.getFeed_id().equals("30")) {
            toolbarLayout.setContentScrimColor(Color.parseColor("#00CE5B"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#008039"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#00CE5B"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#00CE5B"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#008039"));
            }
        }

        else if(nxt.getFeed_id().equals("22")) {
            //Washington Post
            toolbarLayout.setContentScrimColor(Color.parseColor("#0A0A0A"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#000000"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#0A0A0A"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#0A0A0A"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#000000"));
            }
        } else if (nxt.getFeed_id().equals("44") || nxt.getFeed_id().equals("29")) {
            //SCMP
            toolbarLayout.setContentScrimColor(Color.parseColor("#006391"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#003366"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#006391"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#006391"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#003366"));
            }
        }

        else if(nxt.getFeed_id().equals("50")) {
            //Washington Post
            toolbarLayout.setContentScrimColor(Color.parseColor("#B70000"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#990000"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#B70000"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#B70000"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#990000"));
            }
        }

        else if(nxt.getFeed_id().equals("42")) {
            //Washington Post
            toolbarLayout.setContentScrimColor(Color.parseColor("#0A9E00"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#076600"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#0A9E00"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#0A9E00"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#076600"));
            }
        }

        else if(nxt.getFeed_id().equals("6")) {
            //cbs
            toolbarLayout.setContentScrimColor(Color.parseColor("#384048"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#21262b"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#384048"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#384048"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#21262b"));
            }
        }

        else if(nxt.getFeed_id().equals("47")) {
            //Washington Post
            toolbarLayout.setContentScrimColor(Color.parseColor("#EC2E27"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#d31912"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#EC2E27"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#EC2E27"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#d31912"));
            }
        }

        else if(nxt.getFeed_id().equals("45")) {
            //LATIMES
            toolbarLayout.setContentScrimColor(Color.parseColor("#1e1e1e"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#0d0d0d"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#1e1e1e"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#1e1e1e"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#0d0d0d"));
            }
        }

        else if(nxt.getFeed_id().equals("46")) {
            //CNN MONEY
            toolbarLayout.setContentScrimColor(Color.parseColor("#255ea3"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#1c487d"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#255ea3"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#255ea3"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#1c487d"));
            }
        }

        else if(nxt.getFeed_id().equals("48")) {
            //ChannelNewsAsia
            toolbarLayout.setContentScrimColor(Color.parseColor("#2a2a2a"));
            toolbarLayout.setStatusBarScrimColor(Color.parseColor("#1a1a1a"));
            toolbarLayout.setBackgroundColor(Color.parseColor("#2a2a2a"));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mDilatingDotsProgressBar.setDotColor(Color.parseColor("#2a2a2a"));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#1a1a1a"));
            }
        }

        else {
            toolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
            toolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        doc = Jsoup.parse(nxt.getTitle());
        res = doc.body().text();
        if(res.length() >= 70) {
            tt.setTextSize(24);
        }

        tt.setText(res);


        if(!nxt.isProcessed()) {
            doc = Jsoup.parse(nxt.getContent());
            Elements p = doc.select("p");
            for (Element px : p) {
                text += px.text() + "\n\n";
            }


            Log.v("LENGTH IS:", text.length() + "");
            if (text.length() < 650) {
                mDilatingDotsProgressBar.showNow();
                mHandler = new Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        text2 = "";
                        try {
                            if (nxt.getFeed_id().equals("25") || nxt.getFeed_id().equals("26") || nxt.getFeed_id().equals("27") || nxt.getFeed_id().equals("28")) {
                                Connection.Response response = Jsoup.connect(nxt.getUrl())
                                        .ignoreContentType(true)
                                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US;   rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                                        .referrer("http://www.google.com")
                                        .followRedirects(true)
                                        .execute();

                                doc = response.parse();

                            } else {
                                try {
                                    doc = Jsoup.connect(nxt.getUrl()).get();
                                } catch (Exception e) {

                                }
                            }
                            paragraphs = doc.select("p");
                            for (Element pz : paragraphs) {
                                if (!pz.text().isEmpty()) {
                                    text2 += pz.text().trim();
                                    text2 += "\n\n";
                                }
                            }
                            loadImage(doc);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.v("NEW LENGTH", text2.length() + "");
                                mDilatingDotsProgressBar.hideNow();
                                if (nxt.getFeed_id().equals("23") || nxt.getFeed_id().equals("29")) {
                                    int index;
                                    index = text2.lastIndexOf(scmpText);
                                    Log.v("SCMP", index + "");
                                    if (index < 0) {
                                        bt.setText(text2);
                                        bt.startAnimation(AnimationUtils.loadAnimation(ArticleView.this, android.R.anim.fade_in));
                                        if(!text2.isEmpty()) {
                                            if(text2.length() >= 100) {
                                                for(NewsObject oxt: sendList) {
                                                    if(oxt.getId().equals(nxt.getId())) {
                                                        oxt.setContent(text2);
                                                        oxt.setProcessed(true);
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        index += (scmpText.length());
                                        text3 = text2.substring(index);
                                        text3 = text3.trim() + "\n\n";
                                        bt.setText(text3);
                                        bt.startAnimation(AnimationUtils.loadAnimation(ArticleView.this, android.R.anim.fade_in));
                                        if(!text3.isEmpty()) {
                                            if(text3.length() >= 400) {
                                                for(NewsObject oxt: sendList) {
                                                    if(oxt.getId().equals(nxt.getId())) {
                                                        oxt.setContent(text3);
                                                        oxt.setProcessed(true);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    bt.setText(text2);
                                    bt.startAnimation(AnimationUtils.loadAnimation(ArticleView.this, android.R.anim.fade_in));
                                    if(!text2.isEmpty()) {
                                        if(text2.length() >= 650) {
                                            for(NewsObject oxt: sendList) {
                                                if(oxt.getId().equals(nxt.getId())) {
                                                    oxt.setContent(text2);
                                                    oxt.setProcessed(true);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                }).start();
            } else {
                bt.setText(text);
                if(nxt.getByteBitmap() != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final Bitmap bitmap = nxt.getBitmap();
                            Log.v("RETRIEVED", "BITMAP FROM DISK");
                            ArticleView.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imgView.setImageBitmap(bitmap);
                                }
                            });
                        }
                    }).start();
                }
                else {
                    loadImage(doc);
                }
            }
        }

        else {
            bt.setText(nxt.getContent());
            if(nxt.getByteBitmap() != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = nxt.getBitmap();
                        Log.v("RETRIEVED", "BITMAP FROM DISK");
                        ArticleView.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgView.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();
            }
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FinestWebView.Builder(getBaseContext()).show(nxt.getUrl());
            }
        });


    }

    public final void loadImage(Document doc) {
        if(nxt.getByteBitmap() == null) {
            Element image = null;
            String imageURL;
            int h = 0;
            for (Element s : doc.select("img")) {
                if (!(nxt.getFeed_id().equals("24") || nxt.getFeed_id().equals("42")) || nxt.getFeed_id().equals("49")) {
                    if (s != null && s.hasAttr("src")) {
                        image = s;
                        break;
                    }
                } else if (h > 2) {
                    if (!(nxt.getFeed_id().equals("24") || nxt.getFeed_id().equals("49"))) {
                        if (s != null && s.hasAttr("src")) {
                            image = s;
                            break;
                        }
                    } else {
                        if (h > 2) {
                            if (s != null && s.hasAttr("src")) {
                                image = s;
                                break;
                            }
                        }
                    }
                }
                h++;
            }
            if (image != null) {
                Log.v("THIS ->", image.attr("abs:src"));
                new DownloadImage().execute(image.attr("abs:src"));
            } else {
                Log.v("SORRY!", "NO URL : (");
                if(!(nxt.getFeed_id().equals("23") || nxt.getFeed_id().equals("29"))) {
                    new FillImageTask().execute(nxt);
                }
            }
        }
        else {
            new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = nxt.getBitmap();
                        Log.v("RETRIEVED", "BITMAP FROM DISK");
                        ArticleView.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgView.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();
        }
    }


    private final class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                // Download Image from URL
                    InputStream input = new java.net.URL(imageURL).openStream();
                    // Decode Bitmap
                    bitmap = BitmapFactory.decodeStream(input);

                /*else {
                    InputStream input = new java.net.URL(imageURL).openStream();
                    SVG svg = SVGParser.getSVGFromInputStream(input);
                    Drawable drawable = (Drawable) svg.createPictureDrawable();
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    bitmap = bitmapDrawable.getBitmap();
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            imgView.setImageBitmap(result);
            final Bitmap temp = result;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(temp != null) {
                        for(NewsObject oxt: sendList) {
                            if(oxt.getId().equals(nxt.getId())) {
                                oxt.setBitmap(temp);
                            }
                        }
                    }
                }
            }).start();
        }
    }

   private final class FillImageTask extends AsyncTask<NewsObject, Void, Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(NewsObject... params) {
            NewsObject no = params[0];
            Element image = null;
            String imageURL = null;
            int h = 0;
            try {
                Document inDoc = Jsoup.connect(no.getUrl()).get();
                for(Element x: inDoc.select("img")) {
                    if(h > 1) {
                        if (x != null && x.hasAttr("src")) {
                            imageURL = x.attr("abs:src");
                            Log.v("FILL->", imageURL);
                            break;
                        }
                    }
                    h++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            Bitmap bitmap = null;

            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                if(imageURL.endsWith(".svg") || imageURL.contains(".svg")) {
                   SVG svg = SVGParser.getSVGFromInputStream(input);
                    PictureDrawable pic = svg.createPictureDrawable();
                    Drawable dw = (Drawable) pic;
                    bitmap = ((BitmapDrawable) dw).getBitmap();
                }
                else {
                    bitmap = BitmapFactory.decodeStream(input);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            imgView.setImageBitmap(result);
            final Bitmap temp = result;
            if(temp != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (NewsObject oxt : sendList) {
                            if (oxt.getId().equals(nxt.getId())) {
                                oxt.setBitmap(temp);
                            }
                        }

                    }
                }).start();
            }

            // Close progressdialog

        }
    }
}

