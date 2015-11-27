package pe.edu.ulima.petapp.ui.navigator.Item.petProfile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;

import java.io.File;
import java.util.List;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.dao.Pet;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private List<Pet> friends;
    private Activity activity;

    public RecyclerAdapter(Activity activity, List<Pet> friends) {
        this.friends = friends;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int position) {

        //setting data to view holder elements
        viewHolder.name.setText(friends.get(position).getPetName());
        viewHolder.job.setText(friends.get(position).getPetAge()+" años");
        viewHolder.type.setText(friends.get(position).getPetType());
        Bitmap bmp = null;
        //try {
          //  bmp = BitmapFactory.decodeByteArray(friends.get(position).getPetImage().getData(), 0, friends.get(position).getPetImage().getData().length);
        //    viewHolder.imageView.setImageBitmap(bmp);
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //} catch (OutOfMemoryError oe){
            viewHolder.imageView.setImageResource(R.mipmap.logo);
        //    oe.printStackTrace();
       // }
        //set on click listener for each element
        //viewHolder.container.setOnClickListener(onClickListener(position));
    }

    private void setDataToView(TextView name, TextView job, ImageView genderIcon, int position) {
        name.setText(friends.get(position).getPetName());
        job.setText(friends.get(position).getPetAge());
        if (friends.get(position).isGender()) {
            genderIcon.setImageResource(R.mipmap.logo_white);
        } else {
            genderIcon.setImageResource(R.mipmap.logo);
        }
    }

    @Override
    public int getItemCount() {
        return (null != friends ? friends.size() : 0);
    }

    /**
     * View holder to display each pet item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView job;
        private TextView type;
        private View container;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            job = (TextView) view.findViewById(R.id.job);
            type = (TextView) view.findViewById(R.id.type);
            container = view.findViewById(R.id.card_view);
        }
    }

}
