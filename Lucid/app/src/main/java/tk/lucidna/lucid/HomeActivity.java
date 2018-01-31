package tk.lucidna.lucid;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.ConnectionQuality;
import com.facebook.network.connectionclass.DeviceBandwidthSampler;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Instant;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, ConnectionClassManager.ConnectionClassStateChangeListener
{
    Toolbar mActionBarToolbar;
    TextView tv;
    Button refresh;
    String res;
    ArrayList<NewsObject> list;
    ArrayList<String> titleList;
    ArrayList<String> article;
    String jString;
    Handler mHandler;
    private boolean autoRefresh;
    public static ArrayList<NewsObject> cList;
    boolean firstRun;
    static MainAdapter adapter;
    String timestamp;
    FilterArticleSearch fas;
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    LinearLayoutManager myLayout;
    static SharedPreferences prefs;
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    Document doc;
    String title;
    Context context = this;
    public static ArrayList<NewsObject> nList;
    public static ArrayList<NewsObject> xList;
    public static ArrayList<NewsObject> bList;
    public static ArrayList<NewsObject> pList;
    public static ArrayList<NewsObject> tList;
    public static ArrayList<NewsObject> iList;
    public static ArrayList<NewsObject> lList;
    RecyclerView recyclerView;
    SwipeRefreshLayout sRLayout;
    TextView errortext;
    ConnectionClassManager.ConnectionClassStateChangeListener mListener;
    DeviceBandwidthSampler sampler;
    private Dialog searchDialog;
    private boolean searchOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        errortext = (TextView) findViewById(R.id.errorText);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int interval = 10000;
        long flexSecs = 5000;
        String tag = "Auto-Update Lucid";
        ConnectionClassManager ccManager = ConnectionClassManager.getInstance();
        // Initialize Device Bandwidth Listener
         sampler = DeviceBandwidthSampler.getInstance();
        ccManager.register(mListener);
        setSearchDialog(new Dialog(HomeActivity.this, R.style.MaterialSearch));
        final SharedPreferences settings = getSharedPreferences("prefs", 0);
        setPrefs(settings);
        final boolean firstRun = settings.getBoolean("firstRun", true);
        getSupportActionBar().setTitle("Lucid ");
        final MyCustomLayoutManager layoutManager = new MyCustomLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setMyLayout(layoutManager);
        mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
        sRLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        sRLayout.setColorSchemeColors(Color.parseColor("#009900"));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final PeriodicTask task = new PeriodicTask.Builder()
                .setService(GCMService.class)
                .setTag("TASK_TAG_PERIODIC")
                .setPeriod(3600L)
                .setRequiredNetwork(Task.NETWORK_STATE_UNMETERED)
                .build();

        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refreshing...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sRLayout.setEnabled(true);
                if (isNetworkOnline()) {
                    if (settings.getBoolean("firstRun", true)) {
                        errortext.setVisibility(View.INVISIBLE);
                        mDilatingDotsProgressBar.showNow();
                        loadItems(layoutManager, recyclerView);
                        settings.edit().putBoolean("firstRun", false).commit();
                        GcmNetworkManager.getInstance(getBaseContext()).schedule(task);
                    } else {
                        errortext.setVisibility(View.INVISIBLE);
                        sRLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                sRLayout.setRefreshing(true);
                            }
                        });
                        refreshItems(layoutManager, recyclerView);
                    }
                } else {
                    if (settings.getBoolean("firstRun", true)) {
                        errortext.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "NOT CONNECTED TO THE INTERNET", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            xList = Database.read(getBaseContext());
                            inflateView(layoutManager, xList, recyclerView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView= (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        setMyLayout(layoutManager);
        if ( firstRun ) {
            if(isNetworkOnline()) {
                errortext.setVisibility(View.INVISIBLE);
                mDilatingDotsProgressBar.showNow();
                loadItems(layoutManager, recyclerView);
                Toast.makeText(getApplicationContext(), "Welcome to Lucid", Toast.LENGTH_SHORT).show();
                settings.edit().putBoolean("firstRun", false).commit();
                GcmNetworkManager.getInstance(getBaseContext()).schedule(task);
            }
            else {
                errortext.setVisibility(View.VISIBLE);
            }
        }

        else {
            try {
                xList = Database.read(getBaseContext());
                inflateView(layoutManager, xList, recyclerView);
                sortList(xList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(settings.getBoolean("refreshStart", true)) {
                if (isNetworkOnline()) {
                    errortext.setVisibility(View.INVISIBLE);
                    Log.v("STATUS", "CONNECTED");
                    sRLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            sRLayout.setRefreshing(true);
                        }
                    });

                    refreshItems(layoutManager, recyclerView);
                } else {
                    Log.v("STATUS", "DISCONNECTED");
                    Toast.makeText(getApplicationContext(), "Not Connected to the Internet", Toast.LENGTH_LONG).show();
                }
            }
        }
        sRLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (settings.getBoolean("firstRun", true)) {
                    if (isNetworkOnline()) {
                        errortext.setVisibility(View.INVISIBLE);
                        loadItems(layoutManager, recyclerView);
                        sRLayout.setRefreshing(false);
                        settings.edit().putBoolean("firstRun", false).commit();
                        GcmNetworkManager.getInstance(getBaseContext()).schedule(task);
                    } else {
                        errortext.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (isNetworkOnline()) {
                        refreshItems(layoutManager, recyclerView);
                    } else {
                        sRLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), "Not Connected to the Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.v("onBackPressed", "is called!");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if(getSearchDialog().isShowing()) {
            Log.v("Search Open", "YES");
            getSearchDialog().dismiss();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.action_search) {
            loadToolBarSearch();
            setSearchOpen(true);
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.library) {
            // Handle the camera action
            Intent i = new Intent(this, ViewActivity.class);
            startActivity(i);
        }
        else if (id == R.id.sources) {
            Intent j = new Intent(this, LibraryActivity.class);
            startActivity(j);
        }
        else if (id == R.id.prefs) {
            Intent intent = new Intent( this, PrefsScreen.class );
            intent.putExtra( PrefsScreen.EXTRA_SHOW_FRAGMENT, PrefsScreen.DataSyncPreferenceFragment.class.getName() );
            intent.putExtra( PrefsScreen.EXTRA_NO_HEADERS, true );
            startActivity(intent);
        }
        else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadItems(final LinearLayoutManager lman, final RecyclerView rView) {
        final Intent intent = new Intent(getApplicationContext(), StartupService.class)
                .putExtra("rr", new ResultReceiver(new Handler()) {
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                        super.onReceiveResult(resultCode, resultData);
                        long epoch;
                        Log.v("SERVICE -> ACTIVITY", "SUCCESSFUL");
                        list = (ArrayList<NewsObject>) resultData.getSerializable("KEY");
                        if (list.get(0) != null) {
                            getPrefs().edit().putLong("ts", getCurrentStamp()).commit();
                            Log.v("LAST TIME STAMP", getCurrentStamp() + "");
                        }
                        Log.v("SERVICE -> ACTIVITY", "NO ERRORS SO FAR");
                        list = shuffleList(list);
                        list = shuffleList(list);
                        inflateView(lman, list, rView);
                        setList(list);
                        mDilatingDotsProgressBar.hideNow();
                        Toast.makeText(getApplicationContext(), "All items loaded", Toast.LENGTH_SHORT).show();
                        try {
                            Database.write(getBaseContext(), list);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sortList(list);
                    }
                });
        long l = getPrefs().getLong("ts", getTimeStamp());
        intent.putExtra("timestamp", l);
        startService(intent);
    }

    public void inflateView(LinearLayoutManager llm, ArrayList<NewsObject> nol, RecyclerView recyclerView) {
        adapter = new MainAdapter(nol, getBaseContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void refreshItems(final LinearLayoutManager lman, final RecyclerView rView) {
        final Intent intent = new Intent(getApplicationContext(), StartupService.class)
                .putExtra("rr", new ResultReceiver(new Handler()) {
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                        ArrayList<NewsObject> getList = new ArrayList<NewsObject>();
                        int q;
                        super.onReceiveResult(resultCode, resultData);
                        Log.v("SERVICE -> ACTIVITY", "SUCCESSFUL");
                        getList = (ArrayList<NewsObject>) resultData.getSerializable("KEY");
                        getPrefs().edit().putLong("ts", getCurrentStamp()).commit();
                        Log.v("LAST TIME STAMP", getCurrentStamp() + "");

                        Log.v("SERVICE -> ACTIVITY", "NO ERRORS SO FAR");
                        for (NewsObject nd : adapter.list) {
                            for (int h = 0; h < getList.size(); h++) {
                                if (nd.getId().equals(getList.get(h).getId())) {
                                    Log.v("REMOVED", getList.get(h).getTitle());
                                    getList.remove(h);
                                }
                            }
                        }
                        Collections.shuffle(getList);
                        Collections.shuffle(getList);
                        adapter.list.addAll(0, getList);
                        adapter.notifyDataSetChanged();
                        setList(adapter.list);
                        sRLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), "Refreshed Successfully!", Toast.LENGTH_SHORT).show();
                        try {
                            Database.write(getBaseContext(), adapter.list);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sortList(adapter.list);
                    }
                });
        long x = handOverStamp(getPrefs().getLong("ts", getTimeStamp()), getCurrentStamp());
        intent.putExtra("timestamp", x);
        startService(intent);
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


    public Context getLucidContext() {
        return getApplicationContext();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        adapter.getFilter().filter(query);
        if(query.isEmpty()) {
            try {
                inflateView(getMyLayout(), Database.read(getBaseContext()), recyclerView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private List<NewsObject> filter(ArrayList<NewsObject> models, String query) {
        query = query.toLowerCase();

        final ArrayList<NewsObject> filteredModelList = new ArrayList<>();
        for (NewsObject model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public void setMyLayout(LinearLayoutManager myLayout) {
        this.myLayout = myLayout;
    }

    public LinearLayoutManager getMyLayout() {
        return this.myLayout;
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
        DateTime dateTime = new DateTime().minusHours(8);
        date = dateTime.toDate();
        unix = date.getTime()/1000;
        return unix;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("STOP", "CALLED");
        try {
            Database.write(getBaseContext(), adapter.list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPrefs(SharedPreferences set){
        HomeActivity.prefs = set;
    }

    public static  SharedPreferences getPrefs() {
        return HomeActivity.prefs;
    }

    public static void setList(ArrayList<NewsObject> enter) {
        HomeActivity.cList = enter;
    }

    public static ArrayList<NewsObject> getList() {
        return HomeActivity.cList;
    }

    public static MainAdapter getAdapter() {
        return HomeActivity.adapter;
    }

    @Override
    public void onBandwidthStateChange(ConnectionQuality bandwidthState) {
        Log.v("QUALITY", bandwidthState.toString());
    }

    public static long getCurrentStamp() {
        long unixTime = System.currentTimeMillis() / 1000L;
        return unixTime;
    }

    public void loadToolBarSearch() {
        int index = 0;
        final ArrayList<String> mCountries = new ArrayList<String>();
        ArrayList<NewsObject> myList = getList();
        for(NewsObject nwo: myList) {
            mCountries.add(nwo.getTitle() + "`"  + myList.indexOf(nwo));
        }

        ArrayList<String> countryStored = SharedPreference.loadList(HomeActivity.this, Utils.PREFS_NAME, Utils.KEY_COUNTRIES);

        View view = HomeActivity.this.getLayoutInflater().inflate(R.layout.view_toolbar_search, null);
        LinearLayout parentToolbarSearch = (LinearLayout) view.findViewById(R.id.parent_toolbar_search);
        ImageView imgToolBack = (ImageView) view.findViewById(R.id.img_tool_back);
        final EditText edtToolSearch = (EditText) view.findViewById(R.id.edt_tool_search);
        ImageView imgToolMic = (ImageView) view.findViewById(R.id.img_tool_mic);
        final ListView listSearch = (ListView) view.findViewById(R.id.list_search);
        final TextView txtEmpty = (TextView) view.findViewById(R.id.txt_empty);

        Utils.setListViewHeightBasedOnChildren(listSearch);

        edtToolSearch.setHint("Search news articles");

        final Dialog toolbarSearchDialog = getSearchDialog();
        toolbarSearchDialog.setContentView(view);
        toolbarSearchDialog.setCancelable(false);
        toolbarSearchDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        toolbarSearchDialog.getWindow().setGravity(Gravity.BOTTOM);
        toolbarSearchDialog.show();

        toolbarSearchDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        countryStored = (countryStored != null && countryStored.size() > 0) ? countryStored : new ArrayList<String>();
        final SearchAdapter searchAdapter = new SearchAdapter(HomeActivity.this, countryStored, false);

        listSearch.setVisibility(View.VISIBLE);
        listSearch.setAdapter(searchAdapter);


        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int x;
                String[] split;
                String country = String.valueOf(adapterView.getItemAtPosition(position));
                SharedPreference.addList(HomeActivity.this, Utils.PREFS_NAME, Utils.KEY_COUNTRIES, country);
                Log.v("OnItemClick", "I am called");
                Log.v("COUNTRY", country);
                split = country.split("`");
                x = Integer.parseInt(split[1]);
                edtToolSearch.setText(split[0]);
                listSearch.setVisibility(View.GONE);
                Intent i = new Intent(getBaseContext(), ArticleView.class);
                i.putExtra("KEY", getList().get(x));
                startActivity(i);
            }
        });
        edtToolSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                listSearch.setVisibility(View.VISIBLE);
                searchAdapter.updateList(mCountries, true);


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> filterList = new ArrayList<String>();
                boolean isNodata = false;
                if (s.length() > 0) {
                    for (int i = 0; i < mCountries.size(); i++) {


                        if (mCountries.get(i).toLowerCase().startsWith(s.toString().trim().toLowerCase())) {

                            filterList.add(mCountries.get(i));

                            listSearch.setVisibility(View.VISIBLE);
                            searchAdapter.updateList(filterList, true);
                            isNodata = true;
                        }

                        if (mCountries.get(i).toLowerCase().contains(s.toString().trim().toLowerCase())) {

                            filterList.add(mCountries.get(i));

                            listSearch.setVisibility(View.VISIBLE);
                            searchAdapter.updateList(filterList, true);
                            isNodata = true;
                        }
                    }
                    if (!isNodata) {
                        listSearch.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                        txtEmpty.setText("No articles found");
                    }
                } else {
                    listSearch.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarSearchDialog.dismiss();
            }
        });

        imgToolMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtToolSearch.setText("");

            }
        });


    }

    public Dialog getSearchDialog() {
        return searchDialog;
    }

    public void setSearchDialog(Dialog searchDialog) {
        this.searchDialog = searchDialog;
    }

    public boolean isSearchOpen() {
        return searchOpen;
    }

    public void setSearchOpen(boolean searchOpen) {
        this.searchOpen = searchOpen;
    }

    public long handOverStamp(long date1, long date2) {
        Hours h = Hours.hoursBetween(new Instant(date1*1000), new Instant(date2*1000));
        int j = h.getHours();
        Log.v("TIME DIFF", j + "");
        if(j > 8) {
            return getTimeStamp();
        }
        else {
            return getPrefs().getLong("ts", getTimeStamp());
        }
    }

    public void sortList(ArrayList<NewsObject> all) {
        bList = new ArrayList<NewsObject>();
        pList = new ArrayList<NewsObject>();
        tList = new ArrayList<NewsObject>();
        iList = new ArrayList<NewsObject>();
        lList = new ArrayList<NewsObject>();

            for(NewsObject gx: all) {
                if(gx.getFeed_id().equals("48") || gx.getFeed_id().equals("41") || gx.getFeed_id().equals("32") || gx.getFeed_id().equals("46") ) {
                    bList.add(gx);
                }

                else if(gx.getFeed_id().equals("39") || gx.getFeed_id().equals("21") || gx.getFeed_id().equals("33")) {
                    pList.add(gx);
                }

                else if(gx.getFeed_id().equals("40") || gx.getFeed_id().equals("37") || gx.getFeed_id().equals("28") || gx.getFeed_id().equals("42") || gx.getFeed_id().equals("50") ) {
                    tList.add(gx);
                }

                else if(gx.getFeed_id().equals("4") || gx.getFeed_id().equals("7") || gx.getFeed_id().equals("22") || gx.getFeed_id().equals("5") || gx.getFeed_id().equals("19") || gx.getFeed_id().equals("27")) {
                    iList.add(gx);
                }

                else if(gx.getFeed_id().equals("44") || gx.getFeed_id().equals("14") || gx.getFeed_id().equals("49") || gx.getFeed_id().equals("30") || gx.getFeed_id().equals("24") || gx.getFeed_id().equals("13")) {
                    lList.add(gx);
                }
            }

    }
}
