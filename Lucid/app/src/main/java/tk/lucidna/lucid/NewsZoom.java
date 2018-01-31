package tk.lucidna.lucid;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;

public class NewsZoom extends Activity {
    RecyclerView mRecyclerView;
    MainAdapter adapter;
    ArrayList<NewsObject> rList;
    ArrayList<NewsObject> nList;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_zoom);
        nList = new ArrayList<NewsObject>();
        rList = HomeActivity.getList();
        img = (ImageView) findViewById(R.id.img);
        if(getIntent().getIntExtra("ID", 1) == 1) {
            for(NewsObject nwo: rList) {
                img.setImageResource(R.drawable.img_nytimes);
                if(nwo.getFeed_id().equals("19")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#1a1a1a"));
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 2) {
            img.setImageResource(R.drawable.img_bbc);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("16") || nwo.getFeed_id().equals("39")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#cc0000"));
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 3) {
            img.setImageResource(R.drawable.img_guardian);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("5") || nwo.getFeed_id().equals("32") || nwo.getFeed_id().equals("33") || nwo.getFeed_id().equals("37")) {
                    nList.add(nwo);
                }
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 4) {
            img.setImageResource(R.drawable.img_cnnmoney);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("46")) {
                    nList.add(nwo);
                }
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 5) {
            img.setImageResource(R.drawable.img_reuters);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("41") || nwo.getFeed_id().equals("21") || nwo.getFeed_id().equals("40")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#1a1a1a"));
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 6) {
            img.setImageResource(R.drawable.img_france);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("7")) {
                    nList.add(nwo);
                }
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 7) {
            img.setImageResource(R.drawable.img_channel);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("48")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#cc0000"));
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 8) {
            img.setImageResource(R.drawable.img_techcrunch);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("42")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#00b300"));
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 9) {
            img.setImageResource(R.drawable.img_dbeast);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("47")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#cc0000"));
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 10) {
            img.setImageResource(R.drawable.img_cnet);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("50")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#cc0000"));
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 11) {
            img.setImageResource(R.drawable.img_washington);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("22")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#1a1a1a"));
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 12) {
            img.setImageResource(R.drawable.img_coconut);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("30")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#29a329"));
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 13) {
            img.setImageResource(R.drawable.cbs_cover);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("6")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#1a1a1a"));
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 14) {
            img.setImageResource(R.drawable.img_time);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("25") || nwo.getFeed_id().equals("27") || nwo.getFeed_id().equals("28")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#cc0000"));
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 15) {
            img.setImageResource(R.drawable.img_hkfp);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("13")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#1a1a1a"));
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 16) {
            img.setImageResource(R.drawable.img_hkgov);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("14")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#c2c2d6"));
            }
        }
        else if(getIntent().getIntExtra("ID", 1) == 17) {
            img.setImageResource(R.drawable.img_chinadaily);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("24") || nwo.getFeed_id().equals("49")) {
                    nList.add(nwo);
                }
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 18) {
            img.setImageResource(R.drawable.img_alj);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("4")) {
                    nList.add(nwo);
                }
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 19) {
            img.setImageResource(R.drawable.img_latimes);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("45")) {
                    nList.add(nwo);
                }
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#1f1f2e"));
            }
        }

        else if(getIntent().getIntExtra("ID", 1) == 20) {
            img.setImageResource(R.drawable.img_scmp);
            for(NewsObject nwo: rList) {
                if(nwo.getFeed_id().equals("44")) {
                    nList.add(nwo);
                }
            }
        }
        adapter = new MainAdapter(nList, getBaseContext());
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }
}
