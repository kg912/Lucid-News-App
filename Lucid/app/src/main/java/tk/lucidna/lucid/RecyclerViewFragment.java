package tk.lucidna.lucid;

/**
 * Created by user on 4/1/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    public static ArrayList<NewsObject> all;
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    private SharedPreferences prefs;
    private LinearLayoutManager myLayout;
    private static SwipeRefreshLayout sRLayout;
    private static String ARG_SECTION_NUMBER = "Section Number";

    public RecyclerViewFragment() {

    }

    public static RecyclerViewFragment newInstance(int sectionNumber) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view, container, false);
        Log.v("METHOD", "onCreateView");
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        setMyLayout((LinearLayoutManager) layoutManager);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        ArrayList<NewsObject> mContentItems = new ArrayList<>();
        if(getArguments().getInt(ARG_SECTION_NUMBER) == 0) {
            mContentItems = HomeActivity.adapter.list;
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
            mContentItems = HomeActivity.bList;
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
            mContentItems = HomeActivity.pList;
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
            mContentItems = HomeActivity.tList;
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER) == 4) {
            mContentItems = HomeActivity.iList;
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER) == 5) {
            mContentItems = HomeActivity.lList;
        }

        //penser à passer notre Adapter (ici : CardAdapter) à un RecyclerViewMaterialAdapter
        mAdapter = new RecyclerViewMaterialAdapter(new MainAdapter(mContentItems, getContext()));
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v("METHOD", "onViewCreated");
        //notifier le MaterialViewPager qu'on va utiliser une RecyclerView
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }

    public void setPrefs(SharedPreferences set){
        this.prefs = set;
    }

    public SharedPreferences getPrefs() {
        return this.prefs;
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
            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
        DateTime dateTime = new DateTime().minusHours(1);
        date = dateTime.toDate();
        unix = date.getTime()/1000;
        return unix;
    }


    public static SwipeRefreshLayout getsRLayout() {
        return RecyclerViewFragment.sRLayout;
    }

    public void setsRLayout(SwipeRefreshLayout sRLayout) {
        RecyclerViewFragment.sRLayout = sRLayout;
    }

    public void setmAdapter(RecyclerViewMaterialAdapter exe){
        RecyclerViewFragment.mAdapter = exe;
    }

    public static RecyclerViewMaterialAdapter getmAdapter(){
        return (RecyclerViewMaterialAdapter) RecyclerViewFragment.mAdapter;
    }
}

