package tk.lucidna.lucid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class LibraryActivity extends AppCompatActivity {

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("News Library");
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        List<ItemObjects> gaggeredList = getListItemData();

        SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(LibraryActivity.this, gaggeredList);
        recyclerView.setAdapter(rcAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent( this, PrefsScreen.class );

            intent.putExtra( PrefsScreen.EXTRA_SHOW_FRAGMENT, PrefsScreen.DataSyncPreferenceFragment.class.getName() );
            intent.putExtra( PrefsScreen.EXTRA_NO_HEADERS, true );
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private List<ItemObjects> getListItemData(){
        List<ItemObjects> listViewItems = new ArrayList<ItemObjects>();
        listViewItems.add(new ItemObjects(1, R.drawable.label_nytimes));
        listViewItems.add(new ItemObjects(2, R.drawable.label_bbc));
        listViewItems.add(new ItemObjects(3, R.drawable.label_guardian));
        listViewItems.add(new ItemObjects(4, R.drawable.label_cnnmoney));
        listViewItems.add(new ItemObjects(5, R.drawable.label_reuters));
        listViewItems.add(new ItemObjects(6, R.drawable.label_france));
        listViewItems.add(new ItemObjects(7, R.drawable.label_channel));
        listViewItems.add(new ItemObjects(8, R.drawable.label_techcrunch));
        listViewItems.add(new ItemObjects(9, R.drawable.label_dailybeast));
        listViewItems.add(new ItemObjects(10, R.drawable.label_cnet));
        listViewItems.add(new ItemObjects(11, R.drawable.label_wp));
        listViewItems.add(new ItemObjects(12, R.drawable.label_coconut));
        listViewItems.add(new ItemObjects(13, R.drawable.label_cbs));
        listViewItems.add(new ItemObjects(14, R.drawable.label_time));
        listViewItems.add(new ItemObjects(15, R.drawable.label_hkfp));
        listViewItems.add(new ItemObjects(16, R.drawable.label_govhk));
        listViewItems.add(new ItemObjects(17, R.drawable.label_cdaily));
        listViewItems.add(new ItemObjects(18, R.drawable.label_aljaz));
        listViewItems.add(new ItemObjects(19, R.drawable.label_latimes));
        listViewItems.add(new ItemObjects(20, R.drawable.label_scmp));
        return listViewItems;
    }
}
