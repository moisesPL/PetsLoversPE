package pe.edu.ulima.petapp.ui.navigator.Item.inbox;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.dao.Pet;
import pe.edu.ulima.petapp.dao.Sms;


public class RecyclerAdapterInbox extends RecyclerView.Adapter<RecyclerAdapterInbox.ViewHolder>{

    private List<Sms> sms;
    private Activity activity;

    public RecyclerAdapterInbox(Activity activity, List<Sms> sms) {
        this.sms = sms;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_inbox, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterInbox.ViewHolder viewHolder, int position) {

        //setting data to view holder elements
        viewHolder.number.setText(sms.get(position).getNumber());
        viewHolder.body.setText(sms.get(position).getBody());
        viewHolder.time.setText(sms.get(position).getTime());

        //set on click listener for each element
        //viewHolder.container.setOnClickListener(onClickListener(position));
    }

    @Override
    public int getItemCount() {
        return (null != sms ? sms.size() : 0);
    }

    /**
     * View holder to display each pet item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView body;
        private TextView number;
        private TextView time;
        private View container;

        public ViewHolder(View view) {
            super(view);
            body = (TextView) view.findViewById(R.id.txtInboxBody);
            number = (TextView) view.findViewById(R.id.txtInboxNumber);
            time = (TextView) view.findViewById(R.id.txtInboxTime);
            container = view.findViewById(R.id.card_view);
        }
    }

}
