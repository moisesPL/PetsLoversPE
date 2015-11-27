package pe.edu.ulima.petapp.ui.navigator.Item.petProfile;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.controller.UserController;
import pe.edu.ulima.petapp.dao.Pet;

public class RecyclerAdapterHeader extends RecyclerView.Adapter<RecyclerAdapterHeader.ViewHolder>{

    private Activity activity;

    public RecyclerAdapterHeader(Activity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_header, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterHeader.ViewHolder viewHolder, int position) {

        //setting data to view holder elements
        if(UserController.getInstance().getUser().getUserName()!=null)
        viewHolder.name.setText(UserController.getInstance().getUser().getUserName());
        if(UserController.getInstance().getUser().getUserName()!=null)
        viewHolder.msj.setText(" ");

        //set on click listener for each element
        //viewHolder.container.setOnClickListener(onClickListener(position));
    }

    @Override
    public int getItemCount() {

        return 1;
    }

    /**
     * View holder to display each pet item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView msj;
        private View container;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.txtName);
            msj = (TextView) view.findViewById(R.id.txtMensaje);
            container = view.findViewById(R.id.card_view_header);
        }
    }
}
