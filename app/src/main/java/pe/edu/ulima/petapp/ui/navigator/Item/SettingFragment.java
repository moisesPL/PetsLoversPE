package pe.edu.ulima.petapp.ui.navigator.Item;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.controller.UserController;

public class SettingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        TextView txtName = (TextView) view.findViewById(R.id.txtSettingName);
        TextView txtEmal = (TextView) view.findViewById(R.id.txtSettingEmail);
        TextView txtPets = (TextView) view.findViewById(R.id.txtSettingPets);

        txtName.setText(UserController.getInstance().getUser().getUserName());
        txtEmal.setText(UserController.getInstance().getUser().getUserEmail());
        //txtPets.setText(UserController.getInstance().getUser().getUsePets());
        return view;
    }


}
