package tk.lucidna.lucid;

/**
 * Created by user on 4/12/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class SolventRecyclerViewAdapter  extends RecyclerView.Adapter<SolventViewHolders> {

    private List<ItemObjects> itemList;
    private Context context;

    public SolventRecyclerViewAdapter(Context context, List<ItemObjects> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.solvent_list, null);
        SolventViewHolders rcv = new SolventViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(SolventViewHolders holder, final int position) {
        holder.countryPhoto.setImageResource(itemList.get(position).getRes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(context, NewsZoom.class);
                x.putExtra("ID", itemList.get(position).getId());
                context.startActivity(x);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}