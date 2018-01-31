package tk.lucidna.lucid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan on 03/24/2016.
 */
public class MainAdapter extends  RecyclerView.Adapter<MainAdapter.RecyclerViewHolder> implements Filterable {
    View v;
    Context context;
    int size;
    ArrayList<NewsObject> list;
    LayoutInflater inflater;
    CharSequence constraint = "";
    private List<NewsObject> mNList;
    List<NewsObject> visibleObjects;
    public MainAdapter(ArrayList<NewsObject> xlist, Context context) {
        setList(xlist);
        this.visibleObjects = xlist;
        this.context = context;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.item_list, parent, false);

        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        size = list.get(position).getTitle().length();
        if(size > 80 ) {
            holder.tv1.setTextSize(13);
            holder.tv2.setTextSize(13);
        }

        if(size >= 120) {
            holder.tv1.setTextSize(12);
            holder.tv2.setTextSize(12);
        }

        holder.tv1.setText(list.get(position).getTitle());

        if(list.get(position).getFeed_id().equals("1") || list.get(position).getFeed_id().equals("2") || list.get(position).getFeed_id().equals("3") || list.get(position).getFeed_id().equals("100") ) {
            holder.imageView.setImageResource(R.drawable.guardian2);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("4")) {
            //CNN Money
            holder.imageView.setImageResource(R.drawable.cnnmoney);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("19")) {
            holder.tv2.setText(list.get(position).getFeed_title());
            holder.imageView.setImageResource(R.drawable.nytimes);
        }

        else if(list.get(position).getFeed_id().equals("25") || list.get(position).getFeed_id().equals("26") || list.get(position).getFeed_id().equals("27") || list.get(position).getFeed_id().equals("28")) {
            holder.imageView.setImageResource(R.drawable.time);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("24") || list.get(position).getFeed_id().equals("49")) {
            holder.imageView.setImageResource(R.drawable.cdaily);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("48")) {
            holder.imageView.setImageResource(R.drawable.channel);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("47")) {
            holder.imageView.setImageResource(R.drawable.dailybeast);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("30")) {
            holder.imageView.setImageResource(R.drawable.coco);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("8")) {
            holder.imageView.setImageResource(R.drawable.huff);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("6")) {
            holder.imageView.setImageResource(R.drawable.cbs);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("7")) {
            holder.imageView.setImageResource(R.drawable.france);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("400")) {
            holder.imageView.setImageResource(R.drawable.alj);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("16") || list.get(position).getFeed_id().equals("39")) {
            holder.imageView.setImageResource(R.drawable.bbc);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("13")) {
            holder.imageView.setImageResource(R.drawable.hkfp);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("14") || list.get(position).getFeed_id().equals("15")) {
            holder.imageView.setImageResource(R.drawable.hkgov);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("18") || list.get(position).getFeed_id().equals("36")) {
            holder.imageView.setImageResource(R.drawable.abc);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("20") || list.get(position).getFeed_id().equals("21") || list.get(position).getFeed_id().equals("40") || list.get(position).getFeed_id().equals("41")) {
            holder.imageView.setImageResource(R.drawable.reuters);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("44") || list.get(position).getFeed_id().equals("29")) {
            holder.imageView.setImageResource(R.drawable.scmp);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("45")) {
            //LA TImes
            holder.imageView.setImageResource(R.drawable.latimes);
            holder.tv2.setText(list.get(position).getFeed_title());
        }



        else if(list.get(position).getFeed_id().equals("22")) {
            holder.imageView.setImageResource(R.drawable.wp);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("50")) {
            holder.imageView.setImageResource(R.drawable.cnet);
            holder.tv2.setText(list.get(position).getFeed_title());
        }

        else if(list.get(position).getFeed_id().equals("42")) {
            holder.imageView.setImageResource(R.drawable.techcrunch);
            holder.tv2.setText(list.get(position).getFeed_title());
        }


        else {
            holder.imageView.setImageResource(R.drawable.menuicon);
            holder.tv2.setText(list.get(position).getFeed_title());
        }


        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ArticleView.class);
                i.putExtra("KEY", list.get(position));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.v("TITLE LEN", list.get(position).getTitle().length() + "");
                Log.v("SENDING THIS OVER ->", list.get(position).getTitle());
                context.startActivity(i);
            }
        };

        holder.itemView.setOnClickListener(clickListener);
        holder.itemView.setTag(holder);
    }



    @Override
    public int getItemCount() {
        return this.list.size();
    }

    @Override
    public Filter getFilter() {
        return new FilterArticleSearch(this, list);
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2;
        ImageView imageView;
        public RecyclerViewHolder (View v) {
            super(v);

            tv1= (TextView) itemView.findViewById(R.id.list_title);
            tv2= (TextView) itemView.findViewById(R.id.list_desc);
            imageView= (ImageView) itemView.findViewById(R.id.list_avatar);
        }
    }

    private List<NewsObject> filter(List<NewsObject> models, String query) {
        query = query.toLowerCase();

        final List<NewsObject> filteredModelList = new ArrayList<>();
        for (NewsObject model : models) {
            final String title = model.getTitle().toLowerCase();
            final String fTitle = model.getFeed_title().toLowerCase();
            if (title.contains(query)) {
                filteredModelList.add(model);
            }
            else if (fTitle.contains(query)) {
                filteredModelList.add(model);
            }

        }
        return filteredModelList;
    }

    public void setFilter(List<NewsObject> newsList) {
        mNList = new ArrayList<>();
        mNList.addAll(newsList);
        notifyDataSetChanged();
    }

    public void setList(ArrayList<NewsObject> nList){
        this.list = nList;
    }

    public ArrayList<NewsObject> getList(){
        return this.list;
    }

    public void flushFilter(){
        setList((ArrayList<NewsObject>)visibleObjects);
        notifyDataSetChanged();
    }

    public void setFilter(String queryText) {

        visibleObjects = new ArrayList<NewsObject>();
        constraint = constraint.toString().toLowerCase();
        for (NewsObject item: getList()) {
            if (item.getTitle().toLowerCase().contains(queryText))
                visibleObjects.add(item);
        }
        notifyDataSetChanged();
    }

}

