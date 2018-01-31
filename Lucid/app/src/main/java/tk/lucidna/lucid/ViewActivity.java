package tk.lucidna.lucid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.squareup.picasso.Picasso;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.joda.time.DateTime;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;


public class ViewActivity extends AppCompatActivity {

    MaterialViewPager materialViewPager;
    View headerLogo;
    ImageView headerLogoContent;
    private static ArrayList<NewsObject> list;
    String timestamp;
    LinearLayoutManager myLayout;
    private static SharedPreferences prefs;
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    ArrayList<NewsObject> nList;
    ArrayList<NewsObject> xList;
    SwipeRefreshLayout sRLayout;
    TextView errortext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final SharedPreferences settings = getSharedPreferences("prefs", 0);
        final boolean firstRun = settings.getBoolean("firstRun", true);
        setPrefs(settings);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#000000"));
        }
        errortext = (TextView) findViewById(R.id.errorText);
        mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
        //4 onglets
        final int tabCount = 6;
        //les vues définies dans @layout/header_logo
        headerLogo = findViewById(R.id.headerLogo);
        headerLogoContent = (ImageView) findViewById(R.id.headerLogoContent);
        final ArrayList<NewsObject> eList = HomeActivity.getAdapter().list;
        this.materialViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        this.materialViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                //je créé pour chaque onglet un RecyclerViewFragment
                Log.v("POSITION", position + "");
                return RecyclerViewFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return tabCount;
            }

            //le titre à afficher pour chaque page
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Top Stories";
                    case 1:
                        return "Business";
                    case 2:
                        return "Politics";
                    case 3:
                        return "Technology";
                    case 4:
                        return "International";
                    case 5:
                        return "Local";
                    default:
                        return "Page " + position;
                }
            }

            int oldItemPosition = -1;

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);
                //seulement si la page est différente
                if (oldItemPosition != position) {
                    oldItemPosition = position;

                    //définir la nouvelle couleur et les nouvelles images
                    String imageUrl = null;
                    int color = Color.BLACK;
                    Drawable newDrawable = null;
                    Drawable image;
                    int fadeDuration = 400;
                    switch (position) {
                        case 0:
                            image = getResources().getDrawable(R.drawable.all);
                            color = getResources().getColor(R.color.purple);
                            newDrawable = getResources().getDrawable(R.drawable.all_img);
                            materialViewPager.setColor(color, fadeDuration);
                            materialViewPager.setImageDrawable(image, fadeDuration);
                            toggleLogo(newDrawable, color, fadeDuration);
                            break;
                        case 1:
                            image = getResources().getDrawable(R.drawable.bns2);
                            color = getResources().getColor(R.color.orange);
                            newDrawable = getResources().getDrawable(R.drawable.business);
                            materialViewPager.setColor(color, fadeDuration);
                            materialViewPager.setImageDrawable(image, fadeDuration);
                            toggleLogo(newDrawable, color, fadeDuration);
                            break;
                        case 2:
                            image = getResources().getDrawable(R.drawable.politics);
                            color = getResources().getColor(R.color.brown);
                            newDrawable = getResources().getDrawable(R.drawable.pol_pic);
                            materialViewPager.setColor(color, fadeDuration);
                            materialViewPager.setImageDrawable(image, fadeDuration);
                            toggleLogo(newDrawable, color, fadeDuration);
                            break;
                        case 3:
                            image = getResources().getDrawable(R.drawable.tech);
                            color = getResources().getColor(R.color.cyan);
                            newDrawable = getResources().getDrawable(R.drawable.light);
                            materialViewPager.setColor(color, fadeDuration);
                            materialViewPager.setImageDrawable(image, fadeDuration);
                            toggleLogo(newDrawable, color, fadeDuration);
                            break;
                        case 4:
                            image = getResources().getDrawable(R.drawable.interna);
                            color = getResources().getColor(R.color.green);
                            newDrawable = getResources().getDrawable(R.drawable.earth);
                            materialViewPager.setColor(color, fadeDuration);
                            materialViewPager.setImageDrawable(image, fadeDuration);
                            toggleLogo(newDrawable, color, fadeDuration);
                            break;
                        case 5:
                            image = getResources().getDrawable(R.drawable.hk);
                            color = getResources().getColor(R.color.newred);
                            newDrawable = getResources().getDrawable(R.drawable.city_pic);
                            materialViewPager.setColor(color, fadeDuration);
                            materialViewPager.setImageDrawable(image, fadeDuration);
                            toggleLogo(newDrawable, color, fadeDuration);
                            break;
                    }

                }
            }
        });

        //permet au viewPager de garder 4 pages en mémoire (à ne pas utiliser sur plus de 4 pages !)
        this.materialViewPager.getViewPager().setOffscreenPageLimit(tabCount);
        //relie les tabs au viewpager
        this.materialViewPager.getPagerTitleStrip().setViewPager(this.materialViewPager.getViewPager());
    }

    public static ArrayList<NewsObject> shuffleList(ArrayList<NewsObject> a) {
        int n = a.size();
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
        return a;
    }

    private static void swap(ArrayList<NewsObject> a, int i, int change) {
        NewsObject helper;
        helper = a.get(i);
        a.set(i, a.get(change));
        a.set(change, helper);
    }

    public boolean isNetworkOnline() {
        boolean status= false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;

    }

    public static long getTimeStamp(){
        Date date;
        long unix;
        DateTime dateTime = new DateTime().minusHours(9);
        date = dateTime.toDate();
        unix = date.getTime()/1000;
        return unix;
    }



    private void toggleLogo(final Drawable newLogo, final int newColor, int duration){

        //animation de disparition
        final AnimatorSet animatorSetDisappear = new AnimatorSet();
        animatorSetDisappear.setDuration(duration);
        animatorSetDisappear.playTogether(
                ObjectAnimator.ofFloat(headerLogo, "scaleX", 0),
                ObjectAnimator.ofFloat(headerLogo, "scaleY", 0)
        );

        //animation d'apparition
        final AnimatorSet animatorSetAppear = new AnimatorSet();
        animatorSetAppear.setDuration(duration);
        animatorSetAppear.playTogether(
                ObjectAnimator.ofFloat(headerLogo, "scaleX", 1),
                ObjectAnimator.ofFloat(headerLogo, "scaleY", 1)
        );

        //après la disparition
        animatorSetDisappear.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                //modifie la couleur du cercle
                ((GradientDrawable) headerLogo.getBackground()).setColor(newColor);

                //modifie l'image contenue dans le cercle
                headerLogoContent.setImageDrawable(newLogo);

                //démarre l'animation d'apparition
                animatorSetAppear.start();
            }
        });

        //démarre l'animation de disparition
        animatorSetDisappear.start();
    }

    public static SharedPreferences getPrefs() {
        return ViewActivity.prefs;
    }

    public void setPrefs(SharedPreferences prefs) {
        ViewActivity.prefs = prefs;
    }


    public static void setList(ArrayList<NewsObject> inList) {
        ViewActivity.list = inList;
    }


}