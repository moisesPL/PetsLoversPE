package pe.edu.ulima.petapp.ui.navigator.Item.petProfile;


import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.controller.PetController;
import pe.edu.ulima.petapp.controller.UserController;
import pe.edu.ulima.petapp.dao.Pet;

public class ProfilePetFragment extends Fragment {

    private RecyclerView recyclerView,recyclerViewHeader;
    private RecyclerAdapter adapter;
    private RecyclerAdapterHeader adapterHeader;
    private ArrayList<Pet> petArrayList;
    private FloatingActionButton fab;
    private boolean gender;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_pet, container, false);
        petArrayList =  PetController.getInstance().getPetArray();;

        recyclerViewHeader = (RecyclerView) view.findViewById(R.id.recyle_view_header);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyle_view);
        //fab = (FloatingActionButton) view.findViewById(R.id.fab);
        recyclerViewHeader.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManagerHeader = new LinearLayoutManager(getActivity());
        recyclerViewHeader.setLayoutManager(layoutManagerHeader);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        if(petArrayList.isEmpty())
            setRecyclerViewDataFromParse(); //adding data to pet from parse
        else
            mostrarPets();
        adapterHeader = new RecyclerAdapterHeader(getActivity());
        recyclerViewHeader.setAdapter(adapterHeader);
        //fab.setOnClickListener(onAddingListener());

        // Inflate the layout for this fragment
        return view;
    }

    private void setRecyclerViewDataFromParse() {
        ParseObject user = ParseObject.createWithoutData("_User",UserController.getInstance().getUser().getUserCode());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pet");
        query.whereEqualTo("user", user);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("pet", "Retrieved " + scoreList.size() + " pets");
                    for (int i = 0; i < scoreList.size(); i++) {
                        Pet temporalPet = new Pet(false, scoreList.get(i).get("petType").toString(), scoreList.get(i).get("petName").toString()
                                , scoreList.get(i).get("petAge").toString(), (ParseFile) scoreList.get(i).get("petImage"),scoreList.get(i).getObjectId().toString());
                        Log.e("temporalPet: ", temporalPet.toString());
                        petArrayList.add(temporalPet);
                    }
                    PetController.getInstance().setPetArray(petArrayList);
                    mostrarPets();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void mostrarPets(){
        adapter = new RecyclerAdapter(getActivity(), petArrayList);
        recyclerView.setAdapter(adapter);
    }
/*
    private View.OnClickListener onAddingListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                //dialog.setContentView(R.layout.dialog_add); //layout for dialog
                dialog.setTitle("Add a new friend");
                dialog.setCancelable(false); //none-dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                EditText name = (EditText) dialog.findViewById(R.id.name);
                EditText job = (EditText) dialog.findViewById(R.id.job);
                Spinner spnGender = (Spinner) dialog.findViewById(R.id.gender);
                View btnAdd = dialog.findViewById(R.id.btn_ok);
                View btnCancel = dialog.findViewById(R.id.btn_cancel);

                //set spinner adapter
                ArrayList<String> gendersList = new ArrayList<>();
                gendersList.add("Male");
                gendersList.add("Female");
                ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line, gendersList);
                spnGender.setAdapter(spnAdapter);

                //set handling event for 2 buttons and spinner
                spnGender.setOnItemSelectedListener(onItemSelectedListener());
                btnAdd.setOnClickListener(onConfirmListener(name, job, dialog));
                btnCancel.setOnClickListener(onCancelListener(dialog));

                dialog.show();
            }
        };
    }*/
}
