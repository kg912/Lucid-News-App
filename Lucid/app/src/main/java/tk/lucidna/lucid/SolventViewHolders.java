package tk.lucidna.lucid;

/**
 * Created by user on 4/12/2016.
 */
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SolventViewHolders extends RecyclerView.ViewHolder {

    public ImageView countryPhoto;

    public SolventViewHolders(View itemView) {
        super(itemView);
        countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
    }

}
