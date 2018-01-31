package tk.lucidna.lucid;

/**
 * Created by karan on 3/28/16.
 */

import android.support.v7.widget.RecyclerView;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;


public class FilterArticleSearch extends Filter {
    private final MainAdapter mAdapter;
    List<NewsObject> newsList;
    List<NewsObject> copy;
    List<NewsObject> filteredList;

    public FilterArticleSearch(MainAdapter mAdapter, ArrayList<NewsObject> inList) {
        this.mAdapter = mAdapter;
        this.newsList = inList;
        copy = new ArrayList<NewsObject>(inList);
        filteredList = new ArrayList<NewsObject>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredList.clear();
        final FilterResults results = new FilterResults();

        if (constraint.length() == 0) {
            filteredList.addAll(copy);

        } else {
            final String filterPattern = constraint.toString().toLowerCase().trim();

            for (final NewsObject nox : newsList) {
                if (nox.getTitle().contains(constraint)) {
                    filteredList.add(nox);
                } else if (nox.getFeed_title().contains(constraint)) {
                    filteredList.add(nox);
                }
            }
        }
        results.values = filteredList;
        results.count = filteredList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        mAdapter.list.clear();
        mAdapter.list.addAll((List<NewsObject>) results.values);
        mAdapter.notifyDataSetChanged();
    }


}