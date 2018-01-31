package tk.lucidna.lucid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 4/24/2016.
 */
public class SearchAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mCountries;
    private LayoutInflater mLayoutInflater;
    private boolean mIsFilterList;

    public SearchAdapter(Context context, ArrayList<String> countries, boolean isFilterList) {
        this.mContext = context;
        this.mCountries =countries;
        this.mIsFilterList = isFilterList;
    }


    public void updateList(ArrayList<String> filterList, boolean isFilterList) {
        this.mCountries = filterList;
        this.mIsFilterList = isFilterList;
        notifyDataSetChanged ();
    }

    @Override
    public int getCount() {
        return mCountries.size();
    }

    @Override
    public String getItem(int position) {
        return mCountries.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String[] splitter;
        View v = convertView;
        ViewHolder holder = null;
        if(v==null){

            holder = new ViewHolder();

            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = mLayoutInflater.inflate(R.layout.list_item_search, parent, false);
            holder.txtCountry = (TextView)v.findViewById(R.id.txt_country);
            v.setTag(holder);
        } else{

            holder = (ViewHolder) v.getTag();
        }
        splitter = mCountries.get(position).split("`");
        holder.txtCountry.setText(splitter[0]);

        Drawable searchDrawable,recentDrawable;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            searchDrawable = mContext.getResources().getDrawable(R.drawable.expandsearch, null);
            recentDrawable = mContext.getResources().getDrawable(R.drawable.searched, null);

        } else {
            searchDrawable = mContext.getResources().getDrawable(R.drawable.expandsearch);
            recentDrawable = mContext.getResources().getDrawable(R.drawable.searched);
        }
        if(mIsFilterList) {
            holder.txtCountry.setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, null, null);
        }else {
            holder.txtCountry.setCompoundDrawablesWithIntrinsicBounds(recentDrawable, null, null, null);

        }
        return v;
    }

}

class ViewHolder{
    TextView txtCountry;
}
