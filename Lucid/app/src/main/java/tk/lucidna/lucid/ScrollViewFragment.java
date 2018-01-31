package tk.lucidna.lucid;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;

public class ScrollViewFragment extends ScrollTabHolderFragment implements NotifyingScrollView.OnScrollChangedListener {

    private static final String ARG_POSITION = "position";


    private NotifyingScrollView mScrollView;

    TextView title;
    TextView titleShortDescription;
    TextView titleDescription;
    TextView textSendEmail;
    TextView textContact;
    TextView textEmail;
    LinearLayout layout1;
    LinearLayout layout2;
    RecyclerView mRecyclerView;
    MainAdapter mAdapter;
    ImageView titleImage;

    private int mPosition;
    private CardView cardView;

    public static Fragment newInstance(int position) {
        ScrollViewFragment f = new ScrollViewFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scrollview, null);

        mScrollView = (NotifyingScrollView) v.findViewById(R.id.scrollview);
        mScrollView.setOnScrollChangedListener(this);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        ArrayList<NewsObject> mContentItems = new ArrayList<>();
        if(mPosition == 0) {
            mContentItems = HomeActivity.adapter.list;
        }
        else if(mPosition == 1) {
            mContentItems = HomeActivity.bList;
        }
        else if(mPosition == 2) {
            mContentItems = HomeActivity.pList;
        }
        else if(mPosition == 3) {
            mContentItems = HomeActivity.tList;
        }
        else if(mPosition == 4) {
            mContentItems = HomeActivity.iList;
        }
        else if(mPosition == 5) {
            mContentItems = HomeActivity.lList;
        }

        //penser à passer notre Adapter (ici : CardAdapter) à un RecyclerViewMaterialAdapter
        mAdapter = new MainAdapter(mContentItems, getContext());
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void adjustScroll(int scrollHeight, int headerTranslationY)
    {
        mScrollView.setScrollY(headerTranslationY - scrollHeight);
    }

    @Override
    public void onScrollChanged(ScrollView view, int l, int t, int oldl, int oldt)
    {
        if (mScrollTabHolder != null)
            mScrollTabHolder.onScroll(view, l, t, oldl, oldt, mPosition);

    }



}
